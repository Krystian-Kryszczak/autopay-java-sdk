package krystian.kryszczak.autopay.sdk.confirmation;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.hash.Hashable;
import krystian.kryszczak.autopay.sdk.http.HttpRequestBody;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;

@Getter
@XmlRootElement
@XmlType(propOrder = {"serviceID", "messageID", "hash"})
@AllArgsConstructor
public final class Confirmation extends Hashable implements Serializable, HttpRequestBody {
    private final int serviceID;
    private final @NotNull String orderID;
    private final @NotNull String hash;

    public @NotNull String getHash() {
        return hash.trim();
    }

    @Override
    public @NotNull Object[] toArray() {
        return new Object[] {
            serviceID,
            orderID
        };
    }

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toMap() {
        return Map.of(
            "serviceID", String.valueOf(serviceID),
            "orderID", orderID,
            "hash", hash
        );
    }
}
