package krystian.kryszczak.bm.sdk.transaction;

import org.jetbrains.annotations.NotNull;

public record TransactionData<T extends Transaction>(@NotNull String gatewayUrl, @NotNull T transaction) {}
