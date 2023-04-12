package krystian.kryszczak.bm.sdk.itn.response;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.hash.HashGenerator;
import krystian.kryszczak.bm.sdk.itn.Itn;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public final class ItnResponse {
    private static final XmlMapper xmlMapper = new XmlMapper();

    private static final String STATUS_CONFIRMED = "CONFIRMED";
    private static final String STATUS_NOT_CONFIRMED = "NOTCONFIRMED";

    public static ItnResponse create(@NotNull Itn itn, boolean transactionConfirmed, @NotNull BlueMediaConfiguration configuration) {
        final var confirmation = transactionConfirmed ? STATUS_CONFIRMED : STATUS_NOT_CONFIRMED;

        final var hashData = new Object[]{
            configuration.getServiceId(),
            itn.getOrderId(),
            confirmation
        };

        final var itnResponseData = Map.of(
            "serviceID", configuration.getServiceId(),
            "transactionsConfirmations", Map.of(
                        //
                    ),
                "hash", HashGenerator.instance.generateHash(hashData, configuration)
        );

        //
    }
}
