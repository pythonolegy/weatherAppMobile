# Aplikacja pogodowa
    
  Android aplikacja wyświetlająca bieżącą pogodę na 5 dni co 3 godziny we wskazanym mieście, wykorzystujące API serwis OpenWeatherMap. Приложение построено согласно Clean Architecture и реализует паттерн MVVM в UI-слое.


## Funkcjonalność

* Wyświetlanie pogody we wskazanym mieście oraz po geolokalizacji
* Pogoda na 5 dni z intervalem co 3 godziny
* Cash danych do bazy danych aplikacji


## Wykorzystane technoligii

* Kotlin
* Android SDK
* MVVM
* Coroutines
* Dagger 2
* Room
* Retrofit
* Glide
* RecyclerView


## Szczegóły
<details><summary>Szczególny opis aplikacji</summary></details>
  
 
### UI-wasrstwa

- **Ekran główny:**
  * Pole nazwy miasta;
  * Przycisk do wyświetlania pogody miasta;
  * Przycisk do wyświetlania pogody geolokalizacji;
  * Po odebraniu odpowiedzi serwera pojawia się blok z nazwą miasta i temperaturą;

  <sub>Po kliknięciu na pierwszy przycisk wysyła się API zapytanie na informację we wskaznym mieście. Jeśli miasto nie zostało znalezione albo sieć jest niedostępna, odpowidnie info wyświeli się Toast-powiadomienie.
- 
  <sub>Po kliknięciu na drugi przycisk pyta się zgoda na odebranie geolokalizacji. W przypadku odmowy pojawia się okienko z informacją po co jest potrzebna zgoda z dalszą możliwością przejścia do ustawień.
  
- <sub>Po odebraniu zgody wysyła się API zapytanie na dostawanie info zgodnie z koordynatami.

  <sub>Przy wciśnięciu na blok z nazwą miasta i temperaturą otwiera się `BottomSheetDialogFragment` z szczególną informacją o pogodzie. 

- **Экран BottomSheetDialogFragment:**
  * Znaczek pogody;
  * Lista parametrów bieżącej pogody: miasto, temperatura, opis pogody,wilgotność, ciśnienie, wiatr;
  * Lista z pogodą na 5 dni co 3 godziny.
 
    
  <sub>Przy zmianie pionowo-poziomo aplikacja działa poprawnie

## Установка

  * Clone repozytoria: 

  ```bash
  git clone https://github.com/pythonolegy/weatherAppMobile.git
  ```

  * w pliku `credentials.properties` dodać API klucz z serwisu OpenWeatherMap
  * 
  ```gradle
  WEATHER_API_KEY="your_key_here"
  ```
