## Autopay Java SDK (Un-official)

[![CodeQL](https://github.com/Krystian-Kryszczak/autopay-java-sdk/actions/workflows/codeql.yml/badge.svg)](https://github.com/Krystian-Kryszczak/autopay-java-sdk/actions/workflows/codeql.yml)
[![Java CI](https://github.com/Krystian-Kryszczak/autopay-java-sdk/actions/workflows/gradle.yml/badge.svg)](https://github.com/Krystian-Kryszczak/autopay-java-sdk/actions/workflows/gradle.yml)

The code contained in this repository enables the execution of transactions and other services offered by Autopay S.A.

## Table of contents
- [Requirements](#requirements).
- [Client Configuration](#client-configuration).
- [Transaction by redirecting to paywall](#transaction-by-redirecting-to-paywall)
- [Pre-transaction](#pre-transaction).
  * [Pre-transaction, link to continue payment](#pre-transaction-link-to-continue-payment).
  * [Pre-transaction, no-continuation](#pre-transaction-no-continuation).
- [Quick-transfer](#quick-transfer).
- [ITN (Instant Transaction Notification) processing](#itn-instant-transaction-notification-processing)
  * [ITN processing, message object creation](#itn-processing-message-object-creation).
- [Download list of currently available regulations](#download-list-of-currently-available-regulations)
- [Download list of payment channels](#download-list-of-payment-channels)

## Requirements
- Java 21 or later.

## Client configuration

In order to create a communication layer, create an object of class `AutopayClient` given the id of the service and the shared key (granted by Autopay).

```java
final AutopayConfiguration configuration = AutopayConfiguration.builder()
  .setServiceId("SERVICE ID")
  .setSharedKey("SHARED KEY")
  .build();

final AutopayClient client = new AutopayClient(configuration);
// or
final ReactorAutopayClient client = new ReactorAutopayClient(configuration);
```

When creating a client object, you can additionally add the used encryption mode and data separator (in case they are given other than the default) behind the service data arguments:

```java
final AutopayConfiguration configuration = AutopayConfiguration.builder()
  .setServiceId("SERVICE ID")
  .setSharedKey("SHARED KEY")
  .setHashAlgorithm(HashType.SHA256) // hash mode, sha256 by default, you can use a constant from HashType
  .setHashSeparator("|") // data separator, "|" by default
  .build();

final AutopayClient client = new AutopayClient(configuration);
// or
final ReactorAutopayClient client = new ReactorAutopayClient(configuration);
```

## Transaction by redirecting to paywall
The simplest type of transaction execution is redirecting to the Autopay service with the transaction data. Payment processing then lies entirely with the Autopay service.

To execute a transaction, call the `getTransactionRedirect` method, correct execution of the method will return a form that performs a redirect to the Autopay service:

```java
final String result = client.getTransactionRedirect(
  TransactionInitRequest.builder()
    .setGatewayUrl("https://testpay.autopay.eu") // Autopay gateway address
    .setTransaction(
      TransactionInit.builder()
        .orderID("123") // Transaction id, required
        .amount("1.20") // Transaction amount, required
        .description("Transaction 123-123") // Transaction title, optional
        .currency("PLN") // Currency of transaction, optional, PLN by default 
        .customerEmail("test@hostname.domain") // Customer email, optional, recommended due to auto-complete field on Autopay side of service
        .build()
    ).build()
);

System.out.println(result);
```

Once the payment is made, the Autopay service will perform a redirection to the pre-configured payment return address. The redirection is done via HTTPS (GET) request with three parameters:
- ServiceID - Service identifier
- OrderID - Transaction ID
- Hash - A checksum calculated from the ServiceID and OrderID.

It is required that the payment return page verify the correctness of the Hash, the `doConfirmationCheck` method is used for this. The data sent in the GET request should be passed to it:

```java
final Confirmation confirmation = new Confirmation(
  123456, // ServiceID
  "123", // OrderID
  "df5f737f48bcef93361f590b460cc633b28f91710a60415527221f9cb90da52a" // Hash
);

final boolean result = client.doConfirmationCheck(Confirmation);
```

## Pre-transaction
The `doTransactionInit` method extends the standard transaction initiation model to support specific needs:
- ordering a payment link based on the submitted parameters
- Customer debits (if no additional authorization by the customer is required)
- verify the correctness of the payment link before the Customer is redirected to the System - the call causes validation of the parameters and configuration of the System
- shorten the payment link - instead of several/several parameters, the link is shortened to two identifiers
- hiding sensitive data parameters of the transaction link - pre-transaction is done on the backend, and the link to continue the transaction does not contain sensitive data, only continuation identifiers
- the use of SDK in the full (safe) model

The method takes parameters like those of a paywall redirect transaction, except that a different header is sent, so that the Autopay service handles the request differently.
In response, you receive a link to continue the transaction or a reply informing you that there is no continuation and the status of the payment.

#### Pre-transaction, link to continue payment

```java
final Mono<? extends Transaction> result = client.doTransactionInit(
  TransactionInitRequest.builder()
    .setGatewayUrl("https://testpay.autopay.eu")
    .setTransaction(
      TransactionInit.builder()
        .orderID("123")
        .amount("1.20")
        .description("Transaction 123-123")
        .currency("PLN")
        .customerEmail("test@hostname.domain")
        .build()
    ).build()
);

final Transaction transactionContinue = result.block();

transactionContinue.getRedirectUrl(); // https://testpay.autopay.eu/payment/continue/9IA2UISN/718GTV5E
transactionContinue.getStatus(); // PENDING
transactionContinue.getOrderId(); // 123
transactionContinue.toArray(); // [...]
// ...
```

#### Pre-transaction, no-continuation

```java
final Mono<? extends Transaction> result = client.doTransactionInit(
  TransactionInitRequest.builder()
    .setGatewayUrl("https://testpay.autopay.eu")
    .setTransaction(
      TransactionInit.builder()
        .orderID("123")
        .amount("1.20")
        .description("Transaction 123-123")
        .gatewayID(1500)
        .currency("PLN")
        .customerEmail("test@hostname.domain")
        .customerIP("127.0.0.1")
        .title("Test")
        .build()
    ).build()
);

final Transaction transactionInit = result.block();

transactionInit.getConfirmation(); // NOTCONFIRMED
transactionInit.getReason(); // MULTIPLY_PAID_TRANSACTION
transactionInit.getOrderId(); // 123
transactionInit.toArray(); // [...]
// ...
```

## Quick transfer
Quick Transfer is a form of payment that requires the Customer to rewrite the transfer data provided by the System himself. The transfer data can be obtained through the `toTransactionBackground` method.

Depending on the payment channel that is selected in the context of the transaction, the method will return transfer data or a ready-made form.

Call example (data for transaction):

```java
final Mono<? extends Transaction> result = client.doTransactionBackground(
  TransactionBackgroundRequest.builder()
    .setGatewayUrl("https://testpay.autopay.eu")
    .setTransaction(
      TransactionBackground.builder()
        .orderID("12345")
        .amount("5.12")
        .description("Test transaction 12345")
        .gatewayID(21)
        .currency("PLN")
        .customerEmail("test@test.test")
        .customerIP("127.0.0.1")
        .title("Test")
        .validityTime(LocalDateTime.now().plusHours(5))
        .linkValidityTime(LocalDateTime.now().plusHours(5))
        .build()
    ).build()
);

final Transaction transactionBackground = result.block();

transactionBackground.getReceiverNRB(); // 47 1050 1764 1000 0023 2741 0516
transactionBackground.getReceiverName(); // Autopay
transactionBackground.getBankHref(); // https://ssl.bsk.com.pl/bskonl/login.html
transactionBackground.toArray(); // [...]
// ...
```

Call example (payment form):

```java
final Mono<? extends Transaction> result = client.doTransactionBackground(
  TransactionBackgroundRequest.builder()
    .setGatewayUrl("https://testpay.autopay.eu")
    .setTransaction(
      TransactionBackground.builder()
        .orderID("12345")
        .amount("5.12")
        .description("Test transaction 12345")
        .gatewayID(1500)
        .currency("PLN")
        .customerEmail("test@test.test")
        .customerIP("127.0.0.1")
        .title("Test")
        .validityTime(LocalDateTime.now().plusHours(5))
        .linkValidityTime(LocalDateTime.now().plusHours(5))
        .build()
    ).build()
);

result
  .doOnSuccess(System.out::println) // <form action="https://pg-accept.blue.pl/gateway/test/index.jsp" name="formGoPBL" method="POST"><input type="hidden" name="transaction" value="758519"> (...)
  .subscribe();
```

## ITN (Instant Transaction Notification) processing
The Autopay service sends a payment status message to a pre-configured ITN address after payment execution. The data is sent in XML format additionally encoded in base64.
The SDK offers a `doItnIn` method that, as a result of passing data from the Autopay service, returns a ready-made `ItnIn` object allowing the use of accessors or conversion to an array.
With this object, the programmer can use the data needed, for example, to update the status of payments in the database, etc.

Once the ITN message has been processed, a response must be passed. This is done by the `doItnInResponse` method, which takes an `ItnIn` object and an argument indicating that the transaction has been confirmed.

Below is an example of the application of ITN processing:

```java
final ItnRequest itnIn = client.doItnIn(transactions);

final Map<Itn, Boolean> predicates = new HashMap<>();

for (final Itn itn : itnIn.transactions.transaction) {
  final boolean transactionConfirmed = client.checkHash(itn);

  // If the payment status from ITN is confirmed and the hash is correct - complete the payment in the system
  if (itn.getPaymentStatus() == "SUCCESS" && transactionConfirmed) {
    final var order = orderRepository.find(itn.getOrderId());

    order.setPaymentCompleted();
    predicates.put(itn, true);
  } else {
    predicates.put(itn, false);
  }
}

final Serializer serializer = client.getSerializer();
final Mono<ItnResponse> itnResponse = client.doItnInResponse(itnIn, predicates::get);

return itnResponse.map(serializer::serialize);
```

#### ITN processing, message object creation
During implementation, it may become necessary, for example, to configure the client on the basis of access data based on currency before performing ITN processing.
In such a model, the programmer can help with the `getItnRequestObject` method.

```java
final ItnRequest itnRequest = AutopayClient.getItnRequestObject(encodedItnRequest, serializer);

for (final Itn itn : itnRequest.transactions.transaction) {
  itn.getCurrency(); // PLN
}
// ...
```

## Download list of currently available regulations
The `getRegulationList` method allows you to query the current list of regulations with links to be displayed on the site and accepted by the customer.

```java
final Mono<RegulationListResponse> result = client.getRegulationList("https://testpay.autopay.eu");
```

## Download list of payment channels
The `getPaywayList` method allows you to query the current payment list.

```java
final Mono<PaywayListResponse> result = client.getPaywayList("https://testpay.autopay.eu");
```
