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
    public void throwsExceptionOnInvalidHashAlgorithm() {
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
    public void throwsExceptionOnInvalidServiceId() {
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
    public void getServiceIdReturnTypeAndValue() {
        assertEquals(SERVICE_ID, configuration.serviceId());
    }

    @Test
    public void getSharedKeyReturnTypeAndValue() {
        assertSame(SHARED_KEY, configuration.sharedKey());
    }

    @Test
    public void getHashAlgoReturnTypeAndValue() {
        assertSame(HASH_SHA256, configuration.hashAlgorithm());
    }

    @Test
    public void getHashSeparatorReturnTypeAndValue() {
        assertSame(HASH_SEPARATOR, configuration.hashSeparator());
    }
}
