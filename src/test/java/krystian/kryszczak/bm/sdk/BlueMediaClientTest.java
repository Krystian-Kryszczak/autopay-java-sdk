package krystian.kryszczak.bm.sdk;

import krystian.kryszczak.bm.sdk.confirmation.Confirmation;
import krystian.kryszczak.bm.sdk.hash.HashGenerator;
import krystian.kryszczak.bm.sdk.transaction.Transaction;
import krystian.kryszczak.bm.sdk.transaction.TransactionData;
import krystian.kryszczak.bm.sdk.transaction.TransactionInit;
import krystian.kryszczak.bm.sdk.util.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class BlueMediaClientTest {
    final BlueMediaConfiguration configuration = BlueMediaConfiguration.fromEnvironmentVariables();
    final BlueMediaClient client = new BlueMediaClient(configuration);

    final String gatewayUrl = "https://pay.bm.pl";


    @Test
    public void getTransactionRedirectTest() {
        final var data = new TransactionData<Transaction>(gatewayUrl, new TransactionInit());

        final String htmlRedirect = client.getTransactionRedirect(data).blockingGet();
        // TODO
    }

    @Test
    public void doTransactionBackgroundTest() {
        // TODO
    }

    @Test
    public void doTransactionInit() {
        // TODO
    }

    @Test
    public void doItnInTest() {
        // TODO
    }

    @Test
    public void doItnInResponseTest() {
        // TODO
    }

    @Test
    public void getPaywayListTest() {
        // TODO
    }

    @Test
    public void getRegulationListTest() {
        final var response = client.getRegulationList(gatewayUrl)
            .blockingGet();

        assertNotNull(response);
        // TODO
    }

    @Test
    public void checkHashTest() {
        // TODO
    }

    @Test
    public void doConfirmationCheckTest() {
        final int serviceId = configuration.getServiceId();
        final String randomMessageId = RandomUtils.randomMessageId();

        final var data = new Object[] {
            serviceId,
            randomMessageId
        };

        final String hash = HashGenerator.instance.generateHash(data, configuration);
        final Confirmation confirmation = new Confirmation(serviceId, randomMessageId, hash);

        assertTrue(client.doConfirmationCheck(confirmation));
    }

    @Test
    public void getItnObjectTest() {
        // TODO
    }
}
