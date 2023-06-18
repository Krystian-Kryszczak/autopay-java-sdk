package krystian.kryszczak.bm.sdk.http;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public interface HttpRequestBody {
    @NotNull Map<@NotNull String, @NotNull String> toArray();

    @NotNull
    default Map<@NotNull String, @NotNull String> toCapitalizedMap() {
        return toArray().entrySet().stream()
            .map(entry -> Map.entry(StringUtils.capitalize(entry.getKey()), entry.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
