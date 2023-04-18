package krystian.kryszczak.bm.sdk.regulation;

import krystian.kryszczak.bm.sdk.common.ServiceRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public class RegulationList extends ServiceRequest implements Serializable {
    public RegulationList(@NotNull String serviceId, @NotNull String messageId, @Nullable String hash) {
        super(serviceId, messageId, hash);
    }
}
