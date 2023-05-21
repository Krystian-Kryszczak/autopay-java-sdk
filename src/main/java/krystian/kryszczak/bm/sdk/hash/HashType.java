package krystian.kryszczak.bm.sdk.hash;

public enum HashType {
    MD5,
    SHA1,
    SHA256,
    SHA512;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
