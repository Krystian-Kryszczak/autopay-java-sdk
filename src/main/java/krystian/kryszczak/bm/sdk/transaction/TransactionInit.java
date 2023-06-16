package krystian.kryszczak.bm.sdk.transaction;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Getter
@SuperBuilder
public final class TransactionInit extends TransactionResponse {
    private final String confirmation;
    private final String reason;
    private final String paymentStatus;

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toArray() {
        final var result = super.toArray();

        result.put("confirmation", confirmation);
        if (reason != null) result.put("reason", reason);
        result.put("paymentStatus", paymentStatus);

        return result;
    }
}
