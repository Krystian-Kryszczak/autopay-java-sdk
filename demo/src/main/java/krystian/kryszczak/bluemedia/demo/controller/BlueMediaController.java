package krystian.kryszczak.bluemedia.demo.controller;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.rxjava3.core.Maybe;
import krystian.kryszczak.bluemedia.demo.service.BlueMediaService;
import krystian.kryszczak.bm.sdk.confirmation.Confirmation;
import krystian.kryszczak.bm.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.bm.sdk.regulation.response.RegulationListResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Controller
@Secured(SecurityRule.IS_ANONYMOUS)
@RequiredArgsConstructor
public final class BlueMediaController {
    private final BlueMediaService blueMediaService;

    @Get("payway-list")
    @NotNull Maybe<PaywayListResponse> paywayList() {
        return blueMediaService.getPaywayList();
    }

    @Get("regulation-list")
    @NotNull Maybe<RegulationListResponse> regulationList() {
        return blueMediaService.getRegulationList();
    }

    @Post("check-hash")
    boolean checkHash(@Body @NotNull PaywayListResponse hashable) {
        return blueMediaService.checkHash(hashable);
    }

    @Post("do-confirmation-check")
    boolean checkHash(@Body @NotNull Confirmation confirmation) {
        return blueMediaService.doConfirmationCheck(confirmation);
    }
}
