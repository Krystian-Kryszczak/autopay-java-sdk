package krystian.kryszczak.autopay.sdk.http.vertx;

import io.reactivex.rxjava3.core.Maybe;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.rxjava3.core.Vertx;
import io.vertx.rxjava3.ext.web.client.WebClient;
import krystian.kryszczak.autopay.sdk.http.HttpClient;
import krystian.kryszczak.autopay.sdk.http.HttpRequest;
import krystian.kryszczak.autopay.sdk.http.HttpRequestBody;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiStatus.AvailableSince("")
public final class VertxHttpClient implements HttpClient {
    private static final Logger logger = LoggerFactory.getLogger(VertxHttpClient.class);
    private final WebClient client;

    public VertxHttpClient() {
        final Vertx vertx = Vertx.vertx();

        final WebClientOptions webClientOptions = new WebClientOptions()
            .setFollowRedirects(false)
            .setVerifyHost(true)
            .setLogActivity(true);

        client = WebClient.wrap(
            vertx.createHttpClient(webClientOptions)
        );
    }

    @Override
    public @NotNull <I extends HttpRequestBody> Maybe<@NotNull String> post(@NotNull HttpRequest<I> httpRequest) {
        final var vertexRequest = client.post(httpRequest.uri().toString());

        return vertexRequest
            .putHeaders(
                vertexRequest.headers()
                    .addAll(httpRequest.headers())
            ).rxSendMultipartForm(
                VertexAdapter.asMultipartForm(httpRequest.body())
            )
            .flatMapMaybe(it -> {
                final String body = it.bodyAsString("UTF-8");
                if (body == null) return Maybe.empty();
                return Maybe.just(body);
            })
            .doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
            .onErrorComplete();
    }
}
