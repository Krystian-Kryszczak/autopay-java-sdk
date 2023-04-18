package krystian.kryszczak.bm.sdk;

import krystian.kryszczak.bm.sdk.transaction.Transaction;
import krystian.kryszczak.bm.sdk.transaction.TransactionData;
import krystian.kryszczak.bm.sdk.transaction.TransactionInit;
import org.junit.jupiter.api.Test;

public final class BlueMediaClientTest {
    final BlueMediaConfiguration configuration = BlueMediaTestConfiguration.get();
    final BlueMediaClient client = new BlueMediaClient(configuration);

    final String testGatewayUrl = "https://pay.bm.pl";


    @Test
    public void getTransactionRedirectTest() {
        final var data = new TransactionData<Transaction>(testGatewayUrl, new TransactionInit());

        final String htmlRedirect = client.getTransactionRedirect(data).blockingGet();
        // TODO
    }

    @Test
    public void test() {
        final String result = client.getRegulationList2(testGatewayUrl).blockingGet();

        System.out.println(result);
    }
}
