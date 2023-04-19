package krystian.kryszczak.bm.sdk.common;

import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter(AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class ServiceRequest implements HttpRequestBody {
    protected @NotNull String serviceId;
    protected final @NotNull String messageId;
    @Getter
    protected @NotNull String hash;
}
