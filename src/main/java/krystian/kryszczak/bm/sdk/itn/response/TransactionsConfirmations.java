package krystian.kryszczak.bm.sdk.itn.response;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public record TransactionsConfirmations(@NotNull TransactionConfirmed transactionConfirmed) implements Serializable {}
