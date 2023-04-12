## BlueMedia Java SDK (Un-official)

Kod zawarty w tym repozytorium umożliwia wykonanie transakcji oraz innych usług oferowanych przez Blue Media S.A.

Użycie SDK zalecane jest podczas implementacji własnych modułów płatności.

## Spis treści
- [Wymagania](#wymagania)
- [Konfiguracja klienta](#konfiguracja-klienta)
- [Pobieranie listy aktualnie dostępnych regulaminów](#pobieranie-listy-aktualnie-dostępnych-regulaminów)
- [Pobieranie listy kanałów płatności](#pobieranie-listy-kanałów-płatności)

## Wymagania
- Java 17 lub nowsza.

## Konfiguracja klienta

W celu utworzenia warstwy komunikacji należy utworzyć obiekt klasy `BlueMediaClient` podając id serwisu oraz klucz współdzielony (przyznane przez BlueMedia).

```java
private BlueMediaClient blueMediaClient = new BlueMediaClient("ID SERWISU", "KLUCZ WSPÓŁDZIELONY");
```

Podczas tworzenia obiektu klienta, za argumentami danych serwisu można dodatkowo dodać użyty tryb szyfrowania oraz separator danych (w przypadku kiedy są nadane inne niż domyślne):

```java
private BlueMediaClient blueMediaClient = new BlueMediaClient(
  "ID SERWISU",
  "KLUCZ WSPÓŁDZIELONY",
  "sha256", // tryb hashowania, domyślnie sha256, można użyć stałej z BlueMedia\Common\Enum\ClientEnum
  "|" // separator danych, domyślnie |
);
```

## Pobieranie listy aktualnie dostępnych regulaminów
Metoda `getRegulationList` umożliwia odpytanie o aktualną listę regulaminów wraz linkami do wyświetlenia w serwisie oraz akceptacji przez klienta.

```java
Maybe<PaywayList> result = this.blueMediaClient.getRegulationList("https://pay-accept.bm.pl");
```

## Pobieranie listy kanałów płatności
Metoda `getPaywayList` umożliwia odpytanie o aktualną listę płatności.

```java
Maybe<PaywayList> result = this.blueMediaClient.getPaywayList("https://pay-accept.bm.pl");
```
