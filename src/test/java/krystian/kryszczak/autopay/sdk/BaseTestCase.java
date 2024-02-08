package krystian.kryszczak.autopay.sdk;

import krystian.kryszczak.autopay.sdk.hash.HashType;

public abstract class BaseTestCase {
    protected static final int SERVICE_ID = 123456;
    protected static final String WRONG_SERVICE_ID = "123456789012";
    protected static final String GATEWAY_URL = "https://testpay.autopay.eu";

    protected static final String SHARED_KEY = "QCBm3N0oFjzQAWsTIVN8mPLK12TW6HU6InSfjvnF";

    protected static final String WRONG_HASH_ALGO = "wrong_algo";
    protected static final String HASH_SEPARATOR = "|";

    protected static final HashType HASH_SHA256 = HashType.SHA256;

    protected AutopayConfiguration getTestConfiguration() {
        return new AutopayConfiguration(
            SERVICE_ID,
            SHARED_KEY,
            HASH_SHA256,
            HASH_SEPARATOR
        );
    }
}
