package krystian.kryszczak.bm.sdk.common.util;

import krystian.kryszczak.bm.sdk.BaseTestCase;
import krystian.kryszczak.bm.sdk.common.exception.TranslationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public final class TranslationsTest extends BaseTestCase {
    private static final String TRANSLATION = "Trwa przekierowanie do Bramki Płatniczej Blue Media...";
    private static final String TRANSLATION_EXCEPTION = "Missing required translation key(s): form.test.key";

    @Test
    public void testGetTranslationReturnsTranslation() {
        final var translation = Translations.getTranslations(Translations.Language.pl);

        assertFalse(translation.isEmpty());
        assertSame(translation.get("form.paywall.redirect"), TRANSLATION);
    }

    @Test
    public void testGetTranslationThrowsExceptionOnMissingTranslationKey() {
        final var required_translation_keys = new String[] {
            "form.test.key"
        };

        assertThrowsExactly(
            TranslationException.class,
            () -> Translations.getTranslations(Translations.Language.en, required_translation_keys),
            TRANSLATION_EXCEPTION
        );
    }
}
