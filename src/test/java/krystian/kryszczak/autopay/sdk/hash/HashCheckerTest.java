package krystian.kryszczak.autopay.sdk.hash;

import krystian.kryszczak.autopay.sdk.BaseTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class HashCheckerTest extends BaseTestCase {
    @Test
    void testCheckHashReturnsTrue() {
        final Object[] fieldsValues = new Object[] {
            "Hello", "world", '!', 55
        };

        final Hashable hashable =  Hashable.create(
            fieldsValues,
            HashGenerator.generateHash(fieldsValues, getConfigurationStub())
        );

        final boolean checkedHash = HashChecker.checkHash(hashable, getConfigurationStub());

        Assertions.assertTrue(checkedHash);
    }
}
