package krystian.kryszczak.bm.sdk.transfer;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface TransferReady {
    @NotNull Map<@NotNull String, @NotNull String> fieldsMapWithCapitalizedKeysAndFormattedValues();
}
