package krystian.kryszczak.bluemedia.demo.factory;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import krystian.kryszczak.bluemedia.demo.configuration.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.BlueMediaClient;
import krystian.kryszczak.bm.sdk.serializer.Serializer;
import krystian.kryszczak.bm.sdk.serializer.XmlSerializer;

@Factory
public final class BlueMediaFactory {
    @Singleton
    BlueMediaClient blueMediaClient(final BlueMediaConfiguration blueMediaConfiguration) {
        final var configuration = krystian.kryszczak.bm.sdk.BlueMediaConfiguration.builder()
            .setServiceId(blueMediaConfiguration.getServiceId())
            .setSharedKey(blueMediaConfiguration.getSharedKey())
            .build();

        return new BlueMediaClient(configuration);
    }

    @Singleton
    Serializer serializer() {
        return new XmlSerializer();
    }
}
