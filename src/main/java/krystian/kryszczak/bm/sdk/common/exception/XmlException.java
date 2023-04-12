package krystian.kryszczak.bm.sdk.common.exception;

import org.jetbrains.annotations.NotNull;

public final class XmlException extends RuntimeException {
    public XmlException(@NotNull String xml) {
        super("XML error: " + xml);
    }
}
