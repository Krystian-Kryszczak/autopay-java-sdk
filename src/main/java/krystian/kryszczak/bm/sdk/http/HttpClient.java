package krystian.kryszczak.bm.sdk.http;

import io.reactivex.rxjava3.core.Maybe;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.AvailableSince("")
public sealed interface HttpClient permits VertxHttpClient {
    @NotNull <I extends HttpRequestBody> Maybe<@NotNull String> post(@NotNull HttpRequest<I> httpRequest);
}
