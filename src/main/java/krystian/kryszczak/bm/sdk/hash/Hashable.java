package krystian.kryszczak.bm.sdk.hash;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Hashable {
    @Nullable String getHash();
    boolean isHashPresent();
    @NotNull Object[] toArrayWithoutHash();
}
