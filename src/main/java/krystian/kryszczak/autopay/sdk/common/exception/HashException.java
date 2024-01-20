package krystian.kryszczak.autopay.sdk.common.exception;

public sealed class HashException extends RuntimeException permits HashNotReturnedFromServerException {
    public HashException() {
        super("Received wrong Hash!");
    }

    protected HashException(String message) {
        super(message);
    }
}
