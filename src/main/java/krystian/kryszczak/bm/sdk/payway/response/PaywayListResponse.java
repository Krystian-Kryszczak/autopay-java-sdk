package krystian.kryszczak.bm.sdk.payway.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import krystian.kryszczak.bm.sdk.payway.PaywayList;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public final class PaywayListResponse extends PaywayList {
    @JsonProperty("gateway")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Gateway> gateways;

    @Override
    public @NotNull Object[] toArrayWithoutHash() {
        final Object[] base = super.toArrayWithoutHash();
        final Object[] result = Arrays.copyOf(base, base.length + 1);

        // TODO

        return result;
    }
}
