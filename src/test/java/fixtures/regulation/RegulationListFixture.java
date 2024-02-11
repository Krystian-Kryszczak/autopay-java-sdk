package fixtures.regulation;

import org.jetbrains.annotations.NotNull;

import static fixtures.Fixtures.readFixtureFile;

public final class RegulationListFixture {
    public static @NotNull String getRegulationListResponse() {
        return readFixtureFile("regulation-list/RegulationList.xml");
    }
}
