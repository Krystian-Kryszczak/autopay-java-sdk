package krystian.kryszczak.bm.sdk.transaction;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
@SuperBuilder
public final class TransactionContinue extends TransactionResponse {
    private final String status;
    private final String redirectUrl;

    @Override
    public @NotNull Map<@NotNull String, @NotNull String> toArray() {
        final var result = super.toArray();

        if (status != null) result.put("status", status);
        if (redirectUrl != null) result.put("redirectUrl", redirectUrl);

        return result;
    }
}
