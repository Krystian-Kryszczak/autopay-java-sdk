package krystian.kryszczak.bm.sdk.transaction;

import krystian.kryszczak.bm.sdk.common.Routes;
import krystian.kryszczak.bm.sdk.common.util.Translations;
import krystian.kryszczak.bm.sdk.transaction.dto.TransactionDto;
import org.jetbrains.annotations.NotNull;

public final class View {
    private static final @NotNull String separator = System.getProperty("line.separator", "\n");

    public static @NotNull String createRedirectHtml(@NotNull TransactionDto transactionDto) {

        final var translationPhrases = Translations.getTranslationPhrasesMap(
            transactionDto.getHtmlFormLanguage()
        );

        final var result = new StringBuilder()
            .append("<p>").append(translationPhrases.get(Translations.REDIRECT)).append("</p>").append(separator)
            .append(
                String.format(
                    "<form action=\"%s\" method=\"post\" id=\"BlueMediaPaymentForm\" name=\"BlueMediaPaymentForm\">",
                    transactionDto.getGatewayUrl() + Routes.PAYMENT_ROUTE
                )
            ).append(separator);


        for (final var entry : transactionDto.fieldsMapWithCapitalizedKeysAndFormattedValues().entrySet()) {
            final var fieldName = entry.getKey();
            final var fieldValue = entry.getValue();

            if (fieldValue.isBlank())
                continue;

            result.append(
                String.format("<input type=\"hidden\" name=\"%s\" value=\"%s\" />", fieldName, fieldValue)
            ).append(separator);
        }

        result.append("<input type=\"submit\" />").append(separator)
            .append("</form>").append(separator)
            .append("<script type=\"text/javascript\">document.BlueMediaPaymentForm.submit();</script>")
            .append("<noscript><p>")
                .append(translationPhrases.get(Translations.JAVASCRIPT_DISABLED))
                .append("<br>")
                .append(translationPhrases.get(Translations.JAVASCRIPT_REQUIRED))
            .append("</p></noscript>").append(separator);

        return result.toString();
    }
}
