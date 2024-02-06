package krystian.kryszczak.autopay.sdk.http.client.okhttp;

import krystian.kryszczak.autopay.sdk.http.client.HttpClient;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequest;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequestBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.io.IOException;

public final class OkHttpClient implements HttpClient { // TODO
    @Override
    public @NotNull <I extends HttpRequestBody> Publisher<@NotNull String> post(@NotNull HttpRequest<I> httpRequest) {
        final okhttp3.OkHttpClient client = new okhttp3.OkHttpClient()
            .newBuilder()
            .build();
        final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        final RequestBody body = RequestBody.create(mediaType, "ServiceID=56467&MessageID=7060232c5e531a4e271c39090b6f751e&Hash=85054a47dba8c7575f7af319795e816fceef53a0b5128a248ca05dd9befa25a2");
        final Request request = new Request.Builder()
            .url(httpRequest.uri().toString())
            .method("POST", body)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build();

        try {
            final Response response = client.newCall(request).execute();
            return Mono.just(response.body()).map(it -> {
                try {
                    return it.string();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
