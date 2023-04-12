package krystian.kryszczak.bm.sdk.payway;

import krystian.kryszczak.bm.sdk.common.dto.AbstractDto;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public final class PaywayListDto extends AbstractDto implements Serializable {
    public PaywayListDto(@NotNull String gatewayUrl, @NotNull String request) {
        super(gatewayUrl, request);
    }

    @Override
    public Serializable getRequestData() {
        return null;
    }
}
