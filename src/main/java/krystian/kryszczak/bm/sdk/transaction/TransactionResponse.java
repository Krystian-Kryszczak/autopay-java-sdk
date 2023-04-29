package krystian.kryszczak.bm.sdk.transaction;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public abstract sealed class TransactionResponse extends Transaction permits TransactionInit, TransactionContinue {}
