package krystian.kryszczak.bm.sdk.regulation.response;

import krystian.kryszczak.bm.sdk.regulation.RegulationList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Getter
@NoArgsConstructor
public final class RegulationListResponse extends RegulationList {
    private Regulations regulations;

    public RegulationListResponse(@NotNull String serviceID, @NotNull Regulations regulations, @NotNull String messageID, @NotNull String hash) {
        super(serviceID, messageID, hash);
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
