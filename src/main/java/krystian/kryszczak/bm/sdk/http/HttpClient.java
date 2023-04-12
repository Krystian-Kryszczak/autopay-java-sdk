package krystian.kryszczak.bm.sdk.http;

import io.reactivex.rxjava3.core.Maybe;
import org.jetbrains.annotations.NotNull;

public sealed interface HttpClient permits VertxHttpClient {
    @NotNull <I, O> Maybe<@NotNull Response<O>> post(@NotNull Request<I, O> request);
}
