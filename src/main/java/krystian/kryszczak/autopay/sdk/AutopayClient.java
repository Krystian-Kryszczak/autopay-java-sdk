package krystian.kryszczak.autopay.sdk;

import krystian.kryszczak.autopay.sdk.common.Routes;
import krystian.kryszczak.autopay.sdk.common.parser.ServiceResponseParser;
import krystian.kryszczak.autopay.sdk.confirmation.Confirmation;
import krystian.kryszczak.autopay.sdk.hash.HashChecker;
import krystian.kryszczak.autopay.sdk.hash.Hashable;
import krystian.kryszczak.autopay.sdk.http.HttpClient;
import krystian.kryszczak.autopay.sdk.http.HttpRequest;
import krystian.kryszczak.autopay.sdk.itn.Itn;
import krystian.kryszczak.autopay.sdk.itn.decoder.Base64ItnDecoder;
import krystian.kryszczak.autopay.sdk.itn.decoder.ItnDecoder;
import krystian.kryszczak.autopay.sdk.itn.response.ItnResponse;
import krystian.kryszczak.autopay.sdk.itn.validator.ItnValidator;
import krystian.kryszczak.autopay.sdk.itn.validator.XmlItnValidator;
import krystian.kryszczak.autopay.sdk.payway.PaywayList;
import krystian.kryszczak.autopay.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.autopay.sdk.regulation.RegulationList;
import krystian.kryszczak.autopay.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.autopay.sdk.transaction.*;
import krystian.kryszczak.autopay.sdk.transaction.parser.TransactionResponseParser;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionBackgroundRequest;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionInitRequest;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionRequest;
import krystian.kryszczak.autopay.sdk.util.RandomUtils;
import lombok.AllArgsConstructor;
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

@AllArgsConstructor
@ApiStatus.AvailableSince("1.0")
public final class AutopayClient {
    private static final String HEADER = "BmHeader";
    private static final String PAY_HEADER = "pay-bm";
    private static final String CONTINUE_HEADER = "pay-bm-continue-transaction-url";

    private static final Logger logger = LoggerFactory.getLogger(AutopayClient.class);

    private final AutopayConfiguration configuration;
    private final HttpClient httpClient;

    public AutopayClient(final @NotNull AutopayConfiguration configuration) {
        this.configuration = configuration;
        this.httpClient = HttpClient.getDefaultHttpClient();
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
    public @NotNull Publisher<@NotNull TransactionBackground> doTransactionBackground(final @NotNull TransactionBackgroundRequest transactionRequest) {
        return doTransaction(transactionRequest, false).cast(TransactionBackground.class);
    }

    /**
     * Initialize transaction.
     * Returns transaction continuation or transaction information.
     */
    @ApiStatus.AvailableSince("1.0")
    public @NotNull Publisher<? extends @NotNull Transaction> doTransactionInit(final @NotNull TransactionInitRequest transactionRequest) {
        return doTransaction(transactionRequest, true);
    }

    @SneakyThrows
    private @NotNull <T extends Transaction> Flux<? extends @NotNull Transaction> doTransaction(final @NotNull TransactionRequest<T> transactionRequest, final boolean transactionInit) {
        return Mono.fromRunnable(() -> transactionRequest.configure(configuration))
            .mapNotNull(it -> {
                try {
                    return new HttpRequest<>(
                        new URI(transactionRequest.getGatewayUrl() + Routes.PAYMENT_ROUTE),
                        Map.of(HEADER, !transactionInit ? PAY_HEADER : CONTINUE_HEADER),
                        transactionRequest.getTransaction()
                    );
                } catch (URISyntaxException e) {
                    logger.error("An error occurred while executing doTransaction" + (transactionInit ? "Init" : "Background") + ".", e);
                    return null;
                }
            })
            .flatMapMany(httpClient::post)
            .flatMap(response -> new TransactionResponseParser<T>(response, configuration).parse(transactionInit))
            .doOnError(throwable -> logger.error(
                "An error occurred while executing doTransaction" + (transactionInit ? "Init" : "Background") + ".",
                throwable
            ));
    }

    /**
     * Process ITN requests.
     * @param itn string encoded with base64
     */
    @SneakyThrows
    @ApiStatus.AvailableSince("1.0")
    public @NotNull Publisher<@NotNull Itn> doItnIn(final @NotNull String itn) {
        return Mono.justOrEmpty(getItnObject(itn));
    }

    /**
     * Returns response for ITN IN request.
     */
    @ApiStatus.AvailableSince("1.0")
    public @NotNull Publisher<@NotNull ItnResponse> doItnInResponse(final @NotNull Itn itn) {
        return doItnInResponse(itn, true);
    }

    /**
     * Returns response for ITN IN request.
     */
    @ApiStatus.AvailableSince("1.0")
    public @NotNull Publisher<@NotNull ItnResponse> doItnInResponse(final @NotNull Itn itn, final boolean transactionConfirmed) {
        return Mono.just(ItnResponse.create(itn, transactionConfirmed, this.configuration))
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
            .flatMap(it ->
                new ServiceResponseParser(it, this.configuration)
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
            .flatMap(it ->
                new ServiceResponseParser(it, this.configuration)
                    .parseListResponse(RegulationListResponse.class)
            );
    }

    /**
     * Checks id hash is valid.
     */
    @ApiStatus.AvailableSince("1.0")
    public boolean checkHash(final @NotNull Hashable hashable) {
        return HashChecker.checkHash(hashable, configuration);
    }

    /**
     * Method allows to check if gateway returns with valid data.
     */
    @ApiStatus.AvailableSince("1.0")
    public boolean doConfirmationCheck(final @NotNull Confirmation confirmation) {
        return checkHash(confirmation);
    }

    /**
     * Method allows to get Itn object from base64
     */
    @ApiStatus.AvailableSince("1.0")
    public static @Nullable Itn getItnObject(final @NotNull String itn) {
        final ItnDecoder itnDecoder = new Base64ItnDecoder();
        final ItnValidator itnValidator = new XmlItnValidator();

        final String decoded = itnDecoder.decode(itn);

        if (!itnValidator.validate(decoded)) {
            throw new IllegalArgumentException("ITN data must be an valid XML, base64 encoded.");
        }

        return Itn.buildFormXml(decoded);
    }
}
