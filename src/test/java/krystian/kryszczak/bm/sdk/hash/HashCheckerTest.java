package krystian.kryszczak.bm.sdk.hash;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HashCheckerTest {
    private final HashGenerator hashGenerator = HashGenerator.instance;
    private final HashChecker hashChecker = HashChecker.instance;

    @Test
    void checkedHashShouldBeTrue() {
        final BlueMediaConfiguration fakeCredentials =
                new BlueMediaConfiguration(0,"xyz", HashType.SHA256, "|");

        final Object[] fakeFieldsValues = new Object[]{
            "Hello", "world", '!', 55
        };

        final Hashable hashable = new Hashable() {
            final String hash = hashGenerator.generateHash(fakeFieldsValues, fakeCredentials);
            @Override
            public @Nullable String getHash() {
                return hash;
            }

            @Override
            public boolean isHashPresent() {
                return hash != null;
            }

            @Override
            public @NotNull Object[] toArrayWithoutHash() {
                return fakeFieldsValues;
            }
        };

        final boolean checkedHash = hashChecker.checkHash(
            hashable,
            fakeCredentials
        );
        Assertions.assertTrue(checkedHash);
    }

    // TODO others
}
