package krystian.kryszczak.bm.sdk.regulation;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.common.ServiceHttpRequestBody;
import krystian.kryszczak.bm.sdk.hash.HashGenerator;
import krystian.kryszczak.bm.sdk.hash.Hashable;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@NoArgsConstructor
public class RegulationList extends ServiceHttpRequestBody implements Serializable, Hashable {
    protected RegulationList(@NotNull String serviceID, @NotNull String messageID, @Nullable String hash) {
        super(serviceID, messageID, hash);
    }

    public static RegulationList create(int serviceID, @NotNull String messageID, @NotNull BlueMediaConfiguration configuration) {
        final String serviceIdStr = String.valueOf(serviceID);

        final String hash = HashGenerator.getInstance()
            .generateHash(
                new Object[] {
                    serviceIdStr,
                    messageID
                },
                configuration
            );

        return new RegulationList(
            serviceIdStr,
            messageID,
            hash
        );
    }

    @Override
    public @NotNull Object[] toArrayWithoutHash() {
        return new Object[] {
            serviceID,
            messageID
        };
    }
}
