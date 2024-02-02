package krystian.kryszczak.autopay.sdk.hash;

import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.common.exception.HashNotReturnedFromServerException;
import org.jetbrains.annotations.NotNull;

public final class HashChecker {
    /**
     * Checks if Hash is correct.
     * @throws HashNotReturnedFromServerException when hash is not present.
     * @return true if hash is valid and false when hash is incorrect.
     */
    public static boolean checkHash(@NotNull Hashable hashable, @NotNull AutopayConfiguration configuration) throws HashNotReturnedFromServerException {
        if (!hashable.isHashPresent()) {
            throw new HashNotReturnedFromServerException();
        }

        final String generatedHash = HashGenerator.generateHash(
            hashable.toArray(),
            configuration
        );

        return generatedHash.equals(hashable.getHash());
    }
}
