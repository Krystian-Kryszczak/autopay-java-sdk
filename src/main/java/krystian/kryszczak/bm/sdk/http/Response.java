package krystian.kryszczak.bm.sdk.http;

import io.reactivex.rxjava3.core.Maybe;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public record Response<T>(
    int statusCode,
    @NotNull String mediaType,
    @NotNull Maybe<T> body
) {}
