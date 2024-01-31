package fixtures.transaction;

import krystian.kryszczak.autopay.sdk.transaction.TransactionInit;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionInitRequest;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;

import static fixtures.Fixtures.FIXTURES_FOLDER_PATH;

public final class TransactionInitFixture {
    public static @NotNull TransactionInitRequest getTransactionInitContinue() {
        return TransactionInitRequest.builder()
            .setGatewayUrl("https://pay-accept.bm.pl")
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
            .setGatewayUrl("https://pay-accept.bm.pl")
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

    @SneakyThrows
    public static String getTransactionInitContinueResponse() {
        return Files.readString(Path.of(FIXTURES_FOLDER_PATH + "transaction/TransactionInitContinueResponse.xml"));
    }

    @SneakyThrows
    public static String getTransactionInitResponse() {
        return Files.readString(Path.of(FIXTURES_FOLDER_PATH + "transaction/TransactionInitResponse.xml"));
    }
}
