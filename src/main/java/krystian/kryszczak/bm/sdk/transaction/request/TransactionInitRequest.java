package krystian.kryszczak.bm.sdk.transaction.request;

import krystian.kryszczak.bm.sdk.common.util.Translations;
import krystian.kryszczak.bm.sdk.transaction.TransactionInit;
import org.jetbrains.annotations.NotNull;

public final class TransactionInitRequest extends TransactionRequest<TransactionInit> {
    private TransactionInitRequest(@NotNull String gatewayUrl, @NotNull TransactionInit transaction, @NotNull Translations.Language language) {
        super(gatewayUrl, transaction, language);
    }

    @Override
    public @NotNull TransactionInit getTransaction() {
        return super.getTransaction();
    }

    public static Builder.Required builder() {
        return new Builder.Required();
    }

    public static final class Builder extends TransactionRequest.Builder<TransactionInit> {
        public static final class Required extends TransactionRequest.Builder.Required<TransactionInit> {

            @Override
            public @NotNull WithGatewayUrl setGatewayUrl(@NotNull String gatewayUrl) {
                return new WithGatewayUrl(gatewayUrl);
            }

            @Override
            public @NotNull WithTransaction setTransaction(@NotNull TransactionInit transaction) {
                return new WithTransaction(transaction);
            }
        }

        public static final class WithGatewayUrl extends TransactionRequest.Builder.WithGatewayUrl<TransactionInit> {
            private WithGatewayUrl(String gatewayUrl) {
                super(gatewayUrl);
            }

            public @NotNull Completer setTransaction(@NotNull TransactionInit transaction) {
                return new TransactionInitRequest.Builder.Completer(super.getGatewayUrl(), transaction);
            }
        }

        public static final class WithTransaction extends TransactionRequest.Builder.WithTransaction<TransactionInit> {
            private WithTransaction(@NotNull TransactionInit transaction) {
                super(transaction);
            }

            public @NotNull Completer setGatewayUrl(@NotNull String gatewayUrl) {
                return new Completer(gatewayUrl, super.getTransaction());
            }
        }

        public static final class Completer extends TransactionRequest.Builder.Completer<TransactionInit> {
            private Completer(@NotNull String gatewayUrl, @NotNull TransactionInit transaction) {
                super(gatewayUrl, transaction);
            }

            @Override
            public @NotNull TransactionInitRequest build() {
                return new TransactionInitRequest(this.gatewayUrl, this.transaction, this.language);
            }
        }
    }
}
