package fixtures.payway;

import org.jetbrains.annotations.NotNull;

import static fixtures.Fixtures.readFixtureFile;

public final class PaywayListFixture {
    public static @NotNull String getPaywayListResponse() {
        return readFixtureFile("payway-list/PaywayListResponse.xml");
    }
}
