package krystian.kryszczak.bm.sdk.common.exception;

public final class HashNotReturnedFromServerException extends HashException {
    public HashNotReturnedFromServerException() {
        super("No Hash received from server! Check your serviceID.");
    }
}
