package krystian.kryszczak.autopay.demo.controller;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.rxjava3.core.Maybe;
import krystian.kryszczak.autopay.demo.service.AutopayService;
import krystian.kryszczak.autopay.sdk.confirmation.Confirmation;
import krystian.kryszczak.autopay.sdk.payway.response.PaywayListResponse;
import krystian.kryszczak.autopay.sdk.regulation.response.RegulationListResponse;
import krystian.kryszczak.autopay.sdk.transaction.Transaction;
import krystian.kryszczak.autopay.sdk.transaction.TransactionBackground;
import krystian.kryszczak.autopay.sdk.transaction.TransactionContinue;
import krystian.kryszczak.autopay.sdk.transaction.TransactionInit;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Controller
@Secured(SecurityRule.IS_ANONYMOUS)
@RequiredArgsConstructor
public final class AutopayController {
    private final AutopayService autopayService;

    @Get("get-transaction-redirect")
    @NotNull String getTransactionRedirect(TransactionContinue transaction) {
        return autopayService.getTransactionRedirect(transaction);
    }

    @Get("do-transaction-init")
    @NotNull Maybe<@NotNull Transaction> doTransactionInit(TransactionInit transaction) {
        return autopayService.doTransactionInit(transaction);
    }

    @Get("do-transaction-background")
    @NotNull Maybe<@NotNull Transaction> doTransactionBackground(TransactionBackground transaction) {
        return autopayService.doTransactionBackground(transaction);
    }

    @Get("payway-list")
    @NotNull Maybe<PaywayListResponse> paywayList() {
        return autopayService.getPaywayList();
    }

    @Get("regulations-list")
    @NotNull Maybe<RegulationListResponse> regulationList() {
        return autopayService.getRegulationList();
    }

    @Post("check-hash")
    boolean checkHash(@Body @NotNull PaywayListResponse hashable) {
        return autopayService.checkHash(hashable);
    }

    @Post("do-confirmation-check")
    boolean checkHash(@Body @NotNull Confirmation confirmation) {
        return autopayService.doConfirmationCheck(confirmation);
    }
}
