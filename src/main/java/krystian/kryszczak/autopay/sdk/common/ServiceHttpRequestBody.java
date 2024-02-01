package krystian.kryszczak.autopay.sdk.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.hash.Hashable;
import krystian.kryszczak.autopay.sdk.http.request.HttpRequestBody;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.ConstructorProperties;
import java.util.Map;

import static krystian.kryszczak.autopay.sdk.util.MapUtils.notNullMapOf;

@Getter
@XmlRootElement
@XmlType(propOrder = {"serviceID", "messageID", "hash"})
@AllArgsConstructor(onConstructor_ = {@JsonCreator, @ConstructorProperties({"serviceID", "messageID", "hash"})})
public abstract class ServiceHttpRequestBody extends Hashable implements HttpRequestBody {
    protected final @NotNull String serviceID;
    protected final @NotNull String messageID;
    protected final @Nullable String hash;

    public @Nullable String getHash() {
        return hash != null ? hash.trim() : null;
    }

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toMap() {
        return notNullMapOf(
            "serviceID", serviceID,
            "messageID", messageID,
            "hash", hash
        );
    }

    @Override
    public @NotNull Object[] toArray() {
        return new Object[] {
            serviceID,
            messageID
        };
    }
}
