package krystian.kryszczak.bm.sdk.http;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Map;

public record Request<I, O>(@NotNull URI uri, @NotNull Map<String, String> headers, I body) {}
