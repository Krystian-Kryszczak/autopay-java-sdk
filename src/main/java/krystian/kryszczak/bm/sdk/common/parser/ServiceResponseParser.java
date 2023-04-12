package krystian.kryszczak.bm.sdk.common.parser;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.http.Response;
import org.jetbrains.annotations.NotNull;

public final class ServiceResponseParser extends ResponseParser {
    public ServiceResponseParser(@NotNull Response response, @NotNull BlueMediaConfiguration credentials) {
        super(response, credentials);
    }
}
