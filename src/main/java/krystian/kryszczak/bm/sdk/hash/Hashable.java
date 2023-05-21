package krystian.kryszczak.bm.sdk.hash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuperBuilder
@NoArgsConstructor
public abstract class Hashable {
    public abstract @Nullable String getHash();

    @JsonIgnore
    public boolean isHashPresent() {
        return getHash() != null;
    }

    public abstract @NotNull Object[] toArrayWithoutHash();
}
