package krystian.kryszczak.bm.sdk.itn;

import krystian.kryszczak.bm.sdk.common.AbstractValueObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public final class CustomerData extends AbstractValueObject implements Serializable {

    /**
     * Customer firstname.
     */
    protected final @NotNull String fName;

    /**
     * Customer lastname.
     */
    protected final @NotNull String lName;

    /**
     * Customer street name.
     */
    protected final @NotNull String streetName;

    /**
     * Customer street name.
     */
    protected final @NotNull String streetHouseNo;

    /**
     * Customer staircase number.
     */
    protected final @NotNull String streetStaircaseNo;

    /**
     * Customer premise number.
     */
    protected final @NotNull String streetPremiseNo;

    /**
     * Customer postal code.
     */
    protected final @NotNull String postalCode;

    /**
     * Customer postal code.
     */
    protected final @NotNull String city;

    /**
     * Customer bank account number.
     */
    protected final @NotNull String nrb;

    /**
     * Merged customer data.
     */
    protected final @NotNull String senderData;
}
