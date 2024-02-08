package krystian.kryszczak.autopay.sdk;

import fixtures.itn.ItnFixture;
import fixtures.payway.PaywayListFixture;
import fixtures.regulation.RegulationListFixture;
import fixtures.transaction.TransactionBackgroundFixture;
import fixtures.transaction.TransactionInitFixture;
import krystian.kryszczak.autopay.sdk.common.exception.HashNotReturnedFromServerException;
import krystian.kryszczak.autopay.sdk.confirmation.Confirmation;
import krystian.kryszczak.autopay.sdk.http.client.HttpClient;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequest;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequestBody;
import krystian.kryszczak.autopay.sdk.itn.Itn;
import krystian.kryszczak.autopay.sdk.itn.request.ItnRequest;
import krystian.kryszczak.autopay.sdk.itn.response.ItnResponse;
import krystian.kryszczak.autopay.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.autopay.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.autopay.sdk.serializer.Serializer;
import krystian.kryszczak.autopay.sdk.transaction.*;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionInitRequest;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static krystian.kryszczak.autopay.sdk.util.ArrayUtils.merge;
import static krystian.kryszczak.autopay.sdk.util.StringUtils.unescapeHtml;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.xmlunit.assertj.XmlAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class AutopayClientTest extends BaseTestCase {
    private static AutopayClient client;
    private static HttpClient httpClient;

    @BeforeAll
    public static void setUp() {
        httpClient = mock(HttpClient.class);

        client = new AutopayClient(
            AutopayConfiguration.builder()
                .setServiceId(SERVICE_ID)
                .setSharedKey(SHARED_KEY)
                .build(),
            httpClient,
            Serializer.createDefault()
        );
    }

    @Test
    public void getTransactionRedirectReturnsRedirectView() {
        final String result = client.getTransactionRedirect(TransactionInitFixture.getTransactionInitContinue());

        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    @ParameterizedTest
    @MethodSource("checkConfirmationProvider")
    public void doConfirmationCheckReturnsStatus(Confirmation data, boolean result) {
        final boolean check = client.doConfirmationCheck(data);

        assertSame(result, check);
    }

    @Test
    public void doTransactionBackgroundReturnsTransactionData() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(TransactionBackgroundFixture.getTransactionBackgroundResponse())
        );

        final Mono<Transaction> result = Mono.fromDirect(
            client.doTransactionBackground(TransactionBackgroundFixture.getTransactionBackground())
        );

        final Transaction transactionBackground = assertDoesNotThrow(() -> result.block());
        assertNotNull(transactionBackground);
        assertInstanceOf(TransactionBackground.class, transactionBackground);

        final var transactionBackgroundFixture = TransactionBackgroundFixture.getTransactionBackgroundResponseData();

        assertNotNull(transactionBackground.getReceiverNRB());
        assertEquals(transactionBackgroundFixture.get("receiverNRB"), transactionBackground.getReceiverNRB());
        assertNotNull(transactionBackground.getReceiverName());
        assertEquals(transactionBackgroundFixture.get("receiverName"), transactionBackground.getReceiverName());
        assertNotNull(transactionBackground.getReceiverAddress());
        assertEquals(transactionBackgroundFixture.get("receiverAddress"), transactionBackground.getReceiverAddress());
        assertNotNull(transactionBackground.getOrderID());
        assertEquals(transactionBackgroundFixture.get("orderID"), transactionBackground.getOrderID());
        assertNotNull(transactionBackground.getAmount());
        assertEquals(transactionBackgroundFixture.get("amount"), transactionBackground.getAmount());
        assertNotNull(transactionBackground.getCurrency());
        assertEquals(transactionBackgroundFixture.get("currency"), transactionBackground.getCurrency());
        assertNotNull(transactionBackground.getTitle());
        assertEquals(transactionBackgroundFixture.get("title"), transactionBackground.getTitle());
        assertNotNull(transactionBackground.getRemoteID());
        assertEquals(transactionBackgroundFixture.get("remoteID"), transactionBackground.getRemoteID());
        assertNotNull(transactionBackground.getBankHref());
        assertEquals(transactionBackgroundFixture.get("bankHref"), transactionBackground.getBankHref());
        assertNotNull(transactionBackground.getReturnURL());
        assertEquals(transactionBackgroundFixture.get("returnURL"), transactionBackground.getReturnURL());

        assertThat(client.getSerializer().serialize(transactionBackground))
            .and(TransactionBackgroundFixture.getTransactionBackgroundResponse())
            .ignoreWhitespace()
            .areIdentical();
    }

    @Test
    public void doTransactionBackgroundReturnsPaywayForm() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(TransactionBackgroundFixture.getPaywayFormResponse())
        );

        final Mono<Transaction> result = Mono.fromDirect(
            client.doTransactionBackground(TransactionBackgroundFixture.getTransactionBackground())
        );

        final Transaction transaction = assertDoesNotThrow(() -> result.block());

        assertNotNull(transaction);
        assertInstanceOf(PaywayFormResponse.class, transaction);
        assertEquals(unescapeHtml(TransactionBackgroundFixture.getPaywayFormResponse()), transaction.toString());
    }

    @Test
    public void doTransactionInitReturnsTransactionContinueData() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(TransactionInitFixture.getTransactionInitContinueResponse())
        );

        final Mono<? extends Transaction> result = Mono.fromDirect(
            client.doTransactionInit(TransactionInitFixture.getTransactionInitContinue())
        );

        final Transaction transaction = assertDoesNotThrow(() -> result.block());
        assertNotNull(transaction);
        assertInstanceOf(TransactionContinue.class, transaction);
    }

    @Test
    public void doTransactionInitReturnsTransactionData() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(TransactionInitFixture.getTransactionInitResponse())
        );

        final Mono<? extends Transaction> result = Mono.fromDirect(
            client.doTransactionInit(TransactionInitFixture.getTransactionInit())
        );

        final Transaction transaction = assertDoesNotThrow(() -> result.block());
        assertNotNull(transaction);
        assertInstanceOf(TransactionInit.class, transaction);
    }

    @Test
    public void doItnInReturnsItnData() {
        final ItnRequest itnRequest = assertDoesNotThrow(() -> client.doItnIn(ItnFixture.getItnInRequestBase64Encoded()));
        final Map<String, String> itnFixture = ItnFixture.getTransactionDataFromXml();

        assertNotNull(itnRequest);
        for (final Itn itn : itnRequest.getTransactions().transaction()) {
            assertEquals(itnFixture.get("remoteID"), itn.getRemoteID());
            assertEquals(itnFixture.get("amount"), itn.getAmount());
            assertEquals(itnFixture.get("currency"), itn.getCurrency());
            assertEquals(itnFixture.get("paymentDate"), itn.getPaymentDate());
            assertEquals(itnFixture.get("paymentStatus"), itn.getPaymentStatus());
            assertEquals(itnFixture.get("paymentStatusDetails"), itn.getPaymentStatusDetails());
        }
    }

    @ParameterizedTest
    @MethodSource("itnProvider")
    public void doItnInThrowsExceptionOnWrongBase64(String itn) {
        assertThrows(IllegalArgumentException.class, () -> client.doItnIn(itn));
    }

    @Test
    public void doItnResponseReturnsConfirmationResponse() {
        final ItnRequest itn = assertDoesNotThrow(() -> client.doItnIn(ItnFixture.getItnInRequestBase64Encoded()));
        assertNotNull(itn);
        final Mono<ItnResponse> result = Mono.fromDirect(client.doItnInResponse(itn, it -> true));
        final ItnResponse itnResponse = assertDoesNotThrow(() -> result.block());
        assertNotNull(itnResponse);
        assertThat(ItnFixture.getItnResponse())
            .and(client.getSerializer().serialize(itnResponse))
            .ignoreWhitespace()
            .areIdentical();
    }

    @Test
    public void getPaywayList() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(PaywayListFixture.getPaywayListResponse())
        );

        final Mono<PaywayListResponse> result = Mono.fromDirect(client.getPaywayList(GATEWAY_URL));

        assertNotNull(assertDoesNotThrow(() -> result.block()));
    }

    @Test
    public void getRegulationList() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(RegulationListFixture.getRegulationListResponse())
        );

        final Mono<RegulationListResponse> result = Mono.fromDirect(client.getRegulationList(GATEWAY_URL));

        assertNotNull(assertDoesNotThrow(() -> result.block()));
    }

    @ParameterizedTest
    @MethodSource("checkHashProvider")
    public void checkHashReturnsExpectedValue(String hash, boolean value) {
        final Transaction transaction = mock(TransactionInit.class);
        final TransactionInitRequest fixture = TransactionInitFixture.getTransactionInit();
        final String[] transactionInitData = merge(
            new String[] { fixture.getGatewayUrl() },
            fixture.getTransaction().toMap().values().toArray(String[]::new)
        );

        when(transaction.toArray()).thenReturn(transactionInitData);
        when(transaction.getHash()).thenReturn(hash);
        when(transaction.isHashPresent()).thenReturn(true);

        final boolean result = client.checkHash(transaction);

        assertEquals(value, result);
    }

    @ParameterizedTest
    @SuppressWarnings("unused")
    @MethodSource("checkHashProvider")
    public void checkHashThrowsHashNotReturnedException(String hash, boolean value) {
        final Transaction transaction = mock(TransactionInit.class);
        final TransactionInitRequest fixture = TransactionInitFixture.getTransactionInit();
        final String[] transactionInitData = merge(
            new String[] { fixture.getGatewayUrl() },
            fixture.getTransaction().toMap().values().toArray(String[]::new)
        );

        when(transaction.toArray()).thenReturn(transactionInitData);
        when(transaction.getHash()).thenReturn(hash);
        when(transaction.isHashPresent()).thenReturn(false);

        assertThrows(HashNotReturnedFromServerException.class, () -> client.checkHash(transaction));
    }

    @Test
    public void getItnRequestObject() {
        final ItnRequest itnRequest = AutopayClient.getItnRequestObject(
            ItnFixture.getItnInRequestBase64Encoded(), client.getSerializer());
        final Map<String, String> itnFixture = ItnFixture.getTransactionDataFromXml();

        assertNotNull(itnRequest);
        for (final Itn itn : itnRequest.getTransactions().transaction()) {
            assertEquals(itnFixture.get("remoteID"), itn.getRemoteID());
            assertEquals(itnFixture.get("amount"), itn.getAmount());
            assertEquals(itnFixture.get("currency"), itn.getCurrency());
            assertEquals(itnFixture.get("paymentDate"), itn.getPaymentDate());
            assertEquals(itnFixture.get("paymentStatus"), itn.getPaymentStatus());
            assertEquals(itnFixture.get("paymentStatusDetails"), itn.getPaymentStatusDetails());
        }
    }

    @Contract(" -> new")
    public @NotNull @Unmodifiable List<Arguments> checkHashProvider() {
        return List.of(
            Arguments.of("56507c9294e43e649e8726d271174297a165aedb858edb0414aadbc9632f17e7", true),
            Arguments.of("56507c9294e43e649e8726d271174297a165aedb858edb0414aadbc9632f1111", false)
        );
    }

    @Contract(" -> new")
    public @Unmodifiable List<Arguments> itnProvider() {
        return List.of(
            Arguments.of("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiP"),
            Arguments.of("nope"),
            Arguments.of("")
        );
    }

    @Contract(" -> new")
    public @NotNull @Unmodifiable List<Arguments> checkConfirmationProvider() {
        return List.of(
            Arguments.of(
                new Confirmation(
                    SERVICE_ID,
                    "123",
                    "df5f737f48bcef93361f590b460cc633b28f91710a60415527221f9cb90da52a"
                ),
                true
            ),
            Arguments.of(
                new Confirmation(
                    SERVICE_ID,
                    "123",
                    "cd4dd0eed6bfeb1fd0605076caf7b7774624af7cb67cd63b97425c26471d8100"
                ),
                false
            )
        );
    }
}
