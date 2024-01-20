package krystian.kryszczak.autopay.sdk.regulation.response;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.regulation.RegulationList;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

@Getter
@XmlRootElement(name = "regulationList")
@XmlType(propOrder = {"serviceID", "messageID", "regulations", "hash"})
public final class RegulationListResponse extends RegulationList {
    private final Regulations regulations;

    @ConstructorProperties({"serviceID", "regulations", "messageID", "hash"})
    public RegulationListResponse(@NotNull String serviceID, @NotNull Regulations regulations, @NotNull String messageID, @NotNull String hash) {
        super(serviceID, messageID, hash);
        this.regulations = regulations;
    }

    @Override
    public @NotNull Object[] toArray() {
        final Object[] parentArray = super.toArray();
        final Object[] result = new Object[parentArray.length + 1];

        System.arraycopy(parentArray, 0, result, 0, parentArray.length);
        result[parentArray.length] = regulations;

        return result;
    }
}
