package krystian.kryszczak.bm.sdk.regulation;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.common.ServiceRequestBody;
import krystian.kryszczak.bm.sdk.hash.HashGenerator;
import krystian.kryszczak.bm.sdk.hash.Hashable;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class RegulationList extends ServiceRequestBody implements Serializable, Hashable {
    protected RegulationList(@NotNull String serviceId, @NotNull String messageId, @NotNull String hash) {
        super(serviceId, messageId, hash);
    }

    public static RegulationList create(int serviceId, @NotNull String messageId, @NotNull BlueMediaConfiguration configuration) {
        final String serviceIdStr = String.valueOf(serviceId);

        final String hash = HashGenerator.getInstance()
            .generateHash(
                new Object[] {
                    serviceIdStr,
                    messageId
                },
                configuration
            );

        return new RegulationList(
            serviceIdStr,
            messageId,
            hash
        );
    }

    @Override
    public boolean isHashPresent() {
        return false;
    }

    @Override
    public @NotNull Object[] toArrayWithoutHash() {
        return new Object[0];
    }
}
