package krystian.kryszczak.bluemedia.demo.controller;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller
public final class BlueMediaItnController {
    @Post("${bluemedia.itn-endpoint}")
    void confirm(@Body String itn) {
        //
    }
}
