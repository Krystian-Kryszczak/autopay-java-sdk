package krystian.kryszczak.autopay.demo.controller;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.reactivex.rxjava3.core.Maybe;
import krystian.kryszczak.autopay.demo.service.AutopayService;
import krystian.kryszczak.autopay.sdk.itn.Itn;
import krystian.kryszczak.autopay.sdk.itn.response.ItnResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequiredArgsConstructor
public final class AutopayItnController {
    private static final Logger logger = LoggerFactory.getLogger(AutopayItnController.class);
    private final AutopayService autopayService;

    @Post("${bluemedia.itn-endpoint}")
    Maybe<String> confirm(String itn) {
        return autopayService.doItnInResponse(itn, this::onSuccess)
            .map(ItnResponse::toXml);
    }

    boolean onSuccess(Itn itn) {
        logger.info("New itn: " + itn);
        return true;
    }
}
