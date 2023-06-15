package krystian.kryszczak.bm.sdk;

import krystian.kryszczak.bm.sdk.hash.HashType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class BlueMediaConfigurationTest {
    private static final int SERVICE_ID = 123456;
    private static final String WRONG_SERVICE_ID = "123456789012";

    private static final String SHARED_KEY = "QCBm3N0oFjzQAWsTIVN8mPLK12TW6HU6InSfjvnF";

    private static final String WRONG_HASH_ALGO = "wrong_algorithm";
    private static final String HASH_SEPARATOR = "|";

    private static final String HASH_SHA256 = "SHA256";

    private static BlueMediaConfiguration configuration;

    @BeforeAll
    public static void setUp() {
        configuration = BlueMediaConfiguration.builder()
            .setServiceId(SERVICE_ID)
            .setSharedKey(SHARED_KEY)
            .setHashAlgorithm(HashType.valueOf(HASH_SHA256))
            .setHashSeparator(HASH_SEPARATOR)
            .build();
    }

    @Test
    public void testThrowsExceptionOnInvalidHashAlgorithm() {
        assertThrows(IllegalArgumentException.class, () ->
            BlueMediaConfiguration.builder()
                .setServiceId(SERVICE_ID)
                .setSharedKey(SHARED_KEY)
                .setHashAlgorithm(HashType.valueOf(WRONG_HASH_ALGO))
                .setHashSeparator(HASH_SEPARATOR)
                .build()
        );
    }

    @Test
    public void testThrowsExceptionOnInvalidServiceId() {
        assertThrows(IllegalArgumentException.class, () ->
            BlueMediaConfiguration.builder()
                .setServiceId(Integer.parseInt(WRONG_SERVICE_ID))
                .setSharedKey(SHARED_KEY)
                .setHashAlgorithm(HashType.valueOf(HASH_SHA256))
                .setHashSeparator(HASH_SEPARATOR)
                .build()
        );
    }

    @Test
    public void testGetServiceIdReturnTypeAndValue() {
        assertEquals(SERVICE_ID, configuration.getServiceId());
    }

    @Test
    public void testGetSharedKeyReturnTypeAndValue() {
        assertSame(SHARED_KEY, configuration.getSharedKey());
    }

    @Test
    public void testGetHashAlgoReturnTypeAndValue() {
        assertSame(HASH_SHA256, configuration.getHashAlgorithm().name());
    }

    @Test
    public void testGetHashSeparatorReturnTypeAndValue() {
        assertSame(HASH_SEPARATOR, configuration.getHashSeparator());
    }
}
