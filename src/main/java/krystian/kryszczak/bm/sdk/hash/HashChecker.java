package krystian.kryszczak.bm.sdk.hash;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import org.jetbrains.annotations.NotNull;

public sealed interface HashChecker permits HashCheckerImpl {
    HashChecker instance = new HashCheckerImpl();
    static HashChecker getInstance() {
        return instance;
    }

    /**
     * Checks if Hash is correct.
     * @return true if Hash is valid otherwise Hash is not present or is incorrect
     */
    boolean checkHash(@NotNull Hashable data, @NotNull BlueMediaConfiguration credentials);
}
