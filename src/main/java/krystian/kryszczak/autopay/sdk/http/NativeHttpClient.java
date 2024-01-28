package krystian.kryszczak.autopay.sdk.http;

import krystian.kryszczak.autopay.sdk.util.HttpUtils;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Deprecated
public final class NativeHttpClient implements HttpClient {
    private static final Logger logger = LoggerFactory.getLogger(NativeHttpClient.class);

    private final java.net.http.HttpClient httpClient;

    public NativeHttpClient() {
        httpClient = java.net.http.HttpClient.newBuilder()
            .followRedirects(java.net.http.HttpClient.Redirect.NEVER)
            .build();
    }

    @SneakyThrows
    @Override
    public @NotNull <I extends HttpRequestBody> Publisher<@NotNull String> post(@NotNull HttpRequest<I> httpRequest) {
        final var body = java.net.http.HttpRequest.BodyPublishers.ofString(
            HttpUtils.convertMapToFormUrlencoded(httpRequest.body().toCapitalizedMap()),
            StandardCharsets.UTF_8
        );

        final var javaHttpRequestBuilder = java.net.http.HttpRequest.newBuilder()
            .uri(httpRequest.uri());

        if (!httpRequest.headers().isEmpty()) {
            javaHttpRequestBuilder.headers(httpRequest.headers().entrySet().stream().map(Object::toString).toArray(String[]::new));
        }

        javaHttpRequestBuilder.setHeader("Content-Type", "application/x-www-form-urlencoded").POST(body);

        return Mono.fromFuture(
            httpClient.sendAsync(
                javaHttpRequestBuilder.build(),
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
            )
        ).doOnSuccess(it -> logger.debug("Response body: " + it.body()))
        .doOnSuccess(response -> logger.debug("Status code: " + response.statusCode()))
        .doOnError(throwable -> logger.error(throwable.getMessage(), throwable))
        .onErrorComplete()
        .map(HttpResponse::body);
    }
}
