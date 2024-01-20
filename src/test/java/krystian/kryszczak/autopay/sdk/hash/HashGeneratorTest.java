package krystian.kryszczak.autopay.sdk.hash;

import krystian.kryszczak.autopay.sdk.BaseTestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class HashGeneratorTest extends BaseTestCase {
    private static final int ORDER_ID = 123;
    private static final String AMOUNT = "1.20";

    @Test
    void testGenerateHashReturnsExpectedHash() {
        final var data = new Object[] {
            2,
            ORDER_ID,
            AMOUNT
        };

        final String hash = HashGenerator.generateHash(data, getConfigurationStub());

        assertEquals("45f8aeeb99c37571ab1c9d952eb5d92ad4017bc6faaf16a708b995c8ffb8bc44", hash);
    }
}
