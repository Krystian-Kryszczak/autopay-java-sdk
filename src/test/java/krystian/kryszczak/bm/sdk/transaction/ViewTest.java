package krystian.kryszczak.bm.sdk.transaction;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.transaction.request.TransactionRequest;
import org.junit.jupiter.api.Test;

public final class ViewTest {
    final BlueMediaConfiguration configuration = BlueMediaConfiguration.fromEnvironmentVariables();

    @Test
    void createRedirectHtmlTest() {
        final TransactionRequest<? extends Transaction> transactionRequest = null; // TODO
        View.createRedirectHtml(transactionRequest);
    }
}
