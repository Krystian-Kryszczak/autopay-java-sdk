package krystian.kryszczak.bm.sdk.serializer;

import krystian.kryszczak.bm.sdk.transaction.request.TransactionInitRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class SerializerTest {
    private final Map<String, Object> transactionData = Map.of(
    "gatewayUrl", "https://accept-pay.bm.pl",
    "transaction", Map.of(
            "orderID", "123-123",
            "amount", "1.20",
            "description", "Transakcja 123-123",
            "gatewayID", 0
        )
    );

    private static Serializer serializer;

    @BeforeAll
    public static void setUp() {
        serializer = new XmlSerializer();
    }

    @Test
    public void testSerializeTransactionDataReturnsTransactionRequest() {
        final var transactionRequest = serializer.fromMap(transactionData, TransactionInitRequest.class);
        assert transactionRequest != null;
        assertEquals(transactionData.get("gatewayUrl"), transactionRequest.getGatewayUrl());
    }
}
