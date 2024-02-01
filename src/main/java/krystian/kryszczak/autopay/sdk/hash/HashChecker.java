package krystian.kryszczak.autopay.sdk.hash;

import krystian.kryszczak.autopay.sdk.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.common.ServiceHttpRequestBody;
import krystian.kryszczak.autopay.sdk.common.exception.HashNotReturnedFromServerException;
import krystian.kryszczak.autopay.sdk.transaction.Transaction;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class HashChecker {
    /**
     * Checks if Hash is correct.
     * @throws HashNotReturnedFromServerException when hash is not present.
     * @return true if hash is valid and false when hash is incorrect.
     */
    public static boolean checkHash(@NotNull Hashable hashable, @NotNull AutopayConfiguration configuration) throws HashNotReturnedFromServerException {
        if (!hashable.isHashPresent()) {
            throw new HashNotReturnedFromServerException();
        }

        System.out.println("Class name: " + hashable.getClass().getSimpleName());
        System.out.println("Values:");
        Arrays.stream(hashable.toArray()).forEach(System.out::println);
        if (hashable instanceof Transaction transaction) {
            System.out.println("Map: ");
            transaction.toCapitalizedMap().forEach((k, v) -> System.out.println(k + " » " + v));
        }
        final String generatedHash = HashGenerator.generateHash(
            hashable.toArray(),
            configuration
        );

        return generatedHash.equals(hashable.getHash());
    }
}
