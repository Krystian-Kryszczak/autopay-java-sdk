package krystian.kryszczak.bm.sdk;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import krystian.kryszczak.bm.sdk.common.Routes;
import krystian.kryszczak.bm.sdk.common.parser.ServiceResponseParser;
import krystian.kryszczak.bm.sdk.confirmation.Confirmation;
import krystian.kryszczak.bm.sdk.hash.HashChecker;
import krystian.kryszczak.bm.sdk.hash.Hashable;
import krystian.kryszczak.bm.sdk.http.HttpClient;
import krystian.kryszczak.bm.sdk.http.HttpRequest;
import krystian.kryszczak.bm.sdk.itn.Itn;
import krystian.kryszczak.bm.sdk.itn.decoder.Base64ItnDecoder;
import krystian.kryszczak.bm.sdk.itn.decoder.ItnDecoder;
import krystian.kryszczak.bm.sdk.itn.response.ItnResponse;
import krystian.kryszczak.bm.sdk.itn.validator.ItnValidator;
import krystian.kryszczak.bm.sdk.itn.validator.XmlItnValidator;
import krystian.kryszczak.bm.sdk.payway.PaywayList;
import krystian.kryszczak.bm.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.bm.sdk.regulation.RegulationList;
import krystian.kryszczak.bm.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.bm.sdk.transaction.*;
import krystian.kryszczak.bm.sdk.transaction.parser.TransactionResponseParser;
import krystian.kryszczak.bm.sdk.transaction.request.TransactionBackgroundRequest;
import krystian.kryszczak.bm.sdk.transaction.request.TransactionInitRequest;
import krystian.kryszczak.bm.sdk.transaction.request.TransactionRequest;
import krystian.kryszczak.bm.sdk.util.RandomUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@ApiStatus.AvailableSince("")
public final class BlueMediaClient {
    private static final String HEADER = "BmHeader";
    private static final String PAY_HEADER = "pay-bm";
    private static final String CONTINUE_HEADER = "pay-bm-continue-transaction-url";

    private static final Logger logger = LoggerFactory.getLogger(BlueMediaClient.class);

    private final BlueMediaConfiguration configuration;
    private final HttpClient httpClient;

    public BlueMediaClient(final @NotNull BlueMediaConfiguration configuration) {
        this.configuration = configuration;
        this.httpClient = HttpClient.getDefaultHttpClient();
    }

    /**
     * Perform standard transaction.
     */
    @ApiStatus.AvailableSince("")
    public @NotNull String getTransactionRedirect(final @NotNull TransactionRequest<?> transactionRequest) {
        transactionRequest.configure(configuration);
        return View.createRedirectHtml(transactionRequest);
    }

    /**
     * Perform transaction in background.
     * Returns payway form or transaction data for user.
     */
    @ApiStatus.AvailableSince("")
    public @NotNull Maybe<@NotNull Transaction> doTransactionBackground(final @NotNull TransactionBackgroundRequest transactionRequest) {
        return doTransaction(transactionRequest, false);
    }

    /**
     * Initialize transaction.
     * Returns transaction continuation or transaction information.
     */
    @ApiStatus.AvailableSince("")
    public @NotNull Maybe<@NotNull Transaction> doTransactionInit(final @NotNull TransactionInitRequest transactionRequest) {
        return doTransaction(transactionRequest, true);
    }

    @SneakyThrows
    private @NotNull <T extends Transaction> Maybe<@NotNull Transaction> doTransaction(final @NotNull TransactionRequest<T> transactionRequest, final boolean transactionInit) {
        return Completable.fromAction(() -> transactionRequest.configure(configuration)).andThen(
            Single.just(new HttpRequest<>(
                    new URI(transactionRequest.getGatewayUrl() + Routes.PAYMENT_ROUTE),
                    Map.of(
                        HEADER, !transactionInit
                                ? PAY_HEADER
                                : CONTINUE_HEADER
                    ),
                    transactionRequest.getTransaction()
                ))
                .doOnError(throwable ->
                    logger.error(
                        "An error occurred while executing doTransaction" + (transactionInit ? "Init" : "Background") + ".",
                        throwable
                    )
                )
                .onErrorComplete()
                .flatMap(httpClient::post)
                .flatMap(response -> new TransactionResponseParser<T>(response, configuration).parse(transactionInit))
        );
    }

    /**
     * Process ITN requests.
     * @param itn string encoded with base64
     */
    @SneakyThrows
    @ApiStatus.AvailableSince("")
    public @NotNull Maybe<@NotNull Itn> doItnIn(final @NotNull String itn) {
        return Maybe.fromOptional(Optional.ofNullable(getItnObject(itn)));
    }

    /**
     * Returns response for ITN IN request.
     */
    @ApiStatus.AvailableSince("")
    public @NotNull Maybe<@NotNull ItnResponse> doItnInResponse(final @NotNull Itn itn) {
        return doItnInResponse(itn, true);
    }

    /**
     * Returns response for ITN IN request.
     */
    @ApiStatus.AvailableSince("")
    public @NotNull Maybe<@NotNull ItnResponse> doItnInResponse(final @NotNull Itn itn, final boolean transactionConfirmed) {
        return Maybe.just(ItnResponse.create(itn, transactionConfirmed, this.configuration))
            .onErrorComplete();
    }

    /**
     * Returns payway list.
     */
    @ApiStatus.AvailableSince("")
    public @NotNull Maybe<PaywayListResponse> getPaywayList(@NotNull String gatewayUrl) {
        final HttpRequest<PaywayList> request = new HttpRequest<>(
            URI.create(gatewayUrl).resolve(Routes.PAYWAY_LIST_ROUTE.getValue()),
            Map.of(),
            PaywayList.create(
                configuration.getServiceId(),
                RandomUtils.randomMessageId(),
                configuration
            )
        );

        return httpClient.post(request)
            .flatMap(it ->
                new ServiceResponseParser(it, this.configuration)
                    .parseListResponse(PaywayListResponse.class)
            );
    }

    /**
     * Returns payment regulations.
     */
    @ApiStatus.AvailableSince("")
    public @NotNull Maybe<RegulationListResponse> getRegulationList(final @NotNull String gatewayUrl) {
        final HttpRequest<RegulationList> request = new HttpRequest<>(
            URI.create(gatewayUrl).resolve(Routes.GET_REGULATIONS_ROUTE.getValue()),
            Map.of(),
            RegulationList.create(
                configuration.getServiceId(),
                RandomUtils.randomMessageId(),
                configuration
            )
        );

        return httpClient.post(request)
            .flatMap(it ->
                new ServiceResponseParser(it, this.configuration)
                    .parseListResponse(RegulationListResponse.class)
            );
    }

    /**
     * Checks id hash is valid.
     */
    @ApiStatus.AvailableSince("")
    public boolean checkHash(final @NotNull Hashable hashable) {
        return HashChecker.checkHash(hashable, configuration);
    }

    /**
     * Method allows to check if gateway returns with valid data.
     */
    @ApiStatus.AvailableSince("")
    public boolean doConfirmationCheck(final @NotNull Confirmation confirmation) {
        return checkHash(confirmation);
    }

    /**
     * Method allows to get Itn object from base64
     */
    @ApiStatus.AvailableSince("")
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
