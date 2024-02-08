package krystian.kryszczak.autopay.sdk.itn;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Builder;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * @param fName Customer firstname.
 * @param lName Customer lastname.
 * @param streetName Customer street name.
 * @param streetHouseNo Customer house number.
 * @param streetStaircaseNo Customer staircase number.
 * @param streetPremiseNo Customer premise number.
 * @param postalCode Customer postal code.
 * @param city Customer city.
 * @param nrb Customer bank account number.
 * @param senderData Merged customer data.
 */
@Builder
@XmlRootElement
@XmlType(propOrder = {
    "fName",
    "lName",
    "streetName",
    "streetHouseNo",
    "streetStaircaseNo",
    "streetPremiseNo",
    "postalCode",
    "city",
    "nrb",
    "senderData"
})
public record CustomerData(
    @Nullable String fName,
    @Nullable String lName,
    @Nullable String streetName,
    @Nullable String streetHouseNo,
    @Nullable String streetStaircaseNo,
    @Nullable String streetPremiseNo,
    @Nullable String postalCode,
    @Nullable String city,
    @Nullable String nrb,
    @Nullable String senderData
) implements Serializable {}
