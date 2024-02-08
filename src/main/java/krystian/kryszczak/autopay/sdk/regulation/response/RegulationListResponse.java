package krystian.kryszczak.autopay.sdk.regulation.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.regulation.RegulationList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;
import java.util.Arrays;

@Getter
@EqualsAndHashCode(callSuper = true)
@JsonRootName("regulationList")
@XmlRootElement
@XmlType(propOrder = { "serviceID", "messageID", "regulations", "hash" })
public final class RegulationListResponse extends RegulationList {
    private final Regulations regulations;

    @ConstructorProperties({ "serviceID", "messageID", "regulations", "hash" })
    public RegulationListResponse(int serviceID, @NotNull String messageID, @NotNull Regulations regulations, @NotNull String hash) {
        super(serviceID, messageID, hash);
        this.regulations = regulations;
    }

    @Override
    public @NotNull Object @NotNull [] toArray() {
        final Object[] base = super.toArray();
        final Regulation[] regulations = this.regulations.regulation();
        final Object[] dst = new Object[base.length + (regulations.length * 6)];
        System.arraycopy(base, 0, dst, 0, base.length);
        int dstPos = base.length;
        for (final Regulation regulation : regulations) {
            dst[dstPos] = regulation.regulationID(); dstPos++;
            final String url = regulation.url();
            if (url != null) {
                dst[dstPos] = url; dstPos++;
            }
            dst[dstPos] = regulation.type(); dstPos++;
            dst[dstPos] = regulation.language(); dstPos++;
            final String inputLabel = regulation.inputLabel();
            if (inputLabel != null) {
                dst[dstPos] = inputLabel; dstPos++;
            }
            final String gatewayID = regulation.gatewayID();
            if (gatewayID != null) {
                dst[dstPos] = gatewayID; dstPos++;
            }
        }
        return Arrays.copyOf(dst, dstPos);
    }
}
