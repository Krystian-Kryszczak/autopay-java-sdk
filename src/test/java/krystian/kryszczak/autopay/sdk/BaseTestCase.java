package krystian.kryszczak.autopay.sdk;

import krystian.kryszczak.autopay.sdk.hash.HashType;
import krystian.kryszczak.autopay.sdk.transaction.Transaction;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class BaseTestCase {
    protected static final int SERVICE_ID = 123456;
    protected static final String WRONG_SERVICE_ID = "123456789012";
    protected static final String GATEWAY_URL = "https://pay-accept.bm.pl";

    protected static final String SHARED_KEY = "QCBm3N0oFjzQAWsTIVN8mPLK12TW6HU6InSfjvnF";

    protected static final String WRONG_HASH_ALGO = "wrong_algo";
    protected static final String HASH_SEPARATOR = "|";

    protected static final HashType HASH_SHA256 = HashType.SHA256;

    protected AutopayConfiguration getConfigurationStub() {
        return new AutopayConfiguration(
            SERVICE_ID,
            SHARED_KEY,
            HASH_SHA256,
            HASH_SEPARATOR
        );
    }

    protected Transaction getTransactionDtoStub() {
        final var transaction = mock(Transaction.class);

        when(transaction.getOrderID()).thenReturn("123-1234");
        when(transaction.getAmount()).thenReturn("100.60");
        when(transaction.getDescription()).thenReturn("transakcja");
        when(transaction.getGatewayID()).thenReturn(0);
        when(transaction.getCurrency()).thenReturn("PLN");
        when(transaction.getCustomerEmail()).thenReturn("test@test.test");
        when(transaction.getReturnURL()).thenReturn("https://google.com");

        return transaction;
    }
}
