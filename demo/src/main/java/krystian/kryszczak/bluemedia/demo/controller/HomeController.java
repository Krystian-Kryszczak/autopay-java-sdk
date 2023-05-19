package krystian.kryszczak.bluemedia.demo.controller;

import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;

import java.util.Collections;
import java.util.Map;

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller
public final class HomeController {

    @View("home")
    @Get
    public Map<String, Object> index() {
        return Collections.emptyMap();
    }
}
