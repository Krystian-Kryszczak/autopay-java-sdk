package krystian.kryszczak.bm.sdk.common.exception;

public final class HashNotReturnedFromServerException extends RuntimeException {
    public HashNotReturnedFromServerException() {
        super("No krystian.kryszczak.bm.sdk.hash received from server! Check your serviceID.");
    }
}
