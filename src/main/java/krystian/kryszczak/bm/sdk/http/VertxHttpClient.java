package krystian.kryszczak.bm.sdk.http;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.impl.NoStackTraceThrowable;
import io.vertx.rxjava3.core.Vertx;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public final class VertxHttpClient implements HttpClient {
    private final Vertx vertx = Vertx.vertx();
    private final io.vertx.rxjava3.core.http.HttpClient client = vertx.createHttpClient();

    @Override
    public @NotNull <I, O> Maybe<@NotNull Response<O>> post(@NotNull Request<I, O> request) {
        client.rxRequest(HttpMethod.GET, 8080, "localhost", "/")
                .flatMap(req -> req
                        .rxSend()
                        .flatMap(response -> {
                            if (response.statusCode() == 200) {
                                return response.body();
                            } else {
                                return Single.error(new NoStackTraceThrowable("Invalid response"));
                            }
                        }))
                .subscribe(body -> {
                    // Process the body
                });
        //
        return Maybe.empty(); // TODO
    }
}
