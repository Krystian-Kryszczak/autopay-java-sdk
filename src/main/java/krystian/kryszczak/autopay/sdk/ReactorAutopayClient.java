package krystian.kryszczak.autopay.sdk;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import krystian.kryszczak.autopay.sdk.http.client.HttpClient;
import krystian.kryszczak.autopay.sdk.itn.Itn;
import krystian.kryszczak.autopay.sdk.itn.request.ItnRequest;
import krystian.kryszczak.autopay.sdk.itn.response.ItnResponse;
import krystian.kryszczak.autopay.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.autopay.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.autopay.sdk.serializer.Serializer;
import krystian.kryszczak.autopay.sdk.transaction.Transaction;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionBackgroundRequest;
import krystian.kryszczak.autopay.sdk.transaction.request.TransactionInitRequest;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Singleton
@ApiStatus.AvailableSince("1.0")
public final class ReactorAutopayClient extends AutopayClient {
    @Inject
    public ReactorAutopayClient(@NotNull AutopayConfiguration configuration, @NotNull HttpClient httpClient, @NotNull Serializer serializer) {
        super(configuration, httpClient, serializer);
    }

    public ReactorAutopayClient(@NotNull AutopayConfiguration configuration) {
        super(configuration);
    }

    @Override
    public @NotNull Mono<? extends @NotNull Transaction> doTransactionBackground(@NotNull TransactionBackgroundRequest transactionRequest) {
        return Mono.fromDirect(super.doTransactionBackground(transactionRequest));
    }

    @Override
    public @NotNull Mono<? extends @NotNull Transaction> doTransactionInit(@NotNull TransactionInitRequest transactionRequest) {
        return Mono.fromDirect(super.doTransactionInit(transactionRequest));
    }

    @Override
    public @NotNull Mono<@NotNull ItnResponse> doItnInResponse(@NotNull ItnRequest itnRequest, Predicate<@NotNull Itn> confirmTransactionPredicate) {
        return Mono.fromDirect(super.doItnInResponse(itnRequest, confirmTransactionPredicate));
    }

    @Override
    public @NotNull Mono<PaywayListResponse> getPaywayList(@NotNull String gatewayUrl) {
        return Mono.fromDirect(super.getPaywayList(gatewayUrl));
    }

    @Override
    public @NotNull Mono<RegulationListResponse> getRegulationList(@NotNull String gatewayUrl) {
        return Mono.fromDirect(super.getRegulationList(gatewayUrl));
    }
}
