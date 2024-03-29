package krystian.kryszczak.autopay.sdk.common.exception;

import org.jetbrains.annotations.NotNull;

public final class TranslationException extends RuntimeException {
    public TranslationException() {
        super("Missing required translation some key(s).");
    }

    public TranslationException(@NotNull String[] keys) {
        super("Missing required translation key(s): " + String.join(", ", keys));
    }
}
