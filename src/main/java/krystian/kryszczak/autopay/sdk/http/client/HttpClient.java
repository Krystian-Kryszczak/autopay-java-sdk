package krystian.kryszczak.autopay.sdk.http.client;

import io.vertx.core.Vertx;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequest;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequestBody;
import krystian.kryszczak.autopay.sdk.http.client.vertx.VertxHttpClient;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;

@ApiStatus.AvailableSince("1.0")
public interface HttpClient {
    @NotNull <I extends HttpRequestBody> Publisher<@NotNull String> post(@NotNull HttpRequest<I> httpRequest);

    @Contract(" -> new")
    static @NotNull HttpClient createDefault() {
        return new VertxHttpClient(Vertx.vertx());
    }
}
