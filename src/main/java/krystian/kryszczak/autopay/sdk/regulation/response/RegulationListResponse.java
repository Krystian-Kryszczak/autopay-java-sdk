package krystian.kryszczak.autopay.sdk.regulation.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.regulation.RegulationList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@JsonRootName("regulationList")
@XmlRootElement
@XmlType(propOrder = { "serviceID", "messageID", "regulations", "hash" })
public final class RegulationListResponse extends RegulationList {
    private final Regulations regulations;

    @JsonCreator
    @ConstructorProperties({ "serviceID", "messageID", "regulations", "hash" })
    public RegulationListResponse(@NotNull String serviceID, @NotNull String messageID, @NotNull Regulations regulations, @NotNull String hash) {
        super(serviceID, messageID, hash);
        this.regulations = regulations;
    }

    @Override
    public @NotNull Object @NotNull [] toArray() {
        return new Object[] {
            serviceID,
            messageID,
            regulations
        };
    }
}
