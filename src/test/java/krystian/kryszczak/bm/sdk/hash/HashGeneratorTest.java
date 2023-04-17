package krystian.kryszczak.bm.sdk.hash;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashGeneratorTest {

    private static final BlueMediaConfiguration configuration = BlueMediaConfiguration.builder()
        .setServiceId(2)
        .setSharedKey("2test2")
        .setHashAlgorithm(HashType.SHA256)
        .setHashSeparator("|")
        .build();

    @Test
    void hashGeneratorTest() {
        final String hash = HashGenerator.instance.generateHash(
            new Object[] {
                configuration.getServiceId(),
                100, // OrderID
                "1.50", // Amount
            },
            configuration
        );

        assertEquals("2ab52e6918c6ad3b69a8228a2ab815f11ad58533eeed963dd990df8d8c3709d1", hash);
    }
}
