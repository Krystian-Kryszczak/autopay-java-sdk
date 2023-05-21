package krystian.kryszczak.bluemedia.demo.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.reactivex.rxjava3.core.Maybe;
import krystian.kryszczak.bluemedia.demo.service.BlueMediaService;
import krystian.kryszczak.bm.sdk.itn.Itn;
import krystian.kryszczak.bm.sdk.itn.response.ItnResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequiredArgsConstructor
public final class BlueMediaItnController {
    private static final Logger logger = LoggerFactory.getLogger(BlueMediaItnController.class);
    private final BlueMediaService blueMediaService;

    @Post("${bluemedia.itn-endpoint}")
    Maybe<String> confirm(String itn) {
        return blueMediaService.doItnInResponse(itn, this::onSuccess)
            .map(ItnResponse::toXml);
    }

    boolean onSuccess(Itn itn) {
        logger.info("New itn: " + itn);
        return true;
    }
}
