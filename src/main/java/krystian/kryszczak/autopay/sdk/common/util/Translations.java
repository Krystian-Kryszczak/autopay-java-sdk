package krystian.kryszczak.autopay.sdk.common.util;

import krystian.kryszczak.autopay.sdk.common.exception.TranslationException;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

public final class Translations {
    public static final String REDIRECT = "form.paywall.redirect";
    public static final String JAVASCRIPT_DISABLED = "form.paywall.javascript_disabled";
    public static final String JAVASCRIPT_REQUIRED = "form.paywall.javascript_required";

    public static final String[] REQUIRED_TRANSLATIONS = new String[] {
        REDIRECT,
        JAVASCRIPT_DISABLED,
        JAVASCRIPT_REQUIRED
    };

    private static final Map<Language, Map<String, String>> translations = Map.of(
        Language.pl, Map.of(
            REDIRECT, "Trwa przekierowanie do Bramki Płatniczej Autopay...",
            JAVASCRIPT_DISABLED, "Masz wyłączoną obsługę JavaScript",
            JAVASCRIPT_REQUIRED, "Aby przejść do Bramki Płatniczej Autopay, musisz włączyć obsługę JavaScript w przeglądarce."
        ),
        Language.en, Map.of(
            REDIRECT, "You are being redirected to the Autopay Payment Gateway...",
            JAVASCRIPT_DISABLED, "You have disabled JavaScript",
            JAVASCRIPT_REQUIRED, "To access the Autopay Payment Gateway, you need to enable JavaScript in your browser."
        ),
        Language.de, Map.of(
            REDIRECT, "Sie werden zum Autopay Payment Gateway weitergeleitet...",
            JAVASCRIPT_DISABLED, "Sie haben JavaScript deaktiviert",
            JAVASCRIPT_REQUIRED, "Damit du auf die zahlungspflichtige Seite Autopay zugreifen kannst, aktiviere das JavaScript."
        )
    );

    public enum Language {
        pl, en, de;

        public static boolean exists(@NotNull String languageName) {
            for (final Language language : Language.values()) {
                if (language.name().equals(languageName)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static @NotNull Map<String, String> getTranslations(@NotNull Language language) {
        return getTranslations(language, Translations.REQUIRED_TRANSLATIONS);
    }

    public static @NotNull Map<String, String> getTranslations(@NotNull Language language, final @NotNull String[] requiredTranslations) {
        final Map<String, String> translationPhrases = translations.get(language);

        final String[] missingTranslationKeys = Arrays.stream(requiredTranslations)
            .filter(it -> !translationPhrases.containsKey(it))
            .toArray(String[]::new);

        if (missingTranslationKeys.length > 0) {
            throw new TranslationException(missingTranslationKeys);
        }

        return translationPhrases;
    }
}
