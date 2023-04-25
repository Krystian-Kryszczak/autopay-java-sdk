package krystian.kryszczak.bm.sdk.common.exception;

import org.jetbrains.annotations.NotNull;

public final class XmlException extends RuntimeException {
    private XmlException(String message) {
        super(message);
    }
    private XmlException(Throwable cause) {
        super(cause);
    }

    public static XmlException xmlBodyContainsError(@NotNull String content) {
        return new XmlException(String.format("%s: %s", "Returned XML contains error: ", content));
    }

    public static XmlException xmlGeneralError(@NotNull String content) {
        return new XmlException(String.format("%s: %s", "Received error instead of XML: ", content));
    }

    public static XmlException xmlParseError(Throwable throwable) {
        return new XmlException(throwable);
    }
}
