package krystian.kryszczak.autopay.sdk;

import fixtures.itn.ItnFixture;
import fixtures.payway.PaywayListFixture;
import fixtures.regulation.RegulationListFixture;
import fixtures.transaction.TransactionBackgroundFixture;
import fixtures.transaction.TransactionInitFixture;
import krystian.kryszczak.autopay.sdk.http.client.HttpClient;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequest;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequestBody;
import krystian.kryszczak.autopay.sdk.itn.request.ItnRequest;
import krystian.kryszczak.autopay.sdk.itn.response.ItnResponse;
import krystian.kryszczak.autopay.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.autopay.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.autopay.sdk.serializer.Serializer;
import krystian.kryszczak.autopay.sdk.transaction.Transaction;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionBackgroundRequest;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionInitRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class ReactorAutopayClientTest extends BaseTestCase {
    private static ReactorAutopayClient client;
    private static HttpClient httpClient;

    @BeforeAll
    public static void setUp() {
        httpClient = mock(HttpClient.class);

        client = new ReactorAutopayClient(AutopayConfiguration.builder()
            .setServiceId(SERVICE_ID)
            .setSharedKey(SHARED_KEY)
            .build(),
            httpClient,
            Serializer.createDefault()
        );
    }

    @Test
    public void doTransactionBackgroundReturnsNotNullTransaction() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(TransactionBackgroundFixture.getTransactionBackgroundResponse())
        );

        // given
        final TransactionBackgroundRequest request = TransactionBackgroundFixture.getTransactionBackground();

        // when
        final Mono<? extends Transaction> result = client.doTransactionBackground(request);

        // then
        assertNotNull(assertDoesNotThrow(() -> result.block()));
    }

    @Test
    public void doTransactionInitReturnsNotNullTransaction() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(TransactionInitFixture.getTransactionInitContinueResponse())
        );

        // given
        final TransactionInitRequest request = TransactionInitFixture.getTransactionInitContinue();

        // when
        final Mono<? extends Transaction> result = client.doTransactionInit(request);

        // then
        assertNotNull(assertDoesNotThrow(() -> result.block()));
    }

    @Test
    public void doItnInResponseReturnsNotNullItnResponse() {
        // given
        final ItnRequest itnRequest = assertDoesNotThrow(() -> client.doItnIn(ItnFixture.getItnInRequestBase64Encoded()));

        // when
        final Mono<ItnResponse> result = client.doItnInResponse(itnRequest, itn -> true);

        // then
        assertNotNull(assertDoesNotThrow(() -> result.block()));
    }

    @Test
    public void getPaywayListReturnsNotNullPaywayListResult() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(PaywayListFixture.getPaywayListResponse())
        );

        // when
        final Mono<PaywayListResponse> result = client.getPaywayList(GATEWAY_URL);

        // then
        assertNotNull(assertDoesNotThrow(() -> result.block()));
    }

    @Test
    public void getRegulationListReturnsNotNullRegulationListResult() {
        when(httpClient.post((HttpRequest<? extends HttpRequestBody>) notNull())).thenReturn(
            Mono.just(RegulationListFixture.getRegulationListResponse())
        );

        // when
        final Mono<RegulationListResponse> result = client.getRegulationList(GATEWAY_URL);

        // then
        assertNotNull(assertDoesNotThrow(() -> result.block()));
    }
}
