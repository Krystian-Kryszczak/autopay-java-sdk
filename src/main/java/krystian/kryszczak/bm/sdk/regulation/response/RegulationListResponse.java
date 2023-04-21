package krystian.kryszczak.bm.sdk.regulation.response;

import krystian.kryszczak.bm.sdk.regulation.RegulationList;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public final class RegulationListResponse extends RegulationList {
    private final Regulations regulations;

    public RegulationListResponse(@NotNull String serviceId, @NotNull Regulations regulations, @NotNull String messageId, @NotNull String hash) {
        super(serviceId, messageId, hash);
        this.regulations = regulations;
    }
}
