package krystian.kryszczak.bm.sdk.hash;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import org.jetbrains.annotations.NotNull;

final class HashCheckerImpl implements HashChecker {
    private final HashGenerator hashGenerator = new HashGeneratorImpl();

    @Override
    public boolean checkHash(@NotNull Hashable hashable, @NotNull BlueMediaConfiguration credentials) {
        if (hashable.getHash() == null) return false;

        final String generatedHash = hashGenerator.generateHash(
            hashable.toArrayWithoutHash(),
            credentials
        );

        return generatedHash.equals(hashable.getHash());
    }
}
