package krystian.kryszczak.bm.sdk.transaction;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.BlueMediaTestConfiguration;
import krystian.kryszczak.bm.sdk.transaction.dto.TransactionDto;
import org.junit.jupiter.api.Test;

public final class ViewTest {
    final BlueMediaConfiguration configuration = BlueMediaTestConfiguration.get();

    @Test
    void createRedirectHtmlTest() {
        final TransactionData transactionData = null; // TODO
        View.createRedirectHtml(TransactionDto.create(transactionData, this.configuration));
    }
}
