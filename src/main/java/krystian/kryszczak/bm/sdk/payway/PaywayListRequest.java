package krystian.kryszczak.bm.sdk.payway;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.hash.HashGenerator;
import krystian.kryszczak.bm.sdk.hash.Hashable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class PaywayListRequest implements Hashable {
    private final @NotNull String gatewayUrl;
    private final @NotNull String hash;

    public static @NotNull PaywayListRequest create(@NotNull String gatewayUrl, @NotNull BlueMediaConfiguration configuration) {
        return new PaywayListRequest(
            gatewayUrl,
            HashGenerator.instance.generateHash(
                new Object[]{
                    gatewayUrl,

                },
                configuration
            )
        );
    }

    @Override
    public @NotNull Object[] toArrayWithoutHash() {
        return new Object[] {
            gatewayUrl
        };
    }
}
