package krystian.kryszczak.autopay.sdk.hash;

import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.BaseTestCase;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class HashGeneratorTest extends BaseTestCase {
    @Test
    public void testGenerateHashReturnsExpectedHash() {
        final Object[] data = new Object[] {
            getTestConfiguration().serviceId(),
            123,
            1.20
        };

        final String exceptedHash = generateHash(data);

        final String hash = HashGenerator.generateHash(data, getTestConfiguration());

        assertEquals(exceptedHash, hash);
    }

    private String generateHash(Object[] array) {
        final AutopayConfiguration configuration = getTestConfiguration();
        final String hashSeparator = configuration.hashSeparator();
        final String data = Arrays.stream(array).map(Object::toString)
            .collect(Collectors.joining(hashSeparator)) + hashSeparator + configuration.sharedKey();

        return HashGenerator.encode(data, configuration.hashAlgorithm().toString());
    }
}
