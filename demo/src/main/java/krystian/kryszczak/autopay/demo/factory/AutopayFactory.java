package krystian.kryszczak.autopay.demo.factory;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import krystian.kryszczak.autopay.demo.configuration.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.AutopayClient;
import krystian.kryszczak.autopay.sdk.serializer.Serializer;
import krystian.kryszczak.autopay.sdk.serializer.XmlSerializer;

@Factory
public final class AutopayFactory {
    @Singleton
    AutopayClient blueMediaClient(final AutopayConfiguration autopayConfiguration) {
        final var configuration = krystian.kryszczak.autopay.sdk.AutopayConfiguration.builder()
            .setServiceId(autopayConfiguration.getServiceId())
            .setSharedKey(autopayConfiguration.getSharedKey())
            .build();

        return new AutopayClient(configuration);
    }

    @Singleton
    Serializer serializer() {
        return new XmlSerializer();
    }
}
