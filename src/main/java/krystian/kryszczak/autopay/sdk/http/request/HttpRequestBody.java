package krystian.kryszczak.autopay.sdk.http.request;

import org.jetbrains.annotations.NotNull;

import java.beans.Transient;
import java.util.Map;

import static krystian.kryszczak.autopay.sdk.util.MapUtils.capitalizeMap;

public interface HttpRequestBody {
    @Transient
    @NotNull Map<@NotNull String, @NotNull String> toMap();

    @Transient
    default @NotNull Map<@NotNull String, @NotNull String> toCapitalizedMap() {
        return capitalizeMap(toMap());
    }
}
