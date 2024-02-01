package krystian.kryszczak.autopay.sdk.confirmation;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.hash.Hashable;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequestBody;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.Map;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@XmlRootElement
@XmlType(propOrder = {"serviceID", "messageID", "hash"})
@AllArgsConstructor(onConstructor_ = {@JsonCreator, @ConstructorProperties({"serviceID", "messageID", "hash"})})
public final class Confirmation extends Hashable implements Serializable, HttpRequestBody {
    private final int serviceID;
    private final @NotNull String orderID;
    private final @NotNull String hash;

    public @NotNull String getHash() {
        return hash.trim();
    }

    @Contract(" -> new")
    @Override
    public @NotNull @Unmodifiable Map<@NotNull String, @NotNull String> toMap() {
        return Map.of(
            "serviceID", String.valueOf(serviceID),
            "orderID", orderID,
            "hash", hash
        );
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public @NotNull Object @NotNull [] toArray() {
        return new Object[] {
            serviceID,
            orderID
        };
    }
}
