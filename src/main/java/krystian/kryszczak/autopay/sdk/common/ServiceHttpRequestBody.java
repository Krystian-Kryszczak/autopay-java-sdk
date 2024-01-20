package krystian.kryszczak.autopay.sdk.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.hash.Hashable;
import krystian.kryszczak.autopay.sdk.http.HttpRequestBody;
import krystian.kryszczak.autopay.sdk.util.CollectionsUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Getter
@XmlRootElement
@XmlType(propOrder = {"serviceID", "messageID", "hash"})
@AllArgsConstructor(onConstructor_ = @JsonCreator)
public abstract class ServiceHttpRequestBody extends Hashable implements HttpRequestBody {
    protected final @NotNull String serviceID;
    protected final @NotNull String messageID;
    protected final @Nullable String hash;

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toMap() {
        return CollectionsUtils.nonNullMapFromArray(
            "serviceID", serviceID,
            "messageID", messageID,
            "hash", hash
        );
    }
}
