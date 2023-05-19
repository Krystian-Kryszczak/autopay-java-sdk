package krystian.kryszczak.bm.sdk.hash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Hashable {
    @Nullable String getHash();

    @JsonIgnore
    default boolean isHashPresent() {
        return getHash() != null;
    }

    @NotNull Object[] toArrayWithoutHash();
}
