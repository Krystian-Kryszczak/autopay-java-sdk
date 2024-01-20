package krystian.kryszczak.autopay.sdk;

import krystian.kryszczak.autopay.sdk.hash.HashType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ApiStatus.AvailableSince("1.0")
public final class AutopayConfiguration {
    final int serviceId;
    final @NotNull String sharedKey;
    final @NotNull HashType hashAlgorithm;
    final @NotNull String hashSeparator;

    public static @NotNull AutopayConfiguration fromEnvironmentVariables() {
        final int bmServiceId = Integer.parseInt(System.getenv("BM_SERVICE_ID"));
        final String sharedKey = Objects.requireNonNull(System.getenv("BM_SHARED_KEY"), "BM_SHARED_KEY is not defined or invalid.");

        final HashType hashAlgorithm = Arrays.stream(HashType.values())
            .filter(it -> it.name().equals(System.getenv("BM_SHARED_KEY")))
            .findFirst().orElse(HashType.SHA256);
        final String hashSeparator = Optional.ofNullable(System.getenv("BM_HASH_SEPARATOR")).orElse("|");

        return AutopayConfiguration.builder()
            .setServiceId(bmServiceId)
            .setSharedKey(sharedKey)
            .setHashAlgorithm(hashAlgorithm)
            .setHashSeparator(hashSeparator)
            .build();
    }

    public static @NotNull Builder.Required builder() {
        return new Builder.Required();
    }

    public final static class Builder {
        @NoArgsConstructor(access = AccessLevel.PRIVATE)
        public final static class Required {
            public @NotNull WithServiceId setServiceId(int serviceId) {
                return new WithServiceId(serviceId);
            }
            public @NotNull WithSharedKey setSharedKey(@NotNull String sharedKey) {
                return new WithSharedKey(sharedKey);
            }
        }

        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        public final static class WithServiceId {
            private int serviceId;

            public @NotNull AutopayConfiguration.Builder.WithServiceId setServiceId(int serviceId) {
                this.serviceId = serviceId;
                return this;
            }
            public @NotNull Completer setSharedKey(@NotNull String sharedKey) {
                return new Completer(this.serviceId, sharedKey);
            }
        }

        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        public final static class WithSharedKey {
            private @NotNull String sharedKey;

            public @NotNull AutopayConfiguration.Builder.Completer setServiceId(int serviceId) {
                return new Completer(serviceId, this.sharedKey);
            }
            public @NotNull AutopayConfiguration.Builder.WithSharedKey setSharedKey(@NotNull String sharedKey) {
                this.sharedKey = sharedKey;
                return this;
            }
        }

        public final static class Completer {
            private int serviceId;
            private String sharedKey;
            private @NotNull HashType hashAlgorithm = HashType.SHA256;
            private @NotNull String hashSeparator = "|";

            private Completer(int serviceId, @NotNull String sharedKey) {
                this.serviceId = serviceId;
                this.sharedKey = sharedKey;
            }

            public @NotNull Completer setServiceId(int serviceId) {
                this.serviceId = serviceId;
                return this;
            }
            public @NotNull Completer setSharedKey(@NotNull String sharedKey) {
                this.sharedKey = sharedKey;
                return this;
            }
            public @NotNull Completer setHashAlgorithm(@NotNull HashType hashAlgorithm) {
                this.hashAlgorithm = hashAlgorithm;
                return this;
            }
            public @NotNull Completer setHashSeparator(@NotNull String hashSeparator) {
                this.hashSeparator = hashSeparator;
                return this;
            }

            public @NotNull AutopayConfiguration build() {
                return new AutopayConfiguration(serviceId, sharedKey, hashAlgorithm, hashSeparator);
            }
        }
    }
}
