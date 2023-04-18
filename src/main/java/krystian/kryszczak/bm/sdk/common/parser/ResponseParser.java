package krystian.kryszczak.bm.sdk.common.parser;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public abstract class ResponseParser<T> {
    protected final @NotNull String responseBody;
    protected final @NotNull BlueMediaConfiguration configuration;
}
