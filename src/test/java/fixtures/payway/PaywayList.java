package fixtures.payway;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

import static fixtures.Fixtures.FIXTURES_FOLDER_PATH;

public final class PaywayList {
    @SneakyThrows
    public static String getPaywayListResponse() {
        return Files.readString(Path.of(FIXTURES_FOLDER_PATH + "payway-list/PaywayListResponse.xml"));
    }
}
