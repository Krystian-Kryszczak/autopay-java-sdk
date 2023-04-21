package krystian.kryszczak.bm.sdk.gateway;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.hash.HashGenerator;
import krystian.kryszczak.bm.sdk.util.RandomUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.MODULE)
public final class GatewayRequest {
    private final int ServiceID;
    private final @NotNull String MessageID;
    private final @NotNull String Currencies;
    private final @NotNull String Hash;

    public static GatewayRequest create(@NotNull BlueMediaConfiguration configuration, @NotNull String... currencies) throws NoSuchAlgorithmException {
        final var serviceId = configuration.getServiceId();
        final var randomMessageId = RandomUtils.randomMessageId();
        final String currenciesJoined = Arrays.stream(currencies)
                .map(String::toUpperCase)
                .collect(Collectors.joining(","));

        return new GatewayRequest(
            serviceId,
            randomMessageId,
            currenciesJoined,
            HashGenerator.instance.generateHash(
                new Object[] {
                    serviceId,
                    randomMessageId,
                    currenciesJoined
                },
                configuration
            )
        );
    }

    public static GatewayRequest create(@NotNull BlueMediaConfiguration configuration) throws NoSuchAlgorithmException {
        return create(configuration, "PLN");
    }
}
