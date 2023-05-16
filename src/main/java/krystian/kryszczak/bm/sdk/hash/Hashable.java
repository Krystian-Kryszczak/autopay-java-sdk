package krystian.kryszczak.bm.sdk.hash;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Hashable {
    @Nullable String getHash();

    default boolean isHashPresent() {
        return getHash() != null;
    }

    @NotNull Object[] toArrayWithoutHash();
}
