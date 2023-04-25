package krystian.kryszczak.bm.sdk.hash;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import org.jetbrains.annotations.NotNull;

public sealed interface HashChecker permits HashCheckerImpl {
    HashChecker instance = new HashCheckerImpl();
    static HashChecker getInstance() {
        return instance;
    }

    /**
     * Checks if hash is correct.
     * @return true if hash is valid otherwise hash is not present or is incorrect
     */
    boolean checkHash(@NotNull Hashable data, @NotNull BlueMediaConfiguration credentials);
}
