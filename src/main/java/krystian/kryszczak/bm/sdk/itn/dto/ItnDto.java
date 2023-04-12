package krystian.kryszczak.bm.sdk.itn.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import krystian.kryszczak.bm.sdk.common.dto.AbstractDto;
import krystian.kryszczak.bm.sdk.itn.Itn;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
public final class ItnDto extends AbstractDto implements Serializable {
    private static final XmlMapper xmlMapper = new XmlMapper();

    private final @NotNull Itn itn;
    public ItnDto(@NotNull String gatewayUrl, @NotNull String request, @NotNull Itn itn) {
        super(gatewayUrl, request);
        this.itn = itn;
    }

    public static @NotNull ItnDto buildFormXml(@NotNull String decodedItnData) throws JsonProcessingException {
        final ItnDto itnDto = xmlMapper.readValue(decodedItnData, ItnDto.class);
        // TODO
        return itnDto;
    }

    @Override
    public Serializable getRequestData() {
        return getItn();
    }
}
