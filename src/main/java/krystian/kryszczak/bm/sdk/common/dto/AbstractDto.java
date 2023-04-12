package krystian.kryszczak.bm.sdk.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public abstract class AbstractDto {
    protected final @NotNull String gatewayUrl;
    protected final @NotNull String request;

    public abstract Serializable getRequestData();
}
