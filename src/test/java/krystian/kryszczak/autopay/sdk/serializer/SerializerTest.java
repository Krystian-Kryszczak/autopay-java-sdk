package krystian.kryszczak.autopay.sdk.serializer;

import krystian.kryszczak.autopay.sdk.transaction.TransactionInit;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionInitRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class SerializerTest {
    private final TransactionInitRequest transactionRequest = TransactionInitRequest.builder()
        .setGatewayUrl("https://accept-pay.bm.pl")
        .setTransaction(
            TransactionInit.builder()
                .orderID("123-123")
                .amount("1.20")
                .description("Transakcja 123-123")
                .gatewayID(0)
                .build()
        ).build();

    private static Serializer serializer;

    @BeforeAll
    public static void setUp() {
        serializer = new XmlSerializer();
    }

    @Test
    public void serializedTransactionRequestAndDeserializedTransactionRequestAreEquals() {
        final String transactionData = serializer.serialize(this.transactionRequest);
        assertNotNull(transactionData);

        final var transactionRequest = serializer.deserialize(transactionData, TransactionInitRequest.class);
        assertNotNull(transactionRequest);
        assertEquals(this.transactionRequest, transactionRequest);
    }
}
