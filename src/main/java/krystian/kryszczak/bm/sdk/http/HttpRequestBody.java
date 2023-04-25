package krystian.kryszczak.bm.sdk.http;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public interface HttpRequestBody {
    @NotNull Map<@NotNull String, @NotNull String> toArray();

    @NotNull
    default Map<@NotNull String, @NotNull String> toCapitalizedMap() {
        return toArray().entrySet().stream()
            .map(entry -> Map.entry(
                entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1),
                entry.getValue()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
