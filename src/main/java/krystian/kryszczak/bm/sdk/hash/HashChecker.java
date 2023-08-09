package krystian.kryszczak.bm.sdk.hash;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import org.jetbrains.annotations.NotNull;

public final class HashChecker {

    /**
     * Checks if Hash is correct.
     * @return true if Hash is valid otherwise Hash is not present or is incorrect
     */
    public static boolean checkHash(@NotNull Hashable hashable, @NotNull BlueMediaConfiguration configuration) {
        if (!hashable.isHashPresent()) return false;

        final String generatedHash = HashGenerator.generateHash(
            hashable.toArray(),
            configuration
        );

        return generatedHash.equals(hashable.getHash());
    }
}
