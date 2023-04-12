package krystian.kryszczak.bm.sdk.hash;

import org.jetbrains.annotations.NotNull;

public enum HashType {
    MD5("md5"),
    SHA1("sha1"),
    SHA256("sha256"),
    SHA512("sha512");

    final @NotNull String name;
    HashType(@NotNull String name) {
        this.name = name;
    }
    @Override
    public @NotNull String toString() {
        return name;
    }

    public static final String HASH_SEPARATOR = "|";
}
