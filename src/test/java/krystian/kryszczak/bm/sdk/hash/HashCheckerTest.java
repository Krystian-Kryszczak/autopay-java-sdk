package krystian.kryszczak.bm.sdk.hash;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HashCheckerTest {

    @Test
    void checkHashShouldReturnTrue() {
        final BlueMediaConfiguration fakeConfiguration = BlueMediaConfiguration.builder()
                .setServiceId(0)
                .setSharedKey("xyz")
                .setHashAlgorithm(HashType.SHA256)
                .setHashSeparator("|")
                .build();

        final Object[] fakeFieldsValues = new Object[]{
            "Hello", "world", '!', 55
        };

        final Hashable hashable = new Hashable() {
            final String hash = HashGenerator.generateHash(fakeFieldsValues, fakeConfiguration);
            @Override
            public @NotNull String getHash() {
                return hash;
            }

            @Override
            public @NotNull Object[] toArrayWithoutHash() {
                return fakeFieldsValues;
            }
        };

        final boolean checkedHash = HashChecker.checkHash(
            hashable,
            fakeConfiguration
        );

        Assertions.assertTrue(checkedHash);
    }
}
