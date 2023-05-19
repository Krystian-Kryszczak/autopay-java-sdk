package krystian.kryszczak.bluemedia.demo.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.rxjava3.core.Maybe;
import krystian.kryszczak.bluemedia.demo.service.BlueMediaService;
import krystian.kryszczak.bm.sdk.regulation.response.RegulationListResponse;
import lombok.RequiredArgsConstructor;

@Controller("controller")
@RequiredArgsConstructor
public final class BlueMediaController {
    private final BlueMediaService blueMediaService;

    @Get("regulation-list")
    @Secured(SecurityRule.IS_ANONYMOUS)
    Maybe<RegulationListResponse> getRegulationList() {
        return blueMediaService.getRegulations();
    }
}
