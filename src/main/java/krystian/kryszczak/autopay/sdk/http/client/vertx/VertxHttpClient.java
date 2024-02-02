package krystian.kryszczak.autopay.sdk.http.client.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.impl.headers.HeadersMultiMap;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.multipart.MultipartForm;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import krystian.kryszczak.autopay.sdk.http.client.HttpClient;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequest;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequestBody;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@Singleton
@ApiStatus.AvailableSince("1.0")
public final class VertxHttpClient implements HttpClient {
    private final WebClient client;

    @Inject
    public VertxHttpClient(@NotNull Vertx vertx) {
        this.client = WebClient.wrap(
            vertx.createHttpClient(
                new WebClientOptions()
                    .setFollowRedirects(false)
                    .setVerifyHost(true)
                    .setLogActivity(true)
            )
        );
    }

    @Override
    public @NotNull <I extends HttpRequestBody> Publisher<@NotNull String> post(@NotNull HttpRequest<I> httpRequest) {
        return Flux.create(sink ->
            client.post(httpRequest.uri().toString())
                .putHeaders(HeadersMultiMap.httpHeaders().addAll(httpRequest.headers()))
                .sendMultipartForm(adapt(httpRequest.body()))
                .onSuccess(response -> sink.next(response.bodyAsString("UTF-8")))
                .onFailure(throwable -> sink.error(throwable.getCause()))
                .onComplete(it -> sink.complete())
        );
    }

    private MultipartForm adapt(@NotNull HttpRequestBody body) {
        final MultipartForm multipartForm = MultipartForm.create()
                .setCharset("UTF-8");
        for (final var entry : body.toCapitalizedMap().entrySet()) {
            multipartForm.attribute(entry.getKey(), entry.getValue());
        }
        return multipartForm;
    }
}