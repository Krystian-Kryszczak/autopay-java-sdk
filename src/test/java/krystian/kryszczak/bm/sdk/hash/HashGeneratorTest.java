package krystian.kryszczak.bm.sdk.hash;

import krystian.kryszczak.bm.sdk.BlueMediaClient;
import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import org.junit.jupiter.api.Test;

public class HashGeneratorTest {

    private static final BlueMediaClient blueMediaClient = new BlueMediaClient(
        new BlueMediaConfiguration(
            56467,
            "",
            HashType.SHA256,
            "|"
        )
    );

    @Test
    void temp() {
//        HashGenerator.instance.generateHash(
//            new Object[]{},
//        )
        // TODO
    }
}
