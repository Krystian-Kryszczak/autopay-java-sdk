package krystian.kryszczak.bm.sdk.transaction.parser;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.common.parser.ResponseParser;
import krystian.kryszczak.bm.sdk.transaction.Transaction;
import org.jetbrains.annotations.NotNull;

public final class TransactionResponseParser<T extends Transaction> extends ResponseParser<T> {
    public TransactionResponseParser(@NotNull String response, @NotNull BlueMediaConfiguration configuration) {
        super(response, configuration);
    }

    public @NotNull T parse() {
        return parse(false);
    }

    public @NotNull T parse(boolean transactionInit) {
        // TODO
        return null;
    }
}
