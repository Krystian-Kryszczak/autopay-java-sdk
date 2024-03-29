## Autopay Java SDK (Un-official)

[![CodeQL](https://github.com/Krystian-Kryszczak/autopay-java-sdk/actions/workflows/codeql.yml/badge.svg)](https://github.com/Krystian-Kryszczak/autopay-java-sdk/actions/workflows/codeql.yml)
[![Java CI](https://github.com/Krystian-Kryszczak/autopay-java-sdk/actions/workflows/gradle.yml/badge.svg)](https://github.com/Krystian-Kryszczak/autopay-java-sdk/actions/workflows/gradle.yml)

Kod zawarty w tym repozytorium umożliwia wykonanie transakcji oraz innych usług oferowanych przez Autopay S.A.

## Spis treści
- [Wymagania](#wymagania)
- [Konfiguracja klienta](#konfiguracja-klienta)
- [Transakcja poprzez przekierowanie na paywall](#transakcja-poprzez-przekierowanie-na-paywall)
- [Przedtransakcja](#przedtransakcja)
    * [Przedtransakcja, link do kontynuacji płatności](#przedtransakcja-link-do-kontynuacji-płatności)
    * [Przedtransakcja, brak kontynuacji](#przedtransakcja-brak-kontynuacji)
- [Szybki przelew](#szybki-przelew)
- [Obsługa ITN (Instant Transaction Notification)](#obsługa-itn-instant-transaction-notification)
    * [Obsługa ITN, utworzenie obiektu komunikatu](#obsługa-itn-utworzenie-obiektu-komunikatu)
- [Pobieranie listy aktualnie dostępnych regulaminów](#pobieranie-listy-aktualnie-dostępnych-regulaminów)
- [Pobieranie listy kanałów płatności](#pobieranie-listy-kanałów-płatności)

## Wymagania
- Java 21 lub nowsza.

## Konfiguracja klienta

W celu utworzenia warstwy komunikacji należy utworzyć obiekt klasy `AutopayClient` podając id serwisu oraz klucz współdzielony (przyznane przez Autopay).

```java
final AutopayConfiguration configuration = AutopayConfiguration.builder()
  .setServiceId("ID SERWISU")
  .setSharedKey("KLUCZ WSPÓŁDZIELONY")
  .build();

final AutopayClient client = new AutopayClient(configuration);
// lub
final ReactorAutopayClient client = new ReactorAutopayClient(configuration);
```

Podczas tworzenia obiektu klienta, za argumentami danych serwisu można dodatkowo dodać użyty tryb szyfrowania oraz separator danych (w przypadku kiedy są nadane inne niż domyślne):

```java
final AutopayConfiguration configuration = AutopayConfiguration.builder()
  .setServiceId("ID SERWISU")
  .setSharedKey("KLUCZ WSPÓŁDZIELONY")
  .setHashAlgorithm(HashType.SHA256) // tryb hashowania, domyślnie sha256, można użyć stałej z HashType
  .setHashSeparator("|") // separator danych, domyślnie |
  .build();

final AutopayClient client = new AutopayClient(configuration);
// lub
final ReactorAutopayClient client = new ReactorAutopayClient(configuration);
```

## Transakcja poprzez przekierowanie na paywall
Najprostszym typem wykonania transakcji jest przekierowanie do serwisu Autopay wraz z danymi o transakcji. Obsługa płatności leży wtedy w całości po stronie serwisu Autopay.

Aby wykonać transakcję należy wywołać metodę `getTransactionRedirect`, poprawne wykonanie metody zwróci formularz który wykona przekierowanie do serwisu Autopay:

```java
final String result = client.getTransactionRedirect(
  TransactionInitRequest.builder()
    .setGatewayUrl("https://testpay.autopay.eu") // Adres bramki Autopay
    .setTransaction(
      TransactionInit.builder()
        .orderID("123") // Id transakcji, wymagany
        .amount("1.20") // Kwota transakcji, wymagany
        .description("Transakcja 123-123") // Tytuł transakcji, opcjonalny
        .currency("PLN") // Waluta transakcji, opcjonalny, domyślnie PLN
        .customerEmail("test@hostname.domain") // Email klienta, opcjonalny, zalecany ze względu na automatyczne uzupełnienie pola po stronie serwisu BM
        .build()
    ).build()
);

System.out.println(result);
```

Po wykonaniu płatności, serwis Autopay wykona przekierowanie na skonfigurowany wcześniej adres powrotu płatności. Przekierowanie następuje poprzez żądanie HTTPS (GET) z trzema parametrami:
- ServiceID - Identyfikator serwisu
- OrderID - Identyfikator transakcji
- Hash - Suma kontrolna wyliczona na podstawie ServiceID i OrderID.

Wymagane jest, aby strona powrotu z płatności weryfikowała poprawność Hash, służy do tego metoda `doConfirmationCheck`. Należy przekazać do niej dane przesłane w żądaniu GET:

```java
final Confirmation confirmation = new Confirmation(
  123456, // ServiceID
  "123", // OrderID
  "df5f737f48bcef93361f590b460cc633b28f91710a60415527221f9cb90da52a" // Hash
);

final boolean result = client.doConfirmationCheck(Confirmation);
```

## Przedtransakcja
Metoda `doTransactionInit` rozszerza standardowy model rozpoczęcia transakcji o obsługę określonych potrzeb:
- zamówienia linku do płatności na podstawie przesłanych parametrów
- obciążenia Klienta (jeśli nie jest wymagana dodatkowa autoryzacja dokonana przez Klienta)
- zweryfikowania poprawności linku płatności, zanim Klient zostanie przekierowany do Systemu – wywołanie powoduje walidację parametrów i konfiguracji Systemu
- skrócenia linka płatności – zamiast kilku/kilkunastu parametrów, link zostaje skrócony do dwóch identyfikatorów
- ukrycia danych wrażliwych parametrów linku transakcji – przedtransakcja odbywa się backendowo, a link do kontynuacji transakcji nie zawiera danych wrażliwych, a jedynie identyfikatory kontynuacji
- użycia SDK w modelu pełnym (bezpiecznym)

Metoda przyjmuje parametry takie jak w przypadku transakcji z przekierowaniem na paywall, z tą różnicą że wysyłany jest inny nagłówek, dzięki czemu serwis Autopay obsługuje żądanie w inny sposób.
W odpowiedzi otrzymywany jest link do kontynuacji transakcji lub odpowiedź informująca o braku kontynuacji oraz statusem płatności.

#### Przedtransakcja, link do kontynuacji płatności

```java
final Mono<? extends Transaction> result = client.doTransactionInit(
  TransactionInitRequest.builder()
    .setGatewayUrl("https://testpay.autopay.eu")
    .setTransaction(
      TransactionInit.builder()
        .orderID("123")
        .amount("1.20")
        .description("Transakcja 123-123")
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

#### Przedtransakcja, brak kontynuacji

```java
final Mono<? extends Transaction> result = client.doTransactionInit(
  TransactionInitRequest.builder()
    .setGatewayUrl("https://testpay.autopay.eu")
    .setTransaction(
      TransactionInit.builder()
        .orderID("123")
        .amount("1.20")
        .description("Transakcja 123-123")
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

## Szybki przelew
Szybki przelew to forma płatności, która wymaga od Klienta samodzielnego przepisania danych do przelewu dostarczanych przez System. Dane do przelewu można pozyskać dzięki metodzie `doTransactionBackground`.

W zależności od kanału płatności jaki zostanie wybrany w kontekście transakcji, metoda zwróci dane do przelewu lub gotowy formularz.

Przykład wywołania (dane do transakcji):

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

Przykład wywołania (formularz płatności):

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

## Obsługa ITN (Instant Transaction Notification)
Serwis Autopay po wykonaniu płatności wysyła na wcześniej skonfigurowany adres ITN komunikat o statusie płatności. Dane przesyłane są w formacie XML dodatkowo zakodowanym w base64.
SDK oferuje metodę `doItnIn` która w wyniku przekazania danych z serwisu Autopay zwraca gotowy obiekt `ItnIn` pozwalający na użycie akcesorów lub konwersję do tablicy.
Dzięki temu obiektowi, programista może użyć danych potrzebnych np. do aktualizacji statusu płatności w bazie danych itp.

Po przetworzeniu komunikatu ITN należy przekazać odpowiedź. Służy do tego metoda `doItnInResponse` która przyjmuje obiekt `ItnIn` oraz argument informujący o potwierdzeniu transakcji.

Poniżej przykład zastosowania obsługi ITN:

```java
final ItnRequest itnIn = client.doItnIn(transactions);

final Map<Itn, Boolean> predicates = new HashMap<>();

for (final Itn itn : itnIn.transactions.transaction) {
  final boolean transactionConfirmed = client.checkHash(itn);
  
  // Jeżeli status płatności z ITN jest potwierdzony i hash jest poprawny - zakończ płatność w systemie
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

#### Obsługa ITN, utworzenie obiektu komunikatu
Podczas implementacji może okazać się że przed wykonaniem obsługi ITN zajdzie potrzeba np. konfiguracji klienta na podstawie danych dostępowych w oparciu o walutę.
W takim modelu programista może wspomóc się metodą `getItnRequestObject`.

```java
final ItnRequest itnRequest = AutopayClient.getItnRequestObject(encodedItnRequest, serializer);

for (final Itn itn : itnRequest.transactions.transaction) {
  itn.getCurrency(); // PLN
}
// ...
```

## Pobieranie listy aktualnie dostępnych regulaminów
Metoda `getRegulationList` umożliwia odpytanie o aktualną listę regulaminów wraz linkami do wyświetlenia w serwisie oraz akceptacji przez klienta.

```java
final Mono<RegulationListResponse> result = client.getRegulationList("https://testpay.autopay.eu");
```

## Pobieranie listy kanałów płatności
Metoda `getPaywayList` umożliwia odpytanie o aktualną listę płatności.

```java
final Mono<PaywayListResponse> result = client.getPaywayList("https://testpay.autopay.eu");
```
