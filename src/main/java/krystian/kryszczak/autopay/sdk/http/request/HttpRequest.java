package krystian.kryszczak.autopay.sdk.http.request;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Map;

public record HttpRequest<I extends HttpRequestBody>(@NotNull URI uri, @NotNull Map<String, String> headers, I body) {}
