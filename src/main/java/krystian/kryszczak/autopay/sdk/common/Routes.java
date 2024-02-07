package krystian.kryszczak.autopay.sdk.common;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public enum Routes {
    PAYMENT_ROUTE("/payment"),
    PAYWAY_LIST_ROUTE("/paywayList"),
    GET_REGULATIONS_ROUTE("/webapi/regulationsGet");

    private final @NotNull String value;

    Routes(@NotNull String value) {
        this.value = value;
    }

    @Override
    public final @NotNull String toString() {
        return value;
    }
}
