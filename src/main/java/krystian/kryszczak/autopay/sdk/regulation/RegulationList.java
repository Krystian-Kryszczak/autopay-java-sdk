package krystian.kryszczak.autopay.sdk.regulation;

import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.common.ServiceHttpRequestBody;
import krystian.kryszczak.autopay.sdk.hash.HashGenerator;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegulationList extends ServiceHttpRequestBody implements Serializable {
    protected RegulationList(int serviceID, @NotNull String messageID, @Nullable String hash) {
        super(serviceID, messageID, hash);
    }

    public static @NotNull RegulationList create(int serviceID, @NotNull String messageID, @NotNull AutopayConfiguration configuration) {
        final String hash = HashGenerator.generateHash(
            new Object[] {
                serviceID,
                messageID
            },
            configuration
        );

        return new RegulationList(
            serviceID,
            messageID,
            hash
        );
    }
}
