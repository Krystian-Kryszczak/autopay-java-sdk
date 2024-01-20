package krystian.kryszczak.autopay.sdk.http;

import io.reactivex.rxjava3.core.Maybe;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.AvailableSince("")
public interface HttpClient {
    @NotNull <I extends HttpRequestBody> Maybe<@NotNull String> post(@NotNull HttpRequest<I> httpRequest);

    static HttpClient getDefaultHttpClient() {
        return new NativeHttpClient();
    }
}
