package krystian.kryszczak.autopay.sdk;

import krystian.kryszczak.autopay.sdk.confirmation.Confirmation;
import krystian.kryszczak.autopay.sdk.http.client.HttpClient;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequest;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequestBody;
import krystian.kryszczak.autopay.sdk.itn.Itn;
import krystian.kryszczak.autopay.sdk.itn.response.ItnResponse;
import krystian.kryszczak.autopay.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.autopay.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.autopay.sdk.transaction.Transaction;
import krystian.kryszczak.autopay.sdk.transaction.TransactionBackground;
import krystian.kryszczak.autopay.sdk.transaction.TransactionContinue;
import krystian.kryszczak.autopay.sdk.transaction.TransactionInit;
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
            httpClient
        );
    }

    @Test
    public void testGetTransactionRedirectReturnsRedirectView() {
        final String result = client.getTransactionRedirect(fixtures.transaction.TransactionInit.getTransactionInitContinue());

        assertNotNull(result);
        assertFalse(result.isBlank());
    }

    @ParameterizedTest
    @MethodSource("checkConfirmationProvider")
    public void testDoConfirmationCheckReturnsStatus(Confirmation data, boolean result) {
        final var check = client.doConfirmationCheck(data);

        assertSame(result, check);
    }

    @Test
    public void testDoTransactionBackgroundReturnsTransactionData() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(fixtures.transaction.TransactionBackground.getTransactionBackgroundResponse())
        );

        final var result = Mono.fromDirect(
            client.doTransactionBackground(fixtures.transaction.TransactionBackground.getTransactionBackground())
        );

        assertInstanceOf(Mono.class, result);
        assertInstanceOf(TransactionBackground.class, result.block());

        final var transactionBackground = result.block();
        final var transactionBackgroundFixture = fixtures.transaction.TransactionBackground.getTransactionBackgroundResponseData();

        assertEquals(transactionBackgroundFixture.get("receiverNRB"), transactionBackground.getReceiverNRB());
        assertEquals(transactionBackgroundFixture.get("receiverName"), transactionBackground.getReceiverName());
        assertEquals(transactionBackgroundFixture.get("receiverAddress"), transactionBackground.getReceiverAddress());
        assertEquals(transactionBackgroundFixture.get("orderID"), transactionBackground.getOrderID());
        assertEquals(transactionBackgroundFixture.get("amount"), transactionBackground.getAmount());
        assertEquals(transactionBackgroundFixture.get("currency"), transactionBackground.getCurrency());
        assertEquals(transactionBackgroundFixture.get("title"), transactionBackground.getTitle());
        assertEquals(transactionBackgroundFixture.get("remoteID"), transactionBackground.getRemoteID());
        assertEquals(transactionBackgroundFixture.get("bankHref"), transactionBackground.getBankHref());
        assertEquals(transactionBackgroundFixture.get("returnURL"), transactionBackground.getReturnURL());
    }

    @Test
    public void testDoTransactionInitReturnsTransactionContinueData() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(fixtures.transaction.TransactionInit.getTransactionInitContinueResponse())
        );

        final var result = Mono.fromDirect(client.doTransactionInit(fixtures.transaction.TransactionInit.getTransactionInitContinue()));

        assertInstanceOf(Mono.class, result);
        assertInstanceOf(TransactionContinue.class, result.block());
    }

    @Test
    public void testDoTransactionInitReturnsTransactionData() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(fixtures.transaction.TransactionInit.getTransactionInitResponse())
        );

        final var result = Mono.fromDirect(client.doTransactionInit(fixtures.transaction.TransactionInit.getTransactionInit()));

        assertInstanceOf(Mono.class, result);
        final Transaction transaction = result.block();
        assertNotNull(transaction);
        assertInstanceOf(TransactionInit.class, transaction);
    }

    @Test
    public void testDoItnInReturnsItnData() {
        final var result = Mono.fromDirect(client.doItnIn(fixtures.itn.Itn.getItnInRequest()));

        final Itn itn = result.block();
        final var itnFixture = fixtures.itn.Itn.getTransactionXml();

        assertInstanceOf(Mono.class, result);
        assertInstanceOf(Itn.class, result.block());
        assertEquals(itnFixture.get("remoteID"), itn.getRemoteID());
        assertEquals(itnFixture.get("amount"), itn.getAmount());
        assertEquals(itnFixture.get("currency"), itn.getCurrency());
        assertEquals(itnFixture.get("paymentDate"), itn.getPaymentDate());
        assertEquals(itnFixture.get("paymentStatus"), itn.getPaymentStatus());
        assertEquals(itnFixture.get("paymentStatusDetails"), itn.getPaymentStatusDetails());
    }

    @ParameterizedTest
    @MethodSource("itnProvider")
    public void testDoItnInThrowsExceptionOnWrongBase64(String itn) {
        assertThrows(IllegalArgumentException.class, () -> client.doItnIn(itn));
    }

    @Test
    public void testDoItnResponseReturnsConfirmationResponse() {
        final var itnIn = Mono.fromDirect(client.doItnIn(fixtures.itn.Itn.getItnInRequest()));
        final var result = Mono.fromDirect(client.doItnInResponse(itnIn.block(), true));
        assertInstanceOf(ItnResponse.class, result.block());
        assertThat(fixtures.itn.Itn.getItnResponse())
            .and(result.block().toXml())
            .ignoreWhitespace()
            .areIdentical();
    }

    @Test
    public void testGetPaywayList() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(fixtures.payway.PaywayList.getPaywayListResponse())
        );

        final var result = Mono.fromDirect(client.getPaywayList(GATEWAY_URL));

        assertInstanceOf(Mono.class, result);
        assertInstanceOf(PaywayListResponse.class, result.block());
    }

    @Test
    public void testGetRegulationList() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(fixtures.regulation.RegulationList.getRegulationListResponse())
        );

        final var result = Mono.fromDirect(client.getRegulationList(GATEWAY_URL));

        assertInstanceOf(Mono.class, result);
        assertInstanceOf(RegulationListResponse.class, result.block());
    }

    @ParameterizedTest
    @MethodSource("checkHashProvider")
    public void testCheckHashReturnsExpectedValue(String hash, boolean value) {
        final var transaction = mock(TransactionInit.class);
        final var transactionInitData = fixtures.transaction.TransactionInit.getTransactionInit().getTransaction().toArray();

        when(transaction.toArray()).thenReturn(transactionInitData);
        when(transaction.getHash()).thenReturn(hash);
        when(transaction.isHashPresent()).thenReturn(true);

        final var result = client.checkHash(transaction);

        assertEquals(value, result);
    }

    @Test
    public void testGetItnObject() {
        final var itn = AutopayClient.getItnObject(fixtures.itn.Itn.getItnInRequest());
        final var itnFixture = fixtures.itn.Itn.getTransactionXml();

        assertNotNull(itn);
        assertInstanceOf(Itn.class, itn);
        assertEquals(itnFixture.get("remoteID"), itn.getRemoteID());
        assertEquals(itnFixture.get("amount"), itn.getAmount());
        assertEquals(itnFixture.get("currency"), itn.getCurrency());
        assertEquals(itnFixture.get("paymentDate"), itn.getPaymentDate());
        assertEquals(itnFixture.get("paymentStatus"), itn.getPaymentStatus());
        assertEquals(itnFixture.get("paymentStatusDetails"), itn.getPaymentStatusDetails());
    }

    @Contract(" -> new")
    public static @NotNull @Unmodifiable List<Arguments> checkHashProvider() {
        return List.of(
            Arguments.of("56507c9294e43e649e8726d271174297a165aedb858edb0414aadbc9632f17e7", true),
            Arguments.of("56507c9294e43e649e8726d271174297a165aedb858edb0414aadbc9632f1111", false)
        );
    }

    @Contract(" -> new")
    public static @Unmodifiable List<Arguments> itnProvider() {
        return List.of(
            Arguments.of("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiP"),
            Arguments.of("nope"),
            Arguments.of("")
        );
    }

    @Contract(" -> new")
    public static @NotNull @Unmodifiable List<Arguments> checkConfirmationProvider() {
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
