package krystian.kryszczak.autopay.configuration;

import io.micronaut.context.ApplicationContext;
import krystian.kryszczak.autopay.demo.configuration.AutopayConfiguration;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class AutopayConfigurationTest {
    @Test
    void configurationTest() {
        final Map<String, Object> items = new HashMap<>();
        items.put("autopay.service-id", 55555);
        items.put("autopay.shared-key", "test-shared-key");
        items.put("autopay.gateway-url", "https://testpay.autopay.eu");
        items.put("autopay.itnEndpoint", "itn");

        ApplicationContext ctx = ApplicationContext.run(items);
        AutopayConfiguration autopayConfiguration = ctx.getBean(AutopayConfiguration.class);
        final Integer serviceId = autopayConfiguration.getServiceId();
        final String sharedKey = autopayConfiguration.getSharedKey();
        final String gatewayUrl = autopayConfiguration.getGatewayUrl();
        final String itnEndpoint = autopayConfiguration.getItnEndpoint();

        assertNotNull(serviceId);
        assertNotNull(sharedKey);
        assertNotNull(gatewayUrl);
        assertNotNull(itnEndpoint);

        assertEquals(55555, serviceId);
        assertEquals("test-shared-key", sharedKey);
        assertEquals("https://testpay.autopay.eu", gatewayUrl);
        assertEquals("itn", itnEndpoint);

        ctx.close();
    }
}
