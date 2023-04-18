package krystian.kryszczak.bm.sdk.hash;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import org.jetbrains.annotations.NotNull;

public sealed interface HashChecker permits HashCheckerImpl {
    HashChecker instance = new HashCheckerImpl();
    static HashChecker getInstance() {
        return instance;
    }

    /**
     * Checks if krystian.kryszczak.bm.sdk.hash is correct.
     * @return true if krystian.kryszczak.bm.sdk.hash is valid otherwise krystian.kryszczak.bm.sdk.hash is not present or is incorrect
     */
    boolean checkHash(@NotNull Hashable data, @NotNull BlueMediaConfiguration credentials);
}
