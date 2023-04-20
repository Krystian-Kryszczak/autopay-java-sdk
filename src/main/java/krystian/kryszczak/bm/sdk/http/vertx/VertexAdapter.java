package krystian.kryszczak.bm.sdk.http.vertx;

import io.vertx.rxjava3.ext.web.multipart.MultipartForm;
import io.vertx.rxjava3.uritemplate.UriTemplate;
import krystian.kryszczak.bm.sdk.http.HttpRequestBody;
import org.jetbrains.annotations.NotNull;

import java.net.URI;

public final class VertexAdapter {
    public static UriTemplate adapt(@NotNull URI uri) {
        return UriTemplate.of(uri.toString());
    }

    public static MultipartForm asMultipartForm(@NotNull HttpRequestBody body) {
        final MultipartForm multipartForm = MultipartForm.create()
                .setCharset("UTF-8");
        for (final var entry : body.fieldsMapWithCapitalizedKeysAndFormattedValues().entrySet()) {
            multipartForm.attribute(entry.getKey(), entry.getValue());
        }
        return multipartForm;
    }
}
