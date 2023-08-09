package krystian.kryszczak.bm.sdk;

import io.reactivex.rxjava3.core.Maybe;
import krystian.kryszczak.bm.sdk.confirmation.Confirmation;
import krystian.kryszczak.bm.sdk.http.HttpClient;
import krystian.kryszczak.bm.sdk.http.HttpRequest;
import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import krystian.kryszczak.bm.sdk.itn.Itn;
import krystian.kryszczak.bm.sdk.itn.response.ItnResponse;
import krystian.kryszczak.bm.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.bm.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.bm.sdk.transaction.TransactionBackground;
import krystian.kryszczak.bm.sdk.transaction.TransactionContinue;
import krystian.kryszczak.bm.sdk.transaction.TransactionInit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.xmlunit.assertj.XmlAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class BlueMediaClientTest extends BaseTestCase {
    private static BlueMediaClient client;
    private static HttpClient httpClient;

    @BeforeAll
    public static void setUp() {
        httpClient = mock(HttpClient.class);

        client = new BlueMediaClient(
            BlueMediaConfiguration.builder()
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
            Maybe.just(fixtures.transaction.TransactionBackground.getTransactionBackgroundResponse())
        );

        final var result = client.doTransactionBackground(fixtures.transaction.TransactionBackground.getTransactionBackground());

        assertInstanceOf(Maybe.class, result);
        assertInstanceOf(TransactionBackground.class, result.blockingGet());

        final var transactionBackground = result.blockingGet();
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
            Maybe.just(fixtures.transaction.TransactionInit.getTransactionInitContinueResponse())
        );

        final var result = client.doTransactionInit(fixtures.transaction.TransactionInit.getTransactionInitContinue());

        assertInstanceOf(Maybe.class, result);
        assertInstanceOf(TransactionContinue.class, result.blockingGet());
    }

    @Test
    public void testDoTransactionInitReturnsTransactionData() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Maybe.just(fixtures.transaction.TransactionInit.getTransactionInitResponse())
        );

        final var result = client.doTransactionInit(fixtures.transaction.TransactionInit.getTransactionInit());

        assertInstanceOf(Maybe.class, result);
        assertInstanceOf(TransactionInit.class, result.blockingGet());
    }

    @Test
    public void testDoItnInReturnsItnData() {
        final var result = client.doItnIn(fixtures.itn.Itn.getItnInRequest());

        final Itn itn = result.blockingGet();
        final var itnFixture = fixtures.itn.Itn.getTransactionXml();

        assertInstanceOf(Maybe.class, result);
        assertInstanceOf(Itn.class, result.blockingGet());
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
        final var itnIn = client.doItnIn(fixtures.itn.Itn.getItnInRequest());
        final var result = client.doItnInResponse(itnIn.blockingGet(), true);
        assertInstanceOf(ItnResponse.class, result.blockingGet());
        assertThat(fixtures.itn.Itn.getItnResponse())
            .and(result.blockingGet().toXml())
            .ignoreWhitespace()
            .areIdentical();
    }

    @Test
    public void testGetPaywayList() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Maybe.just(fixtures.payway.PaywayList.getPaywayListResponse())
        );

        final var result = client.getPaywayList(GATEWAY_URL);

        assertInstanceOf(Maybe.class, result);
        assertInstanceOf(PaywayListResponse.class, result.blockingGet());
    }

    @Test
    public void testGetRegulationList() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Maybe.just(fixtures.regulation.RegulationList.getRegulationListResponse())
        );

        final var result = client.getRegulationList(GATEWAY_URL);

        assertInstanceOf(Maybe.class, result);
        assertInstanceOf(RegulationListResponse.class, result.blockingGet());
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
        final var itn = BlueMediaClient.getItnObject(fixtures.itn.Itn.getItnInRequest());
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

    public static List<Arguments> checkHashProvider() {
        return List.of(
            Arguments.of("56507c9294e43e649e8726d271174297a165aedb858edb0414aadbc9632f17e7", true),
            Arguments.of("56507c9294e43e649e8726d271174297a165aedb858edb0414aadbc9632f1111", false)
        );
    }

    public static List<Arguments> itnProvider() {
        return List.of(
            Arguments.of("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiP"),
            Arguments.of("nope"),
            Arguments.of("")
        );
    }

    public static List<Arguments> checkConfirmationProvider() {
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
