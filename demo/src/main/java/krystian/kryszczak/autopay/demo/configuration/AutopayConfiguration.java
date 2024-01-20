package krystian.kryszczak.autopay.demo.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

@Data
@ConfigurationProperties("autopay")
public final class AutopayConfiguration {
    private Integer serviceId;
    private String sharedKey;
    private String gatewayUrl;
    private String itnEndpoint;
}
