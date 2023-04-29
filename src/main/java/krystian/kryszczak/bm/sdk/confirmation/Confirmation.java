package krystian.kryszczak.bm.sdk.confirmation;

import krystian.kryszczak.bm.sdk.hash.Hashable;
import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;

@Builder
@Getter
@SuperBuilder
public final class Confirmation implements Serializable, Hashable, HttpRequestBody {
    private final int ServiceID;
    private final @NotNull String OrderID;
    private final @NotNull String Hash;

    @Override
    public boolean isHashPresent() {
        return true;
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
