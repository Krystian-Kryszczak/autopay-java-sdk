package krystian.kryszczak.bm.sdk.hash;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import org.jetbrains.annotations.NotNull;

public sealed interface HashGenerator permits HashGeneratorImpl {
    HashGenerator instance = new HashGeneratorImpl();

    /**
     * Generates hash.
     */
    @NotNull String generateHash(@NotNull Object[] data, @NotNull BlueMediaConfiguration configuration);
}
