package krystian.kryszczak.bm.sdk.payway;

import org.jetbrains.annotations.NotNull;

public record Gateway(
    int gatewayId,
    @NotNull String gatewayName,
    @NotNull String gatewayType,
    @NotNull String bankName,
    @NotNull String iconUrl,
    @NotNull String statusDate
) {}
