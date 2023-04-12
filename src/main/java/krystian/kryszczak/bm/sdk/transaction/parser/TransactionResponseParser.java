package krystian.kryszczak.bm.sdk.transaction.parser;

import krystian.kryszczak.bm.sdk.BlueMediaConfiguration;
import krystian.kryszczak.bm.sdk.common.parser.ResponseParser;
import krystian.kryszczak.bm.sdk.http.Response;
import krystian.kryszczak.bm.sdk.transaction.Transaction;
import org.jetbrains.annotations.NotNull;

public final class TransactionResponseParser<T extends Transaction> extends ResponseParser<T> {
    public TransactionResponseParser(@NotNull Response<@NotNull T> response, @NotNull BlueMediaConfiguration configuration) {
        super(response, configuration);
    }

    public @NotNull Response<T> parse() {
        return parse(false);
    }

    public @NotNull Response<T> parse(boolean transactionInit) {
//        super.isResponseError();
//        $this->isErrorResponse();
        //        $paywayForm = $this->getPaywayFormResponse();
        //
        //        if (!empty($paywayForm)) {
        //            return new Response(htmlspecialchars_decode($paywayForm['1']['0']));
        //        }
        //
        //        if ($transactionInit === true) {
        //            return new Response($this->parseTransactionInitResponse());
        //        }
        //
        //        return new Response($this->parseTransactionBackgroundResponse());


        // TODO
        return getResponse();
    }


    //     public function parse(bool $transactionInit = false): Response
    //    {
    //        $this->isErrorResponse();
    //        $paywayForm = $this->getPaywayFormResponse();
    //
    //        if (!empty($paywayForm)) {
    //            return new Response(htmlspecialchars_decode($paywayForm['1']['0']));
    //        }
    //
    //        if ($transactionInit === true) {
    //            return new Response($this->parseTransactionInitResponse());
    //        }
    //
    //        return new Response($this->parseTransactionBackgroundResponse());
    //    }

    //    private function getPaywayFormResponse(): array
    //    {
    //        $matchesCount = preg_match_all(ClientEnum::PATTERN_PAYWAY, $this->response, $data);
    //
    //        return $matchesCount === 0 ? [] : $data;
    //    }
    //
    //    private function parseTransactionBackgroundResponse(): SerializableInterface
    //    {
    //        /** @var TransactionBackground $krystian.kryszczak.bm.sdk.transaction */
    //        $krystian.kryszczak.bm.sdk.transaction = (new Serializer())->deserializeXml($this->response, TransactionBackground::class);
    //
    //        if (HashChecker::checkHash($krystian.kryszczak.bm.sdk.transaction, $this->configuration) === false) {
    //            throw HashException::wrongHashError();
    //        }
    //
    //        return $krystian.kryszczak.bm.sdk.transaction;
    //    }
    //
    //    private function parseTransactionInitResponse(): SerializableInterface
    //    {
    //        $xmlTransaction = XMLParser::parse($this->response);
    //
    //        if (isset($xmlTransaction->redirecturl)) {
    //            /** @var TransactionContinue $krystian.kryszczak.bm.sdk.transaction */
    //            $krystian.kryszczak.bm.sdk.transaction = (new Serializer())->deserializeXml($this->response, TransactionContinue::class);
    //        } else {
    //            /** @var TransactionInit $krystian.kryszczak.bm.sdk.transaction */
    //            $krystian.kryszczak.bm.sdk.transaction = (new Serializer())->deserializeXml($this->response, TransactionInit::class);
    //        }
    //
    //        if (HashChecker::checkHash($krystian.kryszczak.bm.sdk.transaction, $this->configuration) === false) {
    //            throw HashException::wrongHashError();
    //        }
    //
    //        return $krystian.kryszczak.bm.sdk.transaction;
    //    }
}
