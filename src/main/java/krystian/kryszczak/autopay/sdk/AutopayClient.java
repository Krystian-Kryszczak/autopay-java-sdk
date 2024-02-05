package krystian.kryszczak.autopay.sdk;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import krystian.kryszczak.autopay.sdk.common.Routes;
import krystian.kryszczak.autopay.sdk.common.exception.HashNotReturnedFromServerException;
import krystian.kryszczak.autopay.sdk.common.parser.ServiceResponseParser;
import krystian.kryszczak.autopay.sdk.confirmation.Confirmation;
import krystian.kryszczak.autopay.sdk.hash.HashChecker;
import krystian.kryszczak.autopay.sdk.hash.Hashable;
import krystian.kryszczak.autopay.sdk.http.client.HttpClient;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequest;
import krystian.kryszczak.autopay.sdk.itn.Itn;
import krystian.kryszczak.autopay.sdk.itn.decoder.Base64ItnDecoder;
import krystian.kryszczak.autopay.sdk.itn.decoder.ItnDecoder;
import krystian.kryszczak.autopay.sdk.itn.request.ItnRequest;
import krystian.kryszczak.autopay.sdk.itn.response.ItnResponse;
import krystian.kryszczak.autopay.sdk.itn.validator.ItnValidator;
import krystian.kryszczak.autopay.sdk.itn.validator.XmlItnValidator;
import krystian.kryszczak.autopay.sdk.payway.PaywayList;
import krystian.kryszczak.autopay.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.autopay.sdk.regulation.RegulationList;
import krystian.kryszczak.autopay.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.autopay.sdk.serializer.Serializer;
import krystian.kryszczak.autopay.sdk.transaction.*;
import krystian.kryszczak.autopay.sdk.transaction.parser.TransactionResponseParser;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionBackgroundRequest;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionInitRequest;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionRequest;
import krystian.kryszczak.autopay.sdk.util.RandomUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.function.Predicate;

@Singleton
@AllArgsConstructor(onConstructor_ = @Inject)
@ApiStatus.AvailableSince("1.0")
public final class AutopayClient {
    private static final String HEADER = "BmHeader";
    private static final String PAY_HEADER = "pay-bm";
    private static final String CONTINUE_HEADER = "pay-bm-continue-transaction-url";

    private static final Logger logger = LoggerFactory.getLogger(AutopayClient.class);

    private final AutopayConfiguration configuration;
    private final HttpClient httpClient;
    @Getter
    private final Serializer serializer;

    public AutopayClient(final @NotNull AutopayConfiguration configuration) {
        this(configuration, HttpClient.createDefault(), Serializer.createDefault());
    }

    /**
     * Perform standard transaction.
     */
    @ApiStatus.AvailableSince("1.0")
    public @NotNull String getTransactionRedirect(final @NotNull TransactionRequest<?> transactionRequest) {
        transactionRequest.configure(configuration);
        return View.createRedirectHtml(transactionRequest);
    }

    /**
     * Perform transaction in background.
     * Returns payway form or transaction data for user.
     */
    @ApiStatus.AvailableSince("1.0")
    public @NotNull Publisher<@NotNull TransactionBackground> doTransactionBackground(
            final @NotNull TransactionBackgroundRequest transactionRequest) {
        return doTransaction(transactionRequest, false).cast(TransactionBackground.class);
    }

    /**
     * Initialize transaction.
     * Returns transaction continuation or transaction information.
     */
    @ApiStatus.AvailableSince("1.0")
    public @NotNull Publisher<? extends @NotNull Transaction> doTransactionInit(
            final @NotNull TransactionInitRequest transactionRequest) {
        return doTransaction(transactionRequest, true);
    }

    @SneakyThrows
    private @NotNull <T extends Transaction> Flux<? extends @NotNull Transaction> doTransaction(
            final @NotNull TransactionRequest<T> transactionRequest, final boolean transactionInit) {
        return Mono.fromRunnable(() -> transactionRequest.configure(configuration))
            .then(Mono.fromSupplier(() -> {
                try {
                    return new HttpRequest<>(
                        new URI(transactionRequest.getGatewayUrl() + Routes.PAYMENT_ROUTE),
                        Map.of(HEADER, !transactionInit ? PAY_HEADER : CONTINUE_HEADER),
                        transactionRequest.getTransaction()
                    );
                } catch (URISyntaxException e) {
                    logger.error("An error occurred while executing doTransaction"
                        + (transactionInit ? "Init" : "Background") + ".", e);
                    return null;
                }
            })).flatMapMany(httpClient::post)
            .mapNotNull(response -> new TransactionResponseParser<T>(response, configuration).parse(transactionInit))
            .doOnError(throwable -> logger.error(
                "An error occurred while executing doTransaction" + (transactionInit ? "Init" : "Background") + ".",
                throwable
            ));
    }

    /**
     * Process ITN requests.
     * @param itnRequestBase64 string encoded with base64
     */
    @SneakyThrows
    @ApiStatus.AvailableSince("1.0")
    public @Nullable ItnRequest doItnIn(final @NotNull String itnRequestBase64) {
        return getItnRequestObject(itnRequestBase64, this.serializer);
    }

    /**
     * Returns response for ITN IN request.
     */
    @ApiStatus.AvailableSince("1.0")
    public @NotNull Publisher<@NotNull ItnResponse> doItnInResponse(final @NotNull ItnRequest itnRequest) {
        return doItnInResponse(itnRequest, itn -> true);
    }

    /**
     * Returns response for ITN IN request.
     */
    @ApiStatus.AvailableSince("1.0")
    public @NotNull Publisher<@NotNull ItnResponse> doItnInResponse(final @NotNull ItnRequest itnRequest,
            final Predicate<@NotNull Itn> confirmTransactionPredicate) {
        return Mono.just(ItnResponse.create(itnRequest, confirmTransactionPredicate, this.configuration))
            .onErrorComplete();
    }

    /**
     * Returns payway list.
     */
    @ApiStatus.AvailableSince("1.0")
    public @NotNull Publisher<PaywayListResponse> getPaywayList(@NotNull String gatewayUrl) {
        final HttpRequest<PaywayList> request = new HttpRequest<>(
            URI.create(gatewayUrl).resolve(Routes.PAYWAY_LIST_ROUTE.getValue()),
            Map.of(),
            PaywayList.create(
                configuration.serviceId(),
                RandomUtils.randomMessageId(),
                configuration
            )
        );

        return Flux.from(httpClient.post(request))
            .mapNotNull(it ->
                new ServiceResponseParser(it, this.configuration, this.serializer)
                    .parseListResponse(PaywayListResponse.class)
            );
    }

    /**
     * Returns payment regulations.
     */
    @ApiStatus.AvailableSince("1.0")
    public @NotNull Publisher<RegulationListResponse> getRegulationList(final @NotNull String gatewayUrl) {
        final HttpRequest<RegulationList> request = new HttpRequest<>(
            URI.create(gatewayUrl).resolve(Routes.GET_REGULATIONS_ROUTE.getValue()),
            Map.of(),
            RegulationList.create(
                configuration.serviceId(),
                RandomUtils.randomMessageId(),
                configuration
            )
        );

        return Flux.from(httpClient.post(request))
            .mapNotNull(it ->
                new ServiceResponseParser(it, this.configuration, this.serializer)
                    .parseListResponse(RegulationListResponse.class)
            );
    }

    /**
     * Checks id hash is valid.
     */
    @ApiStatus.AvailableSince("1.0")
    public boolean checkHash(final @NotNull Hashable hashable) throws HashNotReturnedFromServerException {
        return HashChecker.checkHash(hashable, configuration);
    }

    /**
     * Method allows to check if gateway returns with valid data.
     */
    @ApiStatus.AvailableSince("1.0")
    public boolean doConfirmationCheck(final @NotNull Confirmation confirmation) throws HashNotReturnedFromServerException {
        return checkHash(confirmation);
    }

    /**
     * Method allows to get Itn object from base64
     */
    @ApiStatus.AvailableSince("1.0")
    public static @Nullable ItnRequest getItnRequestObject(final @NotNull String encodedItnRequest, final Serializer serializer) {
        final ItnDecoder itnDecoder = new Base64ItnDecoder();
        final ItnValidator itnValidator = new XmlItnValidator();

        final String decoded = itnDecoder.decode(encodedItnRequest);

        if (!itnValidator.validate(decoded)) {
            throw new IllegalArgumentException("ITN data must be an valid XML, base64 encoded.");
        }

        return serializer.deserialize(decoded, ItnRequest.class);
    }
}
