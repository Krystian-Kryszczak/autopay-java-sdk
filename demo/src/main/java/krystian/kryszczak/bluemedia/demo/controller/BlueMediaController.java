package krystian.kryszczak.bluemedia.demo.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.rxjava3.core.Maybe;
import krystian.kryszczak.bluemedia.demo.service.BlueMediaService;
import krystian.kryszczak.bm.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.bm.sdk.regulation.response.RegulationListResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Controller
@RequiredArgsConstructor
public final class BlueMediaController {
    private final BlueMediaService blueMediaService;

    @Get("payway-list")
    @Secured(SecurityRule.IS_ANONYMOUS)
    @NotNull Maybe<PaywayListResponse> paywayList() {
        return blueMediaService.getPaywayList();
    }

    @Get("regulation-list")
    @Secured(SecurityRule.IS_ANONYMOUS)
    @NotNull Maybe<RegulationListResponse> regulationList() {
        return blueMediaService.getRegulationList();
    }
}
