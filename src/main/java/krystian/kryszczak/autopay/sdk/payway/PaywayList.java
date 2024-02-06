package krystian.kryszczak.autopay.sdk.payway;

import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.common.ServiceHttpRequestBody;
import krystian.kryszczak.autopay.sdk.hash.HashGenerator;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class PaywayList extends ServiceHttpRequestBody implements Serializable {
    protected PaywayList(int serviceID, @NotNull String messageID, @NotNull String hash) {
        super(serviceID, messageID, hash);
    }

    public static @NotNull PaywayList create(int serviceID, @NotNull String messageID, @NotNull AutopayConfiguration configuration) {
        final String hash = HashGenerator.generateHash(
            new Object[] {
                serviceID,
                messageID
            },
            configuration
        );

        return new PaywayList(
            serviceID,
            messageID,
            hash
        );
    }
}
