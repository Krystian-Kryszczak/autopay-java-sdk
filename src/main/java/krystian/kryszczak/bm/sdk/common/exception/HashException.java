package krystian.kryszczak.bm.sdk.common.exception;

public final class HashException extends RuntimeException {
    public HashException() {
        super("Received wrong Hash!");
    }
}
