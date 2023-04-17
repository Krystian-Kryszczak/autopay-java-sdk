package krystian.kryszczak.bm.sdk.http;

import io.reactivex.rxjava3.core.Maybe;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public record Response<T>(
    int statusCode,
    @Getter
    @NotNull String mediaType,
    @Getter
    @NotNull Maybe<T> body
) {}
