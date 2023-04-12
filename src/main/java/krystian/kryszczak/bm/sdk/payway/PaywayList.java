package krystian.kryszczak.bm.sdk.payway;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public record PaywayList(
    @NotNull Gateway[] gateways
) implements Serializable {} // PaywayListResponse TODO
