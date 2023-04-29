package krystian.kryszczak.bm.sdk.transaction;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
@SuperBuilder
public final class TransactionContinue extends TransactionResponse {
    private final @NotNull String status;
    private final @NotNull String redirectUrl;

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toArray() {
        final var result = super.toArray();
        result.put("status", status);
        result.put("redirectUrl", redirectUrl);
        return result;
    }
}
