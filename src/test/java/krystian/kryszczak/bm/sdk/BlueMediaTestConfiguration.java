package krystian.kryszczak.bm.sdk;

public final class BlueMediaTestConfiguration {
    public static BlueMediaConfiguration get() {
        return BlueMediaConfiguration.builder()
            .setServiceId(Integer.parseInt(System.getenv("BM_SERVICE_ID")))
            .setSharedKey(System.getenv("BM_SHARED_KEY"))
            .build();
    }
}
