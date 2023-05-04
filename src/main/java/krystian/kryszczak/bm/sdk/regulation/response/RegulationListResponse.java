package krystian.kryszczak.bm.sdk.regulation.response;

import krystian.kryszczak.bm.sdk.regulation.RegulationList;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Getter
public final class RegulationListResponse extends RegulationList {
    private final Regulations regulations;

    public RegulationListResponse(@NotNull String serviceId, @NotNull Regulations regulations, @NotNull String messageId, @NotNull String hash) {
        super(serviceId, messageId, hash);
        this.regulations = regulations;
    }

    @Override
    public @NotNull Object[] toArrayWithoutHash() {
        final List<Object> data = new LinkedList<>(
            Arrays.asList(super.toArrayWithoutHash())
        );
        data.add(regulations);
        return data.toArray();
    }
}
