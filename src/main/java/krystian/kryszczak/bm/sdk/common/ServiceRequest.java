package krystian.kryszczak.bm.sdk.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@AllArgsConstructor
public abstract class ServiceRequest extends AbstractValueObject {
    protected @NotNull String serviceId;
    protected final @NotNull String messageId;
    protected @Nullable String hash;

    @Override
    public @NotNull Object[] toArray() {
        return new Object[] {
            serviceId,
            messageId
        };
    }
}
