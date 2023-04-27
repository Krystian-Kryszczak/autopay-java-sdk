package krystian.kryszczak.bm.sdk.confirmation;

import krystian.kryszczak.bm.sdk.hash.Hashable;
import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public final class Confirmation implements Serializable, Hashable, HttpRequestBody {
    private final int ServiceID;
    private final @NotNull String OrderID;
    private final @NotNull String Hash;

    @Override
    public boolean isHashPresent() {
        return Hash != null;
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
    public @NotNull Object[] toArrayWithoutHash() {
        return new Object[] {
            ServiceID,
            OrderID,
        };
    }

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toArray() {
        return Map.of(
            "ServiceID", String.valueOf(ServiceID),
            "OrderID", OrderID,
            "Hash", Hash
        );
    }

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toCapitalizedMap() {
        return toArray();
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
