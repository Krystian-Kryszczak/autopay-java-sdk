package krystian.kryszczak.autopay.sdk.http.vertx;

import io.vertx.ext.web.multipart.MultipartForm;
import io.vertx.uritemplate.UriTemplate;
import krystian.kryszczak.autopay.sdk.http.HttpRequestBody;
import org.jetbrains.annotations.NotNull;

import java.net.URI;

public final class VertexAdapter {
    public static UriTemplate adapt(@NotNull URI uri) {
        return UriTemplate.of(uri.toString());
    }

    public static MultipartForm adapt(@NotNull HttpRequestBody body) {
        final MultipartForm multipartForm = MultipartForm.create()
            .setCharset("UTF-8");
        for (final var entry : body.toCapitalizedMap().entrySet()) {
            multipartForm.attribute(entry.getKey(), entry.getValue());
        }
        return multipartForm;
    }
}
