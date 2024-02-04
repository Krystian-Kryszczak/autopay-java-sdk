package krystian.kryszczak.autopay.sdk.itn.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import krystian.kryszczak.autopay.sdk.hash.Hashable;
import krystian.kryszczak.autopay.sdk.itn.Itn;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;
import java.io.Serializable;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@JsonRootName("transactionList")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "serviceID", "transactions", "hash" })
@AllArgsConstructor(access = AccessLevel.PUBLIC, onConstructor_ = {
    @JsonCreator,
    @ConstructorProperties({ "serviceID", "transactions", "hash" })
})
public final class ItnRequest extends Hashable implements Serializable {
    private final int serviceID;
    private final @NotNull Transactions transactions;
    private final @NotNull String hash;

    @Override
    public @NotNull Object @NotNull [] toArray() {
        final Itn[] transactions = this.transactions.transaction();

        int dstSize = 1;
        final Object[][] arrays = new Object[transactions.length][];
        for (int i = 0; i < transactions.length; i++) {
            final Object[] array = transactions[i].toArray();
            arrays[i] = array;
            dstSize += array.length;
        }

        final Object[] dst = new Object[dstSize];
        dst[0] = serviceID;

        int i = 1;
        for (final Object[] array : arrays) {
            final int length = array.length;
            System.arraycopy(array, 0, dst, i, length);
            i += length;
        }

        return dst;
    }
}
