package krystian.kryszczak.bluemedia.demo.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("bluemedia")
public final class BlueMediaConfiguration {
    private Integer serviceId;
    private String sharedKey;
    private String gatewayUrl;
}
