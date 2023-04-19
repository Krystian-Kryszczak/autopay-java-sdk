package krystian.kryszczak.bm.sdk.regulation;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.common.ServiceRequest;
import krystian.kryszczak.bm.sdk.hash.HashGenerator;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;

public class RegulationList extends ServiceRequest implements Serializable {
    protected RegulationList(@NotNull String serviceId, @NotNull String messageId, @NotNull String hash) {
        super(serviceId, messageId, hash);
    }

    public static RegulationList create(@NotNull int serviceId, @NotNull String messageId, @NotNull BlueMediaConfiguration configuration) {
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
    public @NotNull Map<@NotNull String, @NotNull String> fieldsMapWithCapitalizedKeysAndFormattedValues() {
        return Map.of(
            "ServiceID", serviceId,
            "MessageID", messageId,
            "Hash", hash
        );
    }
}
