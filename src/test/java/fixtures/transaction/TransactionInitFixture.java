package fixtures.transaction;

import krystian.kryszczak.autopay.sdk.transaction.TransactionInit;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionInitRequest;
import org.jetbrains.annotations.NotNull;

import static fixtures.Fixtures.readFixtureFile;

public final class TransactionInitFixture {
    public static @NotNull TransactionInitRequest getTransactionInitContinue() {
        return TransactionInitRequest.builder()
            .setGatewayUrl("https://testpay.autopay.eu")
            .setTransaction(
                TransactionInit.builder()
                    .orderID("123")
                    .amount("1.20")
                    .description("Transakcja 123-123")
                    .gatewayID(0)
                    .currency("PLN")
                    .customerEmail("test@hostname.domain")
                    .build()
            ).build();
    }

    public static @NotNull TransactionInitRequest getTransactionInit() {
        return TransactionInitRequest.builder()
            .setGatewayUrl("https://testpay.autopay.eu")
            .setTransaction(
                TransactionInit.builder()
                    .orderID("186-1590996507")
                    .amount("22.94")
                    .description("Transakcja testowa")
                    .gatewayID(1500)
                    .currency("PLN")
                    .customerEmail("test@test.test")
                    .customerIP("127.0.0.1")
                    .title("Test")
                    .build()
            ).build();
    }

    public static @NotNull String getTransactionInitContinueResponse() {
        return readFixtureFile("transaction/TransactionInitContinueResponse.xml");
    }

    public static @NotNull String getTransactionInitResponse() {
        return readFixtureFile("transaction/TransactionInitResponse.xml");
    }
}
