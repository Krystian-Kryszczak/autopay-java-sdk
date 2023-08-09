package krystian.kryszczak.bluemedia.demo.service;

import io.reactivex.rxjava3.core.Maybe;
import jakarta.inject.Singleton;
import krystian.kryszczak.bm.sdk.BlueMediaClient;
import krystian.kryszczak.bm.sdk.confirmation.Confirmation;
import krystian.kryszczak.bm.sdk.hash.Hashable;
import krystian.kryszczak.bm.sdk.itn.Itn;
import krystian.kryszczak.bm.sdk.itn.response.ItnResponse;
import krystian.kryszczak.bm.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.bm.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.bluemedia.demo.configuration.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.transaction.Transaction;
import krystian.kryszczak.bm.sdk.transaction.TransactionBackground;
import krystian.kryszczak.bm.sdk.transaction.TransactionContinue;
import krystian.kryszczak.bm.sdk.transaction.TransactionInit;
import krystian.kryszczak.bm.sdk.transaction.request.TransactionBackgroundRequest;
import krystian.kryszczak.bm.sdk.transaction.request.TransactionContinueRequest;
import krystian.kryszczak.bm.sdk.transaction.request.TransactionInitRequest;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@Singleton
public final class BlueMediaService {
    private final BlueMediaClient client;
    private final String gatewayUrl;

    public BlueMediaService(BlueMediaClient client, BlueMediaConfiguration configuration) {
        this.client = client;
        this.gatewayUrl = configuration.getGatewayUrl();
    }

    public @NotNull String getTransactionRedirect(TransactionContinue transaction) {
        return client.getTransactionRedirect(
            TransactionContinueRequest.builder()
                .setGatewayUrl(gatewayUrl)
                .setTransaction(transaction)
                .build()
        );
    }

    public @NotNull Maybe<@NotNull Transaction> doTransactionInit(TransactionInit transaction) {
        return client.doTransactionInit(
            TransactionInitRequest.builder()
                .setGatewayUrl(gatewayUrl)
                .setTransaction(transaction)
                .build()
        );
    }

    public @NotNull Maybe<@NotNull Transaction> doTransactionBackground(TransactionBackground transaction) {
        return client.doTransactionBackground(
            TransactionBackgroundRequest.builder()
                .setGatewayUrl(gatewayUrl)
                .setTransaction(transaction)
                .build()
        );
    }

    public @NotNull Maybe<ItnResponse> doItnInResponse(@NotNull String transactions, @NotNull Function<Itn, Boolean> onSuccessful) {
        return client.doItnIn(transactions)
            .onErrorComplete()
            .filter(client::checkHash)
            .filter(Itn::isPaymentStatusSuccess)
            .flatMap(itn -> client.doItnInResponse(itn, onSuccessful.apply(itn)));
    }

    public @NotNull Maybe<PaywayListResponse> getPaywayList() {
        return client.getPaywayList(gatewayUrl);
    }

    public @NotNull Maybe<RegulationListResponse> getRegulationList() {
        return client.getRegulationList(gatewayUrl);
    }

    public boolean checkHash(final @NotNull Hashable hashable) {
        return client.checkHash(hashable);
    }

    public boolean doConfirmationCheck(final @NotNull Confirmation confirmation) {
        return client.doConfirmationCheck(confirmation);
    }
}
