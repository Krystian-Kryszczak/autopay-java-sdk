package krystian.kryszczak.autopay.sdk.hash;

import krystian.kryszczak.autopay.sdk.BaseTestCase;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class HashCheckerTest extends BaseTestCase {
    @Test
    public void checkHashReturnsTrue() {
        final Object[] fieldsValues = new Object[] { "Hello", "world", '!', 55 };

        final Hashable hashable = new Hashable() {
            @Override
            public @NotNull Object[] toArray() {
                return fieldsValues;
            }

            @Override
            public @NotNull String getHash() {
                return HashGenerator.generateHash(fieldsValues, getTestConfiguration());
            }
        };

        final boolean checkedHash = HashChecker.checkHash(hashable, getTestConfiguration());

        Assertions.assertTrue(checkedHash);
    }
}
