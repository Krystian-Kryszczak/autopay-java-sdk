package krystian.kryszczak.autopay.sdk.payway;

import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.common.ServiceHttpRequestBody;
import krystian.kryszczak.autopay.sdk.hash.HashGenerator;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@ToString
@EqualsAndHashCode(callSuper = false)
public class PaywayList extends ServiceHttpRequestBody implements Serializable {
    protected PaywayList(@NotNull String serviceID, @NotNull String messageID, @NotNull String hash) {
        super(serviceID, messageID, hash);
    }

    public static @NotNull PaywayList create(int serviceID, @NotNull String messageID, @NotNull AutopayConfiguration configuration) {
        final String serviceIdStr = String.valueOf(serviceID);

        final String hash = HashGenerator.generateHash(
            new Object[] {
                serviceIdStr,
                messageID
            },
            configuration
        );

        return new PaywayList(
            serviceIdStr,
            messageID,
            hash
        );
    }

    @Override
    public @NotNull Object[] toArray() {
        return new Object[] {
            serviceID,
            messageID
        };
    }
}
