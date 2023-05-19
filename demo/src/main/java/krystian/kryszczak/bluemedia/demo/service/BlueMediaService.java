package krystian.kryszczak.bluemedia.demo.service;

import io.reactivex.rxjava3.core.Maybe;
import jakarta.inject.Singleton;
import krystian.kryszczak.bm.sdk.BlueMediaClient;
import krystian.kryszczak.bm.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.bluemedia.demo.configuration.BlueMediaConfiguration;
import org.jetbrains.annotations.NotNull;

@Singleton
public final class BlueMediaService {
    private final BlueMediaClient client;
    private final String gatewayUrl;

    public BlueMediaService(BlueMediaClient client, BlueMediaConfiguration configuration) {
        this.client = client;
        this.gatewayUrl = configuration.getGatewayUrl();
    }

    public @NotNull Maybe<RegulationListResponse> getRegulations() {
        return client.getRegulationList(gatewayUrl);
    }
}
