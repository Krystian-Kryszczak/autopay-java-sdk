package krystian.kryszczak.autopay.sdk.hash;

import krystian.kryszczak.autopay.sdk.BaseTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class HashCheckerTest extends BaseTestCase {
    @Test
    public void testCheckHashReturnsTrue() {
        final Object[] fieldsValues = new Object[] {
            "Hello", "world", '!', 55
        };

        final Hashable hashable = Hashable.create(
            fieldsValues,
            HashGenerator.generateHash(fieldsValues, getTestConfiguration())
        );

        final boolean checkedHash = HashChecker.checkHash(hashable, getTestConfiguration());

        Assertions.assertTrue(checkedHash);
    }
}
