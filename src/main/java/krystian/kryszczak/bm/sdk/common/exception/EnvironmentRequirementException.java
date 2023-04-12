package krystian.kryszczak.bm.sdk.common.exception;

public final class EnvironmentRequirementException extends RuntimeException {
    public EnvironmentRequirementException() {
        super("Required at least JVM version 17, current version \"" + System.getProperty("java.vm.version", "unknown") + "\"");
    }
}
