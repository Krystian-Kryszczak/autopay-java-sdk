package fixtures.regulation;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

import static fixtures.Fixtures.FIXTURES_FOLDER_PATH;

public final class RegulationList {
    @SneakyThrows
    public static String getRegulationListResponse() {
        return Files.readString(Path.of(FIXTURES_FOLDER_PATH + "regulation-list/RegulationList.xml"));
    }
}
