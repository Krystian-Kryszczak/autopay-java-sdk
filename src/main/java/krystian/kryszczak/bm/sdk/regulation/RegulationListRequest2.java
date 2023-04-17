package krystian.kryszczak.bm.sdk.regulation;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.hash.HashGenerator;
import krystian.kryszczak.bm.sdk.hash.Hashable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
@Getter
public final class RegulationListRequest2 implements Hashable { // Request
    final int serviceId;
    final @NotNull String messageId;
    final int gatewayId;
    final String language;
    final @NotNull String hash;

    public static RegulationListRequest2 create(final @NotNull int gatewayId,
                                                final int serviceId,
                                                final @NotNull String messageId,
                                                final @NotNull BlueMediaConfiguration configuration) {
        return new RegulationListRequest2(
            serviceId,
            messageId,
            gatewayId,
            "PL",
            HashGenerator.instance.generateHash(
                new Object[] {
                    gatewayId,
                    serviceId,
                    messageId
                },
                configuration
            )
        );
    }

    @Override
    public boolean isHashPresent() {
        return hash != null;
    }

    @Override
    public @NotNull Object[] toArrayWithoutHash() {
        return new Object[]{
            serviceId,
            messageId,
            gatewayId,
            language,
        };
    }
}
