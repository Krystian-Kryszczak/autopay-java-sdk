package krystian.kryszczak.bm.sdk.payway;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.common.ServiceHttpRequestBody;
import krystian.kryszczak.bm.sdk.hash.HashGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class PaywayList extends ServiceHttpRequestBody implements Serializable {

    protected PaywayList(@NotNull String serviceID, @NotNull String messageID, @NotNull String hash) {
        super(serviceID, messageID, hash);
    }

    public static PaywayList create(int serviceID, @NotNull String messageID, @NotNull BlueMediaConfiguration configuration) {
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
