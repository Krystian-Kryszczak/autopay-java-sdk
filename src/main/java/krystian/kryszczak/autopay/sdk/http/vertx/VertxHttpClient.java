package krystian.kryszczak.autopay.sdk.http.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.impl.headers.HeadersMultiMap;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import krystian.kryszczak.autopay.sdk.http.HttpClient;
import krystian.kryszczak.autopay.sdk.http.HttpRequest;
import krystian.kryszczak.autopay.sdk.http.HttpRequestBody;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@ApiStatus.AvailableSince("1.0")
public final class VertxHttpClient implements HttpClient {
    private final WebClient client;

    public VertxHttpClient(Vertx vertx) {
        final WebClientOptions webClientOptions = new WebClientOptions()
            .setFollowRedirects(false)
            .setVerifyHost(true)
            .setLogActivity(true);
        client = WebClient.wrap(vertx.createHttpClient(webClientOptions));
    }

    public VertxHttpClient() {
        this(Vertx.vertx());
    }

    @Override
    public @NotNull <I extends HttpRequestBody> Publisher<@NotNull String> post(@NotNull HttpRequest<I> httpRequest) {
        return Flux.create(sink ->
            client.post(httpRequest.uri().toString())
                .putHeaders(HeadersMultiMap.httpHeaders().addAll(httpRequest.headers()))
                .sendMultipartForm(VertexAdapter.adapt(httpRequest.body()))
                .onSuccess(response -> sink.next(response.bodyAsString("UTF-8")))
                .onFailure(throwable -> sink.error(throwable.getCause()))
                .onComplete(it -> sink.complete())
        );
    }
}
