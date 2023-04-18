package krystian.kryszczak.bm.sdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import krystian.kryszczak.bm.sdk.common.Routes;
import krystian.kryszczak.bm.sdk.confirmation.Confirmation;
import krystian.kryszczak.bm.sdk.hash.HashChecker;
import krystian.kryszczak.bm.sdk.hash.Hashable;
import krystian.kryszczak.bm.sdk.http.HttpClient;
import krystian.kryszczak.bm.sdk.http.VertxHttpClient;
import krystian.kryszczak.bm.sdk.http.HttpRequest;
import krystian.kryszczak.bm.sdk.itn.Itn;
import krystian.kryszczak.bm.sdk.itn.decoder.Base64ItnDecoder;
import krystian.kryszczak.bm.sdk.itn.decoder.ItnDecoder;
import krystian.kryszczak.bm.sdk.itn.dto.ItnDto;
import krystian.kryszczak.bm.sdk.itn.response.ItnResponse;
import krystian.kryszczak.bm.sdk.itn.validator.ItnValidator;
import krystian.kryszczak.bm.sdk.itn.validator.XmlItnValidator;
import krystian.kryszczak.bm.sdk.payway.PaywayList;
import krystian.kryszczak.bm.sdk.payway.PaywayListRequest;
import krystian.kryszczak.bm.sdk.regulation.RegulationList;
import krystian.kryszczak.bm.sdk.regulation.RegulationListRequest;
import krystian.kryszczak.bm.sdk.regulation.RegulationListRequest2;
import krystian.kryszczak.bm.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.bm.sdk.transaction.*;
import krystian.kryszczak.bm.sdk.transaction.dto.TransactionDto;
import krystian.kryszczak.bm.sdk.util.RandomUtils;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;

@AllArgsConstructor
@ApiStatus.AvailableSince("")
public final class BlueMediaClient {
    private static final Logger logger = LoggerFactory.getLogger(BlueMediaClient.class);
    private static final XmlMapper xmlMapper = new XmlMapper();

    private final BlueMediaConfiguration configuration;
    private final HttpClient httpClient;

    public BlueMediaClient(final @NotNull BlueMediaConfiguration configuration) {
        this.configuration = configuration;
        this.httpClient = new VertxHttpClient();
    }

    private static final String HEADER = "BmHeader";
    private static final String PAY_HEADER = "pay-bm";
    private static final String CONTINUE_HEADER = "pay-bm-continue-transaction-url";

    /**
     * Perform standard transaction.
     */
    @ApiStatus.AvailableSince("")
    public @NotNull Single<@NotNull String> getTransactionRedirect(final @NotNull TransactionData<Transaction> transactionData) {
        return Single.just(
            View.createRedirectHtml(TransactionDto.create(transactionData, this.configuration))
        );
    }

    /**
     * Perform transaction in background.
     * Returns payway form or transaction data for user.
     */
    //@ApiStatus.AvailableSince("")
    public @NotNull Maybe<@NotNull TransactionBackground> doTransactionBackground(final @NotNull TransactionData<TransactionBackground> transactionData) {
        return doTransaction(transactionData, false);
    }

    /**
     * Initialize transaction.
     * Returns transaction continuation or transaction information.
     */
    //@ApiStatus.AvailableSince("")
    public @NotNull Maybe<@NotNull TransactionInit> doTransactionInit(final @NotNull TransactionData<TransactionInit> transactionData) {
        return doTransaction(transactionData, true);
    }

    private @NotNull <T extends Transaction> Maybe<@NotNull T> doTransaction(final @NotNull TransactionData<T> transactionData, final boolean transactionInit) { // TODO
        return Single.just(TransactionDto.create(transactionData, this.configuration))
            .map(transactionDto -> new HttpRequest<>(
                new URI(transactionData.gatewayUrl() + Routes.PAYMENT_ROUTE),
                Map.of(
                    HEADER, !transactionInit
                        ? PAY_HEADER
                        : CONTINUE_HEADER
                ),
                transactionDto
            ))
            .doOnError(throwable ->
                logger.error(
                    "An error occurred while executing doTransaction" + (transactionInit ? "Init" : "Background") + ".",
                    throwable
                )
            )
            .onErrorComplete()
            .flatMap(httpClient::post)
//            .map(response -> new TransactionResponseParser<>(response, configuration).parse(transactionInit))
//            .flatMap(Response::getBody)
            .flatMap(r -> Maybe.empty());
    }

    /**
     * Process ITN requests.
     * @param itn string encoded with base64
     */
    //@ApiStatus.AvailableSince("")
    public @NotNull Maybe<@NotNull Itn> doItnIn(final @NotNull String itn) throws JsonProcessingException {
        final ItnDecoder itnDecoder = new Base64ItnDecoder();
        final ItnValidator itnValidator = new XmlItnValidator();

        final var decoded = itnDecoder.decode(itn);
        if (itnValidator.validate(decoded)) {
            return Maybe.just(
                ItnDto.buildFormXml(decoded)
                    .getItn()
            );
        }

        return Maybe.empty();
    }

    /**
     * Returns response for ITN IN request.
     */
    //@ApiStatus.AvailableSince("")
    public @NotNull Maybe<@NotNull ItnResponse> doItnInResponse(final @NotNull Itn itn) {
        return doItnInResponse(itn, true);
    }

    /**
     * Returns response for ITN IN request.
     */
    //@ApiStatus.AvailableSince("1.0")
    public @NotNull Maybe<@NotNull ItnResponse> doItnInResponse(final @NotNull Itn itn, final boolean transactionConfirmed) {
        return Maybe.just(ItnResponse.create(itn, transactionConfirmed, this.configuration))
            .onErrorComplete();
    }

    public @NotNull Maybe<String> getGatewayList() { // new

        return Maybe.empty();
    }

    /**
     * Returns payway list.
     */
    public @NotNull Maybe<PaywayList> getPaywayList(@NotNull String gatewayUrl) {
        final var a = Map.of(
            "gateway", gatewayUrl,
            "paywayList", Map.of(
                "serviceID", this.configuration.getServiceId(),
                "messageID", RandomUtils.randomMessageId()
            )
        );

//        final var request = new HttpRequest<PaywayListRequest>(
//            URI.create(gatewayUrl + Routes.PAYWAY_LIST_ROUTE),
//            Map.of(),
//            PaywayListRequest.create(
//                gatewayUrl,
//                configuration
//            )
//        );

//        return httpClient.post(request)
//            .flatMap(Maybe.empty());
        return Maybe.empty();
    }

    /**
     * Returns payment regulations.
     */
    public @NotNull Maybe<RegulationListResponse> getRegulationList(final @NotNull String gatewayUrl) {

        final URI requestUrl = URI.create(gatewayUrl + Routes.GET_REGULATIONS_ROUTE);
        final Map<String, String> headers = Map.of();

//        final RegulationList regulationList = new RegulationList(
//            configuration.getServiceId(),
//            RandomUtils.randomMessageId()
//        );

        final var request = RegulationListRequest.create(
            gatewayUrl,
            configuration.getServiceId(),
            RandomUtils.randomMessageId(),
            configuration
        );
//
//        final var request = new HttpRequest<>(
//            requestUrl,
//            headers,
//
//            requestUrl
//        );

//        return httpClient.post(request);
        return Maybe.empty();
    }

    public @NotNull Maybe<String> getRegulationList2(final @NotNull String gatewayUrl) {
        return Maybe.empty();
//        return httpClient.post(
//            new HttpRequest<>(
//                URI.create(gatewayUrl + "/legalData"),
//                Map.of(),
//
//                RegulationListRequest2.create(
//                    1500,
//                    configuration.getServiceId(),
//                    RandomUtils.randomMessageId(),
//                    configuration
//                )
//            )
//        );
    }


    /**
     * Checks id hash is valid.
     */
    @ApiStatus.AvailableSince("")
    public boolean checkHash(final @NotNull Hashable hashable) {
        return HashChecker.instance.checkHash(hashable, configuration);
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
    public static @NotNull Itn getItnObject(final @NotNull String itn) throws Exception {
        final ItnDecoder itnDecoder = new Base64ItnDecoder();
        final ItnValidator itnValidator = new XmlItnValidator();

        final String decoded = itnDecoder.decode(itn);

        if (!itnValidator.validate(decoded)) {
            throw new RuntimeException("ITN data must be an valid XML, base64 encoded.");
        }

        return xmlMapper.readValue(decoded, Itn.class);
    }
}
