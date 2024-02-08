package fixtures.itn;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static fixtures.Fixtures.FIXTURES_FOLDER_PATH;

public final class ItnFixture {
    @SneakyThrows
    public static String getItnInRequestBase64Encoded() {
        return Base64.getEncoder().encodeToString(getItnInRequest().getBytes());
    }

    @SneakyThrows
    public static String getItnInRequest() {
        return Files.readString(Path.of(FIXTURES_FOLDER_PATH + "itn/ItnInRequest.xml"));
    }

    @SneakyThrows
    public static @NotNull Map<String, String> getTransactionDataFromXml() {
        final Map<String, String> dst = new HashMap<>();
        new XmlMapper().readTree(getItnInRequest()).get("transactions").get("transaction").fields()
            .forEachRemaining(entry -> dst.put(entry.getKey(), entry.getValue().asText()));
        return dst;
    }

    @SneakyThrows
    public static String getItnInWrongHashRequest() {
        return Base64.getEncoder().encodeToString(
            Files.readString(Path.of(FIXTURES_FOLDER_PATH + "itn/ItnInWrongHashRequest.xml")).getBytes()
        );
    }

    @SneakyThrows
    public static String getItnResponse() {
        return Files.readString(Path.of(FIXTURES_FOLDER_PATH + "itn/ItnResponse.xml"));
    }
}
