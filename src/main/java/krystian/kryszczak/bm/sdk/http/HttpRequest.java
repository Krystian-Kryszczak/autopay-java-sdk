package krystian.kryszczak.bm.sdk.http;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Map;

public record HttpRequest<I extends HttpRequestBody>(@NotNull URI uri, @NotNull Map<String, String> headers, I body) {
    public String getHost() {
        return uri.getHost();
    }
    public int getPort() {
        final int uriPort = uri.getPort();
        if (uriPort >= 0 && uriPort <= 65535) {
            return uriPort;
        }
        return 80;
    }
    public String getPath() {
        return uri.getPath();
    }
}
