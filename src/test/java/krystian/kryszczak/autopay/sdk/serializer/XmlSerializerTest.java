package krystian.kryszczak.autopay.sdk.serializer;

import fixtures.itn.ItnFixture;
import fixtures.payway.PaywayListFixture;
import fixtures.regulation.RegulationListFixture;
import fixtures.transaction.TransactionBackgroundFixture;
import fixtures.transaction.TransactionInitFixture;
import krystian.kryszczak.autopay.sdk.itn.response.ItnResponse;
import krystian.kryszczak.autopay.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.autopay.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.autopay.sdk.transaction.TransactionBackground;
import krystian.kryszczak.autopay.sdk.transaction.TransactionContinue;
import krystian.kryszczak.autopay.sdk.transaction.TransactionInit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;
import static org.xmlunit.assertj.XmlAssert.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class XmlSerializerTest {
    private static Serializer serializer;

    @BeforeAll
    public static void setUp() {
        serializer = new XmlSerializer();
    }

    @Test
    public void testDeserializeReturnsItnResponse() {
        final String itnResponse = ItnFixture.getItnResponse();
        final ItnResponse deserialized = serializer.deserialize(itnResponse, ItnResponse.class);

        assertNotNull(deserialized);
        assertThat(serializer.serialize(deserialized))
            .and(itnResponse)
            .ignoreWhitespace()
            .areIdentical();
    }

    @Test
    public void testDeserializeReturnsPaywayListResponse() {
        final String paywayListResponse = PaywayListFixture.getPaywayListResponse();
        final PaywayListResponse deserialized = serializer.deserialize(paywayListResponse, PaywayListResponse.class);

        assertNotNull(deserialized);
        assertThat(serializer.serialize(deserialized))
            .and(paywayListResponse)
            .ignoreWhitespace()
            .areIdentical();
    }

    @Test
    public void testDeserializeReturnsRegulationList() {
        final String regulationListResponse = RegulationListFixture.getRegulationListResponse();
        final RegulationListResponse deserialized = serializer.deserialize(regulationListResponse, RegulationListResponse.class);

        assertNotNull(deserialized);
        assertThat(serializer.serialize(deserialized))
            .and(regulationListResponse)
            .ignoreWhitespace()
            .areIdentical();
    }

    @Test
    public void testDeserializeReturnsTransactionBackgroundResponse() {
        final String transactionBackgroundResponse = TransactionBackgroundFixture.getTransactionBackgroundResponse();
        final TransactionBackground deserialized = serializer.deserialize(transactionBackgroundResponse, TransactionBackground.class);

        assertNotNull(deserialized);
        assertThat(serializer.serialize(deserialized))
            .and(transactionBackgroundResponse)
            .ignoreWhitespace()
            .areIdentical();
    }

    @Test
    public void testDeserializeReturnsTransactionInitContinueResponse() {
        final String transactionInitContinueResponse = TransactionInitFixture.getTransactionInitContinueResponse();
        final TransactionContinue deserialized = serializer.deserialize(transactionInitContinueResponse, TransactionContinue.class);

        assertNotNull(deserialized);
        assertThat(serializer.serialize(deserialized))
            .and(transactionInitContinueResponse)
            .ignoreWhitespace()
            .areIdentical();
    }

    @Test
    public void testDeserializeReturnsTransactionInitResponse() {
        final String transactionInitResponse = TransactionInitFixture.getTransactionInitResponse();
        final TransactionInit deserialized = serializer.deserialize(transactionInitResponse, TransactionInit.class);

        assertNotNull(deserialized);
        assertThat(serializer.serialize(deserialized))
            .and(transactionInitResponse)
            .ignoreWhitespace()
            .areIdentical();
    }

    @ParameterizedTest
    @MethodSource("wrongXmlProvider")
    public void testParseReturnsNullOnWrongXml(String wrongXml) {
        assertNull(serializer.deserialize(wrongXml, Serializable.class));
    }

    @Contract(value = " -> new", pure = true)
    public String @NotNull [] wrongXmlProvider() {
        return new String[] {
            "ERROR",
            "",
            "wrong_xml",
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        };
    }
}
