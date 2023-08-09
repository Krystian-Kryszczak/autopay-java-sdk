package krystian.kryszczak.bm.sdk.http;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.beans.Transient;
import java.util.Map;
import java.util.stream.Collectors;

public interface HttpRequestBody {
    @Transient
    @NotNull Map<@NotNull String, @NotNull String> toMap();

    @Transient
    default @NotNull Map<@NotNull String, @NotNull String> toCapitalizedMap() {
        return toMap().entrySet().stream()
            .map(entry -> Map.entry(StringUtils.capitalize(entry.getKey()), entry.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
