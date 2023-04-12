package krystian.kryszczak.bm.sdk.common.parser;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.http.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public abstract class ResponseParser<T> {
    protected final @NotNull Response<T> response;
    protected final @NotNull BlueMediaConfiguration configuration;

    protected boolean isResponseError() {
        // TODO
        return true;
    }
}
