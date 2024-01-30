package krystian.kryszczak.autopay.sdk;

import krystian.kryszczak.autopay.sdk.hash.HashType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class AutopayConfigurationTest extends BaseTestCase {
    private static AutopayConfiguration configuration;

    @BeforeAll
    public static void setUp() {
        configuration = AutopayConfiguration.builder()
            .setServiceId(SERVICE_ID)
            .setSharedKey(SHARED_KEY)
            .setHashAlgorithm(HASH_SHA256)
            .setHashSeparator(HASH_SEPARATOR)
            .build();
    }

    @Test
    public void testThrowsExceptionOnInvalidHashAlgorithm() {
        assertThrows(IllegalArgumentException.class, () ->
            AutopayConfiguration.builder()
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
            AutopayConfiguration.builder()
                .setServiceId(Integer.parseInt(WRONG_SERVICE_ID))
                .setSharedKey(SHARED_KEY)
                .setHashAlgorithm(HASH_SHA256)
                .setHashSeparator(HASH_SEPARATOR)
                .build()
        );
    }

    @Test
    public void testGetServiceIdReturnTypeAndValue() {
        assertEquals(SERVICE_ID, configuration.serviceId());
    }

    @Test
    public void testGetSharedKeyReturnTypeAndValue() {
        assertSame(SHARED_KEY, configuration.sharedKey());
    }

    @Test
    public void testGetHashAlgoReturnTypeAndValue() {
        assertSame(HASH_SHA256, configuration.hashAlgorithm());
    }

    @Test
    public void testGetHashSeparatorReturnTypeAndValue() {
        assertSame(HASH_SEPARATOR, configuration.hashSeparator());
    }
}
