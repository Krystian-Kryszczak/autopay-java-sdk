package krystian.kryszczak.autopay.sdk.http;

import krystian.kryszczak.autopay.sdk.http.vertx.VertxHttpClient;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;

@ApiStatus.AvailableSince("1.0")
public interface HttpClient {
    @NotNull <I extends HttpRequestBody> Publisher<@NotNull String> post(@NotNull HttpRequest<I> httpRequest);

    static HttpClient getDefaultHttpClient() {
        return new VertxHttpClient();
    }
}
