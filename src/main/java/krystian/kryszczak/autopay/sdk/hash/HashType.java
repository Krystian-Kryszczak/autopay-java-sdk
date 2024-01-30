package krystian.kryszczak.autopay.sdk.hash;

import org.jetbrains.annotations.NotNull;

public enum HashType {
    MD5,
    SHA1,
    SHA256,
    SHA512;

    @Override
    public @NotNull String toString() {
        return this.name().toLowerCase();
    }
}
