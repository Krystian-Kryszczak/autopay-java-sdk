package krystian.kryszczak.bm.sdk.common;

import krystian.kryszczak.bm.sdk.hash.Hashable;
import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Getter
@NoArgsConstructor(force = true)
public abstract class ServiceHttpRequestBody extends Hashable implements HttpRequestBody {
    protected @NotNull String serviceID;
    protected @NotNull String messageID;
    protected @Nullable String hash;

    public ServiceHttpRequestBody(@NotNull String serviceID, @NotNull String messageID, @Nullable String hash) {
        this.serviceID = serviceID;
        this.messageID = messageID;
        this.hash = hash;
    }

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toArray() {
        return Map.of(
            "serviceID", serviceID,
            "messageID", messageID,
            "hash", hash
        );
    }
}
