package krystian.kryszczak.bm.sdk.itn.validator;

import krystian.kryszczak.bm.sdk.common.Pattern;
import org.jetbrains.annotations.NotNull;

public final class XmlItnValidator implements ItnValidator {
    @Override
    public boolean validate(@NotNull String decodedItn)  {
        return decodedItn.contains(Pattern.PATTERN_XML);
    }
}
