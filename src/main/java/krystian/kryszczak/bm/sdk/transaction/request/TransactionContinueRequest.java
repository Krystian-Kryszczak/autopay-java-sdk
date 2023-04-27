package krystian.kryszczak.bm.sdk.transaction.request;

import krystian.kryszczak.bm.sdk.transaction.TransactionContinue;
import org.jetbrains.annotations.NotNull;

public final class TransactionContinueRequest extends TransactionRequest<TransactionContinue> {
    private TransactionContinueRequest(@NotNull String gatewayUrl, @NotNull TransactionContinue transaction) {
        super(gatewayUrl, transaction);
    }

    public static Builder.Required builder() {
        return new Builder.Required();
    }
    
    public static final class Builder extends TransactionRequest.Builder<TransactionContinue> {
        public static final class Required extends TransactionRequest.Builder.Required<TransactionContinue> {

            @Override
            public @NotNull WithGatewayUrl setGatewayUrl(@NotNull String gatewayUrl) {
                return new WithGatewayUrl(gatewayUrl);
            }

            @Override
            public @NotNull WithTransaction setTransaction(@NotNull TransactionContinue transaction) {
                return new WithTransaction(transaction);
            }
        }

        public static final class WithGatewayUrl extends TransactionRequest.Builder.WithGatewayUrl<TransactionContinue> {
            private String gatewayUrl;
            private WithGatewayUrl(String gatewayUrl) {
                super(gatewayUrl);
            }

            public @NotNull TransactionRequest.Builder.Completer<TransactionContinue> setTransaction(@NotNull TransactionContinue transaction) {
                return new Completer(gatewayUrl, transaction);
            }
        }

        public static final class WithTransaction extends TransactionRequest.Builder.WithTransaction<TransactionContinue> {
            private WithTransaction(@NotNull TransactionContinue transaction) {
                super(transaction);
            }

            public @NotNull Completer setGatewayUrl(@NotNull String gatewayUrl) {
                return new Completer(gatewayUrl, super.getTransaction());
            }
        }

        public static final class Completer extends TransactionRequest.Builder.Completer<TransactionContinue> {
            private Completer(@NotNull String gatewayUrl, @NotNull TransactionContinue transaction) {
                super(gatewayUrl, transaction);
            }

            @Override
            public @NotNull TransactionContinueRequest build() {
                return new TransactionContinueRequest(this.gatewayUrl, this.transaction);
            }
        }
    }
}
