package krystian.kryszczak.bm.sdk;

import org.junit.jupiter.api.Test;

public final class BlueMediaClientTest {

    @Test
    public void test() {
        final BlueMediaConfiguration configuration = BlueMediaTestConfiguration.get();
        final BlueMediaClient client = new BlueMediaClient(configuration);

        client.getRegulationList2("https://pay.bm.pl");
    }
}
