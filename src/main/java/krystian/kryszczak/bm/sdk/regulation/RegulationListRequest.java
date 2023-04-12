package krystian.kryszczak.bm.sdk.regulation;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.hash.HashGenerator;
import krystian.kryszczak.bm.sdk.hash.Hashable;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public final class RegulationListRequest implements Hashable { // Request
    final @NotNull String gatewayUrl;
    final int serviceId;
    final @NotNull String messageId;
    final @NotNull String hash;

    private RegulationListRequest(@NotNull String gatewayUrl, int serviceId,
                                  @NotNull String messageId, @NotNull String hash) {
        this.gatewayUrl = gatewayUrl;
        this.serviceId = serviceId;
        this.messageId = messageId;
        this.hash = hash;
    }

    public static RegulationListRequest create(final @NotNull String gatewayUrl,
                                               final @NotNull int serviceId,
                                               final @NotNull String messageId,
                                               final @NotNull BlueMediaConfiguration configuration) {
        return new RegulationListRequest(
            gatewayUrl,
            serviceId,
            messageId,
            HashGenerator.instance.generateHash(
                new Object[] {
                    gatewayUrl,
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
            gatewayUrl,
            serviceId,
            messageId
        };
    }
}
