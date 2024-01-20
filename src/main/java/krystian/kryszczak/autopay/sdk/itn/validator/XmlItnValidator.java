package krystian.kryszczak.autopay.sdk.itn.validator;

import krystian.kryszczak.autopay.sdk.common.AutopayPattern;
import org.jetbrains.annotations.NotNull;

public final class XmlItnValidator implements ItnValidator {
    @Override
    public boolean validate(@NotNull String decodedItn)  {
        return decodedItn.contains(AutopayPattern.PATTERN_XML);
    }
}
