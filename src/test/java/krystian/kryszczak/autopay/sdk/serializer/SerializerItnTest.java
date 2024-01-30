package krystian.kryszczak.autopay.sdk.serializer;

import krystian.kryszczak.autopay.sdk.itn.CustomerData;
import krystian.kryszczak.autopay.sdk.itn.Itn;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public final class SerializerItnTest {
    private static Serializer serializer;

    @BeforeAll
    public static void setUp() {
        serializer = new XmlSerializer();
    }

    @ParameterizedTest
    @MethodSource("getItns")
    public void testSerializedAndDeserializedItnAreEquals(Itn itn) {
        final String serialized = serializer.serialize(itn);
        assertNotNull(serialized);

        final Itn deserialized = serializer.deserialize(serialized, Itn.class);
        assertNotNull(deserialized);

        assertEquals(itn.getOrderID(), deserialized.getOrderID());
        assertEquals(itn.getRemoteID(), deserialized.getRemoteID());
        assertEquals(itn.getAmount(), deserialized.getAmount());
        assertEquals(itn.getCurrency(), deserialized.getCurrency());
        assertEquals(itn.getPaymentDate(), deserialized.getPaymentDate());
        assertEquals(itn.getPaymentStatus(), deserialized.getPaymentStatus());
        assertEquals(itn.getPaymentStatusDetails(), deserialized.getPaymentStatusDetails());
        assertEquals(itn.getCustomerData(), deserialized.getCustomerData());
        assertEquals(itn.getHash(), deserialized.getHash());
    }

    @Contract(" -> new")
    public Itn @NotNull [] getItns() {
        return new Itn[] {
            Itn.builder()
                .serviceID("service_1")
                .orderID("order_1")
                .remoteID("remote_1")
                .amount("123")
                .currency("PLN")
                .gatewayID(1)
                .paymentDate("09.09.2020")
                .paymentStatus("OK")
                .paymentStatusDetails("details_1")
                .customerData(
                    CustomerData.builder()
                        .fName("fname_1")
                        .lName("lname_1")
                        .streetName("street_name_1")
                        .streetHouseNo("street_house_no_1")
                        .streetStaircaseNo("street_staircase_no_1")
                        .streetPremiseNo("street_premise_no_1")
                        .postalCode("postal_code_1")
                        .city("city_1")
                        .nrb("nrb_1")
                        .senderData("sender_data_1")
                        .build()
                ).hash("123asd123asd")
                .build(),
            Itn.builder()
                .serviceID("service_2")
                .orderID("order_2")
                .remoteID("remote_2")
                .amount("321")
                .currency("EUR")
                .gatewayID(15)
                .paymentDate("12.09.2020")
                .paymentStatus("START")
                .paymentStatusDetails("details_2")
                .customerData(
                    CustomerData.builder()
                        .fName("fname_2")
                        .lName("lname_2")
                        .streetName("street_name_2")
                        .streetHouseNo("street_house_no_2")
                        .postalCode("postal_code_2")
                        .city("city_2")
                        .nrb("nrb_2")
                        .build()
                ).hash("56465as4d65465")
                .build(),
            Itn.builder()
                .serviceID("service_3")
                .orderID("order_3")
                .remoteID("remote_3")
                .amount("13")
                .currency("USD")
                .gatewayID(12)
                .paymentDate("09.09.2022")
                .paymentStatus("PENDING")
                .hash("123asd234565sddsgh")
                .build()
        };
    }
}
