package krystian.kryszczak.autopay.sdk.transaction;

import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@SuperBuilder
@EqualsAndHashCode(callSuper = false)
public final class PaywayFormResponse extends Transaction {
    public PaywayFormResponse(@NotNull String content) {
        super(null, null, null, content, null, null, null,
        null, null, null, null, null, null, null,
        null, null, null, null, null, null,
        null, null, null, null,
        null, null, null, null);
    }

    @Override
    public String toString() {
        return this.description;
    }
}
