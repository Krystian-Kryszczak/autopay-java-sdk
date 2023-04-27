package krystian.kryszczak.bm.sdk.transaction.request;

import krystian.kryszczak.bm.sdk.transaction.TransactionBackground;
import org.jetbrains.annotations.NotNull;

public final class TransactionBackgroundRequest extends TransactionRequest<TransactionBackground> {
    private TransactionBackgroundRequest(@NotNull String gatewayUrl, @NotNull TransactionBackground transaction) {
        super(gatewayUrl, transaction);
    }

    public static @NotNull Builder.Required builder() {
        return new Builder.Required();
    }

    public static final class Builder extends TransactionRequest.Builder<TransactionBackground> {
        public static final class Required extends TransactionRequest.Builder.Required<TransactionBackground> {

            @Override
            public @NotNull WithGatewayUrl setGatewayUrl(@NotNull String gatewayUrl) {
                return new WithGatewayUrl(gatewayUrl);
            }

            @Override
            public @NotNull WithTransaction setTransaction(@NotNull TransactionBackground transaction) {
                return new WithTransaction(transaction);
            }
        }

        public static final class WithGatewayUrl extends TransactionRequest.Builder.WithGatewayUrl<TransactionBackground> {
            private String gatewayUrl;
            private WithGatewayUrl(String gatewayUrl) {
                super(gatewayUrl);
            }

            public @NotNull Completer setTransaction(@NotNull TransactionBackground transaction) {
                return new Completer(gatewayUrl, transaction);
            }
        }

        public static final class WithTransaction extends TransactionRequest.Builder.WithTransaction<TransactionBackground> {
            private WithTransaction(@NotNull TransactionBackground transaction) {
                super(transaction);
            }

            public @NotNull Completer setGatewayUrl(@NotNull String gatewayUrl) {
                return new Completer(gatewayUrl, super.getTransaction());
            }
        }

        public static final class Completer extends TransactionRequest.Builder.Completer<TransactionBackground> {
            private Completer(@NotNull String gatewayUrl, @NotNull TransactionBackground transaction) {
                super(gatewayUrl, transaction);
            }

            @Override
            public @NotNull TransactionBackgroundRequest build() {
                return new TransactionBackgroundRequest(this.gatewayUrl, this.transaction);
            }
        }
    }
}
