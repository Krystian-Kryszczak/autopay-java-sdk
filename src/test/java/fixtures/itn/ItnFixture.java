package fixtures.itn;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static fixtures.Fixtures.readFixtureFile;

public final class ItnFixture {
    public static String getItnInRequestBase64Encoded() {
        return Base64.getEncoder().encodeToString(getItnInRequest().getBytes());
    }

    public static @NotNull String getItnInRequest() {
        return readFixtureFile("itn/ItnInRequest.xml");
    }

    @SneakyThrows
    public static @NotNull Map<String, String> getTransactionDataFromXml() {
        final Map<String, String> dst = new HashMap<>();
        new XmlMapper().readTree(getItnInRequest()).get("transactions").get("transaction").fields()
            .forEachRemaining(entry -> dst.put(entry.getKey(), entry.getValue().asText()));
        return dst;
    }

    public static String getItnInWrongHashRequest() {
        return Base64.getEncoder().encodeToString(readFixtureFile("itn/ItnInWrongHashRequest.xml").getBytes());
    }

    public static @NotNull String getItnResponse() {
        return readFixtureFile("itn/ItnResponse.xml");
    }
}
