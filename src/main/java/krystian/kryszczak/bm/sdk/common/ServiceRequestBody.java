package krystian.kryszczak.bm.sdk.common;

import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter(AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class ServiceRequestBody implements HttpRequestBody {
    protected @NotNull String serviceId;
    protected final @NotNull String messageId;
    @Getter
    protected @NotNull String hash;

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toArray() {
        return Map.of(
            "serviceID", serviceId,
            "messageID", messageId,
            "hash", hash
        );
    }
}
