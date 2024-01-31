package krystian.kryszczak.autopay.sdk.hash;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.Transient;

@ToString
@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Hashable {
    public static @NotNull Hashable create(@NotNull Object[] data, @NotNull String hash) {
        return new Hashable() {
            @Override
            public @NotNull Object[] toArray() {
                return data;
            }

            @Override
            public @NotNull String getHash() {
                return hash.trim();
            }
        };
    }

    /**
     * @return Array without hash.
     */
    @Transient
    public abstract @NotNull Object[] toArray();

    public abstract @Nullable String getHash();

    @Transient
    public final boolean isHashPresent() {
        return getHash() != null;
    }
}
