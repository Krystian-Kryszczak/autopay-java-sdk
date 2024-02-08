package krystian.kryszczak.autopay.sdk.hash;

import fixtures.itn.ItnFixture;
import fixtures.payway.PaywayListFixture;
import fixtures.regulation.RegulationListFixture;
import fixtures.transaction.TransactionBackgroundFixture;
import fixtures.transaction.TransactionInitFixture;
import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.BaseTestCase;
import krystian.kryszczak.autopay.sdk.itn.request.ItnRequest;
import krystian.kryszczak.autopay.sdk.itn.response.ItnResponse;
import krystian.kryszczak.autopay.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.autopay.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.autopay.sdk.serializer.Serializer;
import krystian.kryszczak.autopay.sdk.transaction.TransactionBackground;
import krystian.kryszczak.autopay.sdk.transaction.TransactionContinue;
import krystian.kryszczak.autopay.sdk.transaction.TransactionInit;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class HashGeneratorTest extends BaseTestCase {
    private static Serializer serializer;

    @BeforeAll
    public static void setUp() {
        serializer = Serializer.createDefault();
    }

    @Test
    public void generateHashReturnsHashEqualsItnInRequestFixture() {
        final ItnRequest itnRequest = serializer.deserialize(ItnFixture.getItnInRequest(), ItnRequest.class);
        assertHashIsValid(itnRequest);
    }

    @Test
    public void generateHashReturnsHashEqualsItnResponseFixture() {
        final ItnRequest itnRequest = serializer.deserialize(ItnFixture.getItnInRequest(), ItnRequest.class);
        assertNotNull(itnRequest);

        final ItnResponse itnResponse = serializer.deserialize(ItnFixture.getItnResponse(), ItnResponse.class);
        assertNotNull(itnResponse);

        final String exceptedHash = itnResponse.getHash();
        assertNotNull(exceptedHash);
        final String hash = ItnResponse.create(itnRequest.getTransactions().transaction()[0], true, getTestConfiguration()).getHash();
        assertEquals(exceptedHash, hash);
    }

    @Test
    public void generateHashReturnsHashEqualsItnResponse2Fixture() {
        final ItnRequest itnRequest = serializer.deserialize(ItnFixture.getItnInRequest(), ItnRequest.class);
        assertNotNull(itnRequest);

        final ItnResponse itnResponse = serializer.deserialize(ItnFixture.getItnResponse(), ItnResponse.class);
        assertNotNull(itnResponse);

        final String exceptedHash = itnResponse.getHash();
        assertNotNull(exceptedHash);
        final String hash = ItnResponse.create(itnRequest, itn -> true, getTestConfiguration()).getHash();
        assertEquals(exceptedHash, hash);
    }

    @Test
    public void generateHashReturnsHashEqualsPaywayListResponseFixture() {
        final PaywayListResponse paywayListResponse = serializer.deserialize(
            PaywayListFixture.getPaywayListResponse(), PaywayListResponse.class);
        assertHashIsValid(paywayListResponse);
    }

    @Test
    public void generateHashReturnsHashEqualsRegulationListFixture() {
        final RegulationListResponse regulationListResponse = serializer.deserialize(
            RegulationListFixture.getRegulationListResponse(), RegulationListResponse.class);
        assertHashIsValid(regulationListResponse);
    }

    @Test
    public void generateHashReturnsHashEqualsTransactionBackgroundResponseFixture() {
        final TransactionBackground transactionBackground = serializer.deserialize(
            TransactionBackgroundFixture.getTransactionBackgroundResponse(), TransactionBackground.class);
        assertHashIsValid(transactionBackground);
    }

    @Test
    public void generateHashReturnsHashEqualsTransactionInitContinueResponseFixture() {
        final TransactionContinue transactionContinue = serializer.deserialize(
            TransactionInitFixture.getTransactionInitContinueResponse(), TransactionContinue.class);
        assertHashIsValid(transactionContinue);
    }

    @Test
    public void generateHashReturnsHashEqualsTransactionInitResponseFixture() {
        final TransactionInit transactionInit = serializer.deserialize(
            TransactionInitFixture.getTransactionInitResponse(), TransactionInit.class);
        assertHashIsValid(transactionInit);
    }

    private void assertHashIsValid(Hashable hashable) {
        assertNotNull(hashable);
        final String exceptedHash = hashable.getHash();
        assertNotNull(exceptedHash);
        final var array = hashable.toArray();
        final String hash = HashGenerator.generateHash(array, getTestConfiguration());
        assertEquals(exceptedHash, hash);
    }

    @Test
    public void generateHashReturnsExpectedHash() {
        final Object[] data = new Object[] {
            getTestConfiguration().serviceId(),
            123,
            1.20
        };

        final String exceptedHash = generateHash(data);

        final String hash = HashGenerator.generateHash(data, getTestConfiguration());

        assertEquals(exceptedHash, hash);
    }

    private @NotNull String generateHash(Object[] array) {
        final AutopayConfiguration configuration = getTestConfiguration();
        final String hashSeparator = configuration.hashSeparator();
        final String data = Arrays.stream(array).map(Object::toString)
            .collect(Collectors.joining(hashSeparator)) + hashSeparator + configuration.sharedKey();

        return HashGenerator.encode(data, configuration.hashAlgorithm().toString());
    }
}
