package krystian.kryszczak.bm.sdk.http;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public interface HttpRequestBody {
    @NotNull Map<@NotNull String, @NotNull String> toArray();

    @NotNull
    default Map<@NotNull String, @NotNull String> toCapitalizedMap() {
        final Map<String, String> result = new HashMap<>();
        toArray().forEach((key, value) -> result.put(StringUtils.capitalize(key), value));
        return result;
    }
}
