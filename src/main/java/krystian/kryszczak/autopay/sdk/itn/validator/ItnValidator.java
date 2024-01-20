package krystian.kryszczak.autopay.sdk.itn.validator;

import org.jetbrains.annotations.NotNull;

public sealed interface ItnValidator permits XmlItnValidator {
    boolean validate(@NotNull String decodedItn);
}
