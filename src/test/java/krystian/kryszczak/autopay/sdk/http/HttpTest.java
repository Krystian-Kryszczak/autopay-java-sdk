package krystian.kryszczak.autopay.sdk.http;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import fixtures.payway.PaywayListFixture;
import fixtures.regulation.RegulationListFixture;
import fixtures.transaction.TransactionBackgroundFixture;
import fixtures.transaction.TransactionInitFixture;
import krystian.kryszczak.autopay.sdk.BaseTestCase;
import krystian.kryszczak.autopay.sdk.common.Routes;
import krystian.kryszczak.autopay.sdk.hash.HashGenerator;
import krystian.kryszczak.autopay.sdk.http.client.HttpClient;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequest;
import krystian.kryszczak.autopay.sdk.payway.PaywayList;
import krystian.kryszczak.autopay.sdk.regulation.RegulationList;
import krystian.kryszczak.autopay.sdk.transaction.TransactionBackground;
import krystian.kryszczak.autopay.sdk.transaction.TransactionInit;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WireMockTest
public final class HttpTest extends BaseTestCase {
    private static final String IP_REGEX = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    private static HttpClient httpClient;

    @BeforeAll
    static void setUp() {
        httpClient = HttpClient.createDefault();
    }

    void configurePaymentForTransactionInitContinueResponseRoute() {
        stubFor(post(Routes.PAYMENT_ROUTE.getValue())
            .withFormParam("OrderID", matching("^\\d+$"))
            .withFormParam("Amount", matching("^\\d*\\.?\\d+$"))
            .withFormParam("Description", matching("^[0-9a-zA-Z\\s\\-]+$"))
            .withFormParam("Currency", matching("^[A-Z]{3}$"))
            .withFormParam("CustomerEmail", matching("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            .withFormParam("Hash", matching("^[0-9a-z]{64}$"))
            .withHeader("Content-Type", equalToIgnoreCase("application/x-www-form-urlencoded"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type","application/xml;charset=UTF-8")
                    .withBody(TransactionInitFixture.getTransactionInitContinueResponse())
            ));
    }

    void configurePaymentForTransactionInitResponseRoute() {
        stubFor(post(Routes.PAYMENT_ROUTE.getValue())
            .withFormParam("OrderID", matching("^\\d+$"))
            .withFormParam("Amount", matching("^\\d*\\.?\\d+$"))
            .withFormParam("Description", matching("^[0-9a-zA-Z\\s\\-]+$"))
            .withFormParam("GatewayID", matching("^\\d+$"))
            .withFormParam("Currency", matching("^[A-Z]{3}$"))
            .withFormParam("CustomerEmail", matching("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            .withFormParam("CustomerIP", matching(IP_REGEX))
            .withFormParam("Title", matching("^[0-9a-zA-Z\\s\\-]+$"))
            .withFormParam("Hash", matching("^[0-9a-z]{64}$"))
            .withHeader("Content-Type", equalToIgnoreCase("application/x-www-form-urlencoded"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type","application/xml;charset=UTF-8")
                    .withBody(TransactionInitFixture.getTransactionInitResponse())
            ));
    }

    void configurePaymentForTransactionBackgroundResponseRoute() {
        stubFor(post(Routes.PAYMENT_ROUTE.getValue())
            .withFormParam("OrderID", matching("^\\d+$"))
            .withFormParam("Amount", matching("^\\d*\\.?\\d+$"))
            .withFormParam("Description", matching("^[0-9a-zA-Z\\s\\-]+$"))
            .withFormParam("GatewayID", matching("^\\d+$"))
            .withFormParam("Currency", matching("^[A-Z]{3}$"))
            .withFormParam("CustomerEmail", matching("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            .withFormParam("CustomerIP", matching(IP_REGEX))
            .withFormParam("Title", matching("^[0-9a-zA-Z\\s\\-]+$"))
            .withFormParam("Hash", matching("^[0-9a-z]{64}$"))
            .withHeader("Content-Type", equalToIgnoreCase("application/x-www-form-urlencoded"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type","application/xml;charset=UTF-8")
                    .withBody(TransactionBackgroundFixture.getTransactionBackgroundResponse())
            ));
    }

    void configurePaymentForPaywayFormResponseRoute() {
        stubFor(post(Routes.PAYMENT_ROUTE.getValue())
            .withFormParam("OrderID", matching("^\\d+$"))
            .withFormParam("Amount", matching("^\\d*\\.?\\d+$"))
            .withFormParam("Description", matching("^[0-9a-zA-Z\\s\\-]+$"))
            .withFormParam("GatewayID", equalTo("1500"))
            .withFormParam("Currency", matching("^[A-Z]{3}$"))
            .withFormParam("CustomerEmail", matching("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
            .withFormParam("CustomerIP", matching(IP_REGEX))
            .withFormParam("Title", matching("^[0-9a-zA-Z\\s\\-]+$"))
            .withFormParam("Hash", matching("^[0-9a-z]{64}$"))
            .withHeader("Content-Type", equalToIgnoreCase("application/x-www-form-urlencoded"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type","application/xml;charset=UTF-8")
                    .withBody(TransactionBackgroundFixture.getPaywayFormResponse())
            ));
    }

    void configureRegulationsRoute() {
        stubFor(post(Routes.GET_REGULATIONS_ROUTE.getValue())
            .withFormParam("ServiceID", matching("^\\d+$"))
            .withFormParam("MessageID", matching("^[0-9a-z]{32}$"))
            .withFormParam("Hash", matching("^[0-9a-z]{64}$"))
            .withHeader("Content-Type", equalToIgnoreCase("application/x-www-form-urlencoded"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type","application/xml;charset=UTF-8")
                    .withBody(RegulationListFixture.getRegulationListResponse())
            ));
    }

    void configurePaywayListRoute() {
        stubFor(post(Routes.PAYWAY_LIST_ROUTE.getValue())
            .withFormParam("ServiceID", matching("^\\d+$"))
            .withFormParam("MessageID", matching("^[0-9a-z]{32}$"))
            .withFormParam("Hash", matching("^[0-9a-z]{64}$"))
            .withHeader("Content-Type", equalToIgnoreCase("application/x-www-form-urlencoded"))
            .willReturn(
                aResponse()
                    .withHeader("Content-Type","application/xml;charset=UTF-8")
                    .withBody(PaywayListFixture.getPaywayListResponse())
            ));
    }

    @Test
    void httpClientPostReturnsTransactionInitContinueResponseFixture(final @NotNull WireMockRuntimeInfo runtimeInfo) {
        configurePaymentForTransactionInitContinueResponseRoute();

        // given
        final TransactionInit transaction = TransactionInit.builder()
            .orderID("123")
            .amount("1.20")
            .description("Transakcja 123-123")
            .currency("PLN")
            .customerEmail("test@hostname.domain")
            .build();

        final String hash = HashGenerator.generateHash(transaction.toArray(), getTestConfiguration());
        transaction.setHash(hash);

        final HttpRequest<TransactionInit> request = new HttpRequest<>(
            URI.create(runtimeInfo.getHttpBaseUrl()).resolve(Routes.PAYMENT_ROUTE.getValue()),
            Map.of(),
            transaction
        );

        // when
        final String result = Flux.from(httpClient.post(request)).blockFirst();

        // then
        assertEquals(TransactionInitFixture.getTransactionInitContinueResponse(), result);
    }

    @Test
    void httpClientPostReturnsTransactionInitResponseFixture(final @NotNull WireMockRuntimeInfo runtimeInfo) {
        configurePaymentForTransactionInitResponseRoute();

        // given
        final TransactionInit transaction = TransactionInit.builder()
            .orderID("123")
            .amount("1.20")
            .description("Transakcja 123-123")
            .gatewayID(1500)
            .currency("PLN")
            .customerEmail("test@hostname.domain")
            .customerIP("127.0.0.1")
            .title("Test")
            .build();

        final String hash = HashGenerator.generateHash(transaction.toArray(), getTestConfiguration());
        transaction.setHash(hash);

        final HttpRequest<TransactionInit> request = new HttpRequest<>(
            URI.create(runtimeInfo.getHttpBaseUrl()).resolve(Routes.PAYMENT_ROUTE.getValue()),
            Map.of(),
            transaction
        );

        // when
        final String result = Flux.from(httpClient.post(request)).blockFirst();

        // then
        assertEquals(TransactionInitFixture.getTransactionInitResponse(), result);
    }

    @Test
    void httpClientPostReturnsTransactionBackgroundResponseFixture(final @NotNull WireMockRuntimeInfo runtimeInfo) {
        configurePaymentForTransactionBackgroundResponseRoute();

        // given
        final TransactionBackground transaction = TransactionBackground.builder()
            .orderID("12345")
            .amount("5.12")
            .description("Test transaction 12345")
            .gatewayID(21)
            .currency("PLN")
            .customerEmail("test@test.test")
            .customerIP("127.0.0.1")
            .title("Test")
            .validityTime(LocalDateTime.now().plusHours(5))
            .linkValidityTime(LocalDateTime.now().plusHours(5))
            .build();

        final String hash = HashGenerator.generateHash(transaction.toArray(), getTestConfiguration());
        transaction.setHash(hash);

        final HttpRequest<TransactionBackground> request = new HttpRequest<>(
            URI.create(runtimeInfo.getHttpBaseUrl()).resolve(Routes.PAYMENT_ROUTE.getValue()),
            Map.of(),
            transaction
        );

        // when
        final String result = Flux.from(httpClient.post(request)).blockFirst();

        // then
        assertEquals(TransactionBackgroundFixture.getTransactionBackgroundResponse(), result);
    }

    @Test
    void httpClientPostReturnsPaywayFormResponseFixture(final @NotNull WireMockRuntimeInfo runtimeInfo) {
        configurePaymentForPaywayFormResponseRoute();

        // given
        final TransactionBackground transaction = TransactionBackground.builder()
            .orderID("12345")
            .amount("5.12")
            .description("Test transaction 12345")
            .gatewayID(1500)
            .currency("PLN")
            .customerEmail("test@test.test")
            .customerIP("127.0.0.1")
            .title("Test")
            .validityTime(LocalDateTime.now().plusHours(5))
            .linkValidityTime(LocalDateTime.now().plusHours(5))
            .build();

        final String hash = HashGenerator.generateHash(transaction.toArray(), getTestConfiguration());
        transaction.setHash(hash);

        final HttpRequest<TransactionBackground> request = new HttpRequest<>(
            URI.create(runtimeInfo.getHttpBaseUrl()).resolve(Routes.PAYMENT_ROUTE.getValue()),
            Map.of(),
            transaction
        );

        // when
        final String result = Flux.from(httpClient.post(request)).blockFirst();

        // then
        assertEquals(TransactionBackgroundFixture.getPaywayFormResponse(), result);
    }

    @Test
    void httpClientPostReturnsRegulationListResponseFixture(final @NotNull WireMockRuntimeInfo runtimeInfo) {
        configureRegulationsRoute();

        // given
        final HttpRequest<RegulationList> request = new HttpRequest<>(
            URI.create(runtimeInfo.getHttpBaseUrl()).resolve(Routes.GET_REGULATIONS_ROUTE.getValue()),
            Map.of(),
            RegulationList.create(0, "7060232c5e531a4e271c39090b6f751e", getTestConfiguration())
        );

        // when
        final String result = Flux.from(httpClient.post(request)).blockFirst();

        // then
        assertEquals(RegulationListFixture.getRegulationListResponse(), result);
    }

    @Test
    void httpClientPostReturnsPaywayListResponseFixture(final @NotNull WireMockRuntimeInfo runtimeInfo) {
        configurePaywayListRoute();

        // given
        final HttpRequest<PaywayList> request = new HttpRequest<>(
            URI.create(runtimeInfo.getHttpBaseUrl()).resolve(Routes.PAYWAY_LIST_ROUTE.getValue()),
            Map.of(),
            PaywayList.create(0, "683d1f551968085a127167301b50614b", getTestConfiguration())
        );

        // when
        final String result = Flux.from(httpClient.post(request)).blockFirst();

        // then
        assertEquals(PaywayListFixture.getPaywayListResponse(), result);
    }
}
