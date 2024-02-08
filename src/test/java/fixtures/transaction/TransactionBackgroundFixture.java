package fixtures.transaction;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import krystian.kryszczak.autopay.sdk.transaction.TransactionBackground;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionBackgroundRequest;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static fixtures.Fixtures.FIXTURES_FOLDER_PATH;

public final class TransactionBackgroundFixture {
    public static @NotNull TransactionBackgroundRequest getTransactionBackground() {
        return TransactionBackgroundRequest.builder()
            .setGatewayUrl("https://pay-accept.bm.pl")
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

    @SneakyThrows
    public static String getTransactionBackgroundResponse() {
        return Files.readString(Path.of(FIXTURES_FOLDER_PATH + "transaction/TransactionBackgroundResponse.xml"));
    }

    @SneakyThrows
    public static @NotNull Map<String, String> getTransactionBackgroundResponseData() {
        final Map<String, String> dst = new HashMap<>();
        new XmlMapper().readTree(getTransactionBackgroundResponse()).fields()
            .forEachRemaining(entry -> dst.put(entry.getKey(), entry.getValue().asText()));
        return dst;
    }

    @SneakyThrows
    public static String getPaywayFormResponse() {
        return Files.readString(Path.of(FIXTURES_FOLDER_PATH + "transaction/PaywayFormResponse.txt"));
    }
}
