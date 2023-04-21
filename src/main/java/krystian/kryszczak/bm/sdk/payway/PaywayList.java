package krystian.kryszczak.bm.sdk.payway;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.common.ServiceRequestBody;
import krystian.kryszczak.bm.sdk.hash.HashGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class PaywayList extends ServiceRequestBody implements Serializable {

    protected PaywayList(@NotNull String serviceId, @NotNull String messageId, @NotNull String hash) {
        super(serviceId, messageId, hash);
    }

    public static PaywayList create(int serviceId, @NotNull String messageId, @NotNull BlueMediaConfiguration configuration) {
        final String serviceIdStr = String.valueOf(serviceId);

        final String hash = HashGenerator.getInstance()
            .generateHash(
                new Object[] {
                    serviceIdStr,
                    messageId
                },
                configuration
            );

        return new PaywayList(
            serviceIdStr,
            messageId,
            hash
        );
    }

}
