package krystian.kryszczak.autopay.sdk.serializer;

import krystian.kryszczak.autopay.sdk.itn.Itn;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    public void testSerializeItnDataReturnsItnDto(Map<String, Object> itnData) {
        final Itn itn = serializer.fromMap(itnData, Itn.class);

        assertEquals(itnData.get("orderID"), Objects.requireNonNull(itn).getOrderID());
        assertEquals(itnData.get("remoteID"), itn.getRemoteID());
        assertEquals(itnData.get("amount"), itn.getAmount());
        assertEquals(itnData.get("currency"), itn.getCurrency());
        assertEquals(itnData.get("paymentDate"), itn.getPaymentDate());
        assertEquals(itnData.get("paymentStatus"), itn.getPaymentStatus());
        assertEquals(itnData.get("paymentStatusDetails"), itn.getPaymentStatusDetails());
        if (itnData.get("customerData") instanceof Map<?, ?> customerData) {
            assertEquals(customerData.get("fName"), Objects.requireNonNull(itn.getCustomerData()).fName());
            assertEquals(customerData.get("lName"), itn.getCustomerData().lName());
            assertEquals(customerData.get("streetName"), itn.getCustomerData().streetName());
            assertEquals(customerData.get("streetHouseNo"), itn.getCustomerData().streetHouseNo());
            assertEquals(customerData.get("streetStaircaseNo"), itn.getCustomerData().streetStaircaseNo());
            assertEquals(customerData.get("streetPremiseNo"), itn.getCustomerData().streetPremiseNo());
            assertEquals(customerData.get("postalCode"), itn.getCustomerData().postalCode());
            assertEquals(customerData.get("city"), itn.getCustomerData().city());
            assertEquals(customerData.get("nrb"), itn.getCustomerData().nrb());
            assertEquals(customerData.get("senderData"), itn.getCustomerData().senderData());
        } else {
            assertNull(itn.getCustomerData());
        }
        assertEquals(itnData.get("hash"), itn.getHash());
    }

    public Object[] getItns() {
        final Map<String, Object> itn0 = new HashMap<>();
        itn0.put("serviceID", "service_1");
        itn0.put("orderID", "order_1");
        itn0.put("remoteID", "remote_1");
        itn0.put("amount", "123");
        itn0.put("currency", "PLN");
        itn0.put("gatewayID", 1);
        itn0.put("paymentDate", "09.09.2020");
        itn0.put("paymentStatus", "OK");
        itn0.put("paymentStatusDetails", "details_1");
        itn0.put("customerData", Map.of(
            "fName", "fname_1",
            "lName", "lname_1",
            "streetName", "street_name_1",
            "streetHouseNo", "street_house_no_1",
            "streetStaircaseNo", "street_staircase_no_1",
            "streetPremiseNo", "street_premise_no_1",
            "postalCode", "postal_code_1",
            "city", "city_1",
            "nrb", "nrb_1",
            "senderData", "sender_data_1"
        ));
        itn0.put("hash", "123asd123asd");

        final Map<String, Object> itn1 = new HashMap<>();
        itn1.put("serviceID", "service_2");
        itn1.put("orderID", "order_2");
        itn1.put("remoteID", "remote_2");
        itn1.put("amount", "321");
        itn1.put("currency", "EUR");
        itn1.put("gatewayID", 15);
        itn1.put("paymentDate", "12.09.2020");
        itn1.put("paymentStatus", "START");
        itn1.put("paymentStatusDetails", "details_2");
        itn1.put("customerData", Map.of(
            "fName", "fname_2",
            "lName", "lname_2",
            "streetName", "street_name_2",
            "streetHouseNo", "street_house_no_2",
            "postalCode", "postal_code_2",
            "city", "city_2",
            "nrb", "nrb_2"
        ));
        itn1.put("hash", "56465as4d65465");

        return new Object[] {
            itn0,
            itn1,
            Map.of(
                "serviceID", "service_3",
                "orderID", "order_3",
                "remoteID", "remote_3",
                "amount", "13",
                "currency", "USD",
                "gatewayID", 12,
                "paymentDate", "09.09.2022",
                "paymentStatus", "PENDING",
                "hash", "123asd234565sddsgh"
            )
        };
    }
}
