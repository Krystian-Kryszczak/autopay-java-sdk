package fixtures;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;

public final class Fixtures {
    private static final String FIXTURES_FOLDER_PATH = "src/test/resources/fixtures/";

    @SneakyThrows
    public static @NotNull String readFixtureFile(final @NotNull String path) {
        return Files.readString(Path.of(FIXTURES_FOLDER_PATH + path));
    }
}
