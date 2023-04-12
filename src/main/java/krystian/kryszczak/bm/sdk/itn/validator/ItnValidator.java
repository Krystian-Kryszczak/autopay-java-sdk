package krystian.kryszczak.bm.sdk.itn.validator;

import org.jetbrains.annotations.NotNull;

public sealed interface ItnValidator permits XmlItnValidator {
    public boolean validate(@NotNull String decodedItn);
}
