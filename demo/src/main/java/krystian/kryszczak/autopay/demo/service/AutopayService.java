package krystian.kryszczak.autopay.demo.service;

import io.reactivex.rxjava3.core.Maybe;
import jakarta.inject.Singleton;
import krystian.kryszczak.autopay.demo.configuration.AutopayConfiguration;
import krystian.kryszczak.autopay.sdk.AutopayClient;
import krystian.kryszczak.autopay.sdk.confirmation.Confirmation;
import krystian.kryszczak.autopay.sdk.hash.Hashable;
import krystian.kryszczak.autopay.sdk.itn.Itn;
import krystian.kryszczak.autopay.sdk.itn.response.ItnResponse;
import krystian.kryszczak.autopay.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.autopay.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.autopay.sdk.transaction.Transaction;
import krystian.kryszczak.autopay.sdk.transaction.TransactionBackground;
import krystian.kryszczak.autopay.sdk.transaction.TransactionContinue;
import krystian.kryszczak.autopay.sdk.transaction.TransactionInit;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionBackgroundRequest;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionContinueRequest;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionInitRequest;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

@Singleton
public final class AutopayService {
    private final AutopayClient client;
    private final String gatewayUrl;

    public AutopayService(AutopayClient client, AutopayConfiguration configuration) {
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
