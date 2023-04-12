package krystian.kryszczak.bm.sdk.confirmation;

import krystian.kryszczak.bm.sdk.hash.Hashable;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@Builder
public record Confirmation(
    @NotNull String serviceId,
    @NotNull String orderId,
    @Nullable String hash
) implements Serializable, Hashable {

    @Override
    public @Nullable String getHash() {
        return hash();
    }

    @Override
    public boolean isHashPresent() {
        return hash != null;
    }

    public static @Nullable Confirmation build(@NotNull String[] array) {
        if (array.length < 3 || canNotBeConvertToInt(array[0]) || canNotBeConvertToInt(array[1])) return null;
        // TODO
        return null;
    }
    private static boolean canNotBeConvertToInt(@NotNull String value) {
        return false; // TODO
    }

    @Override
    public @NotNull String[] toArrayWithoutHash() {
        return new String[]{orderId, serviceId};
    }
    
    public enum Status {
        CONFIRMED("CONFIRMED"),
        NOT_CONFIRMED("NOTCONFIRMED");

        private final @NotNull String name;
        Status(@NotNull String name) {
            this.name = name;
        }

        @Override
        public final @NotNull String toString() {
            return name;
        }
    }
}
