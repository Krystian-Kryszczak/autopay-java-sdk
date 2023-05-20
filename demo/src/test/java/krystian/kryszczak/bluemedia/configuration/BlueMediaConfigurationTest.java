package krystian.kryszczak.bluemedia.configuration;

import io.micronaut.context.ApplicationContext;
import krystian.kryszczak.bluemedia.demo.configuration.BlueMediaConfiguration;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public final class BlueMediaConfigurationTest {
    @Test
    void configurationTest() {
        final Map<String, Object> items = new HashMap<>();
        items.put("bluemedia.service-id", 55555);
        items.put("bluemedia.shared-key", "test-shared-key");
        items.put("bluemedia.gateway-url", "https://pay-accept.bm.pl");
        items.put("bluemedia.itnEndpoint", "itn");

        ApplicationContext ctx = ApplicationContext.run(items);
        BlueMediaConfiguration blueMediaConfiguration = ctx.getBean(BlueMediaConfiguration.class);
        final Integer serviceId = blueMediaConfiguration.getServiceId();
        final String sharedKey = blueMediaConfiguration.getSharedKey();
        final String gatewayUrl = blueMediaConfiguration.getGatewayUrl();
        final String itnEndpoint = blueMediaConfiguration.getItnEndpoint();

        assertNotNull(serviceId);
        assertNotNull(sharedKey);
        assertNotNull(gatewayUrl);
        assertNotNull(itnEndpoint);

        assertEquals(55555, serviceId);
        assertEquals("test-shared-key", sharedKey);
        assertEquals("https://pay-accept.bm.pl", gatewayUrl);
        assertEquals("itn", itnEndpoint);

        ctx.close();
    }
}
