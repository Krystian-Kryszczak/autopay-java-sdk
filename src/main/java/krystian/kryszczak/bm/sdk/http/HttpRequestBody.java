package krystian.kryszczak.bm.sdk.http;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface HttpRequestBody {
    @NotNull Map<@NotNull String, @NotNull String> fieldsMapWithCapitalizedKeysAndFormattedValues();
}
