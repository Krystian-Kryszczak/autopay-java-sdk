package fixtures.transaction;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import krystian.kryszczak.autopay.sdk.transaction.TransactionBackground;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionBackgroundRequest;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static fixtures.Fixtures.readFixtureFile;

public final class TransactionBackgroundFixture {
    public static @NotNull TransactionBackgroundRequest getTransactionBackground() {
        return TransactionBackgroundRequest.builder()
            .setGatewayUrl("https://testpay.autopay.eu")
            .setTransaction(
                TransactionBackground.builder()
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
                    .build()
            ).build();
    }

    public static @NotNull String getTransactionBackgroundResponse() {
        return readFixtureFile("transaction/TransactionBackgroundResponse.xml");
    }

    @SneakyThrows
    public static @NotNull Map<String, String> getTransactionBackgroundResponseData() {
        final Map<String, String> dst = new HashMap<>();
        new XmlMapper().readTree(getTransactionBackgroundResponse()).fields()
            .forEachRemaining(entry -> dst.put(entry.getKey(), entry.getValue().asText()));
        return dst;
    }

    public static @NotNull String getPaywayFormResponse() {
        return readFixtureFile("transaction/PaywayFormResponse.txt");
    }
}
