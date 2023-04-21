package krystian.kryszczak.bm.sdk.itn.response;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public record TransactionConfirmed(@NotNull String orderID, @NotNull String confirmation) implements Serializable {} // TODO check if orderID can be int type
