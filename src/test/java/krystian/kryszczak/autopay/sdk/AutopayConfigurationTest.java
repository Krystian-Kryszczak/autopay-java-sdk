package krystian.kryszczak.autopay.sdk;

import krystian.kryszczak.autopay.sdk.hash.HashType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class AutopayConfigurationTest {
    private static final int SERVICE_ID = 123456;
    private static final String WRONG_SERVICE_ID = "123456789012";

    private static final String SHARED_KEY = "QCBm3N0oFjzQAWsTIVN8mPLK12TW6HU6InSfjvnF";

    private static final String WRONG_HASH_ALGO = "wrong_algorithm";
    private static final String HASH_SEPARATOR = "|";

    private static final HashType HASH_SHA256 = HashType.SHA256;

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
