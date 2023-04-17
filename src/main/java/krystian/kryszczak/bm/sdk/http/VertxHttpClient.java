package krystian.kryszczak.bm.sdk.http;

import io.reactivex.rxjava3.core.Maybe;
import io.vertx.rxjava3.core.Vertx;
import io.vertx.rxjava3.ext.web.client.WebClient;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.net.URI;

@AllArgsConstructor
public final class VertxHttpClient implements HttpClient {
    private final Vertx vertx = Vertx.vertx();
    private final WebClient client = WebClient.wrap(vertx.createHttpClient());

    @Override
    public @NotNull <I, O> Maybe<@NotNull O> post(@NotNull Request<I, O> request) {
        final URI uri = request.uri();

        System.out.println();
        System.out.println(request.getPort());
        System.out.println(request.getHost());
        System.out.println(request.getPath());
        System.out.println();

        client.post(request.getPort(), request.getHost(), request.getPath())
            .followRedirects(true)
            .rxSendJson(request.body())
            .doOnSuccess(it -> System.out.println(it.bodyAsString("UTF-8")))
            .doOnSuccess(it -> System.out.println("Status code: " + it.statusCode()))
            .doOnSuccess(it -> System.out.println(it.getHeader("Location")))
            .blockingGet();


//        return client.rxRequest(HttpMethod.POST, request.uri().getPort(), request.uri().getHost(), request.uri().getPath())
//            .flatMap(req -> req
//                .rxSend()
//                .flatMap(response -> {
//                    if (response.statusCode() == 200) {
//                        return Single.just((O) response.body());
//                    } else {
//                        return Single.error(new NoStackTraceThrowable("Invalid response"));
//                    }
//                })
//            )
//            .toMaybe();

        return Maybe.empty();
    }
}
