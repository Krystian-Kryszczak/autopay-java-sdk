package krystian.kryszczak.bluemedia.demo.service;

import io.reactivex.rxjava3.core.Maybe;
import jakarta.inject.Singleton;
import krystian.kryszczak.bm.sdk.BlueMediaClient;
import krystian.kryszczak.bm.sdk.payway.PaywayList;
import krystian.kryszczak.bm.sdk.payway.response.PaywayListResponse;
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

    public @NotNull Maybe<PaywayListResponse> getPaywayList() {
        return client.getPaywayList(gatewayUrl);
    }

    public @NotNull Maybe<RegulationListResponse> getRegulationList() {
        return client.getRegulationList(gatewayUrl);
    }
}
