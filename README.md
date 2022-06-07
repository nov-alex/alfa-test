Alfa-test Application
=====================
Приложение выполняет запросы к сторонним сервисам:
* openexchangerates.org - получение актуальных и вчерашних курсов валют
* giphy.com - поиск изображений по ключевым словам **broke** и **rich**
Все настройки в application.properties

# Используемые инструменты
* Gradle
* Spring Boot 2
* Lombok
* JUnit 5
* Mockito
* OpenFeign

# Описание работы
Базовая валюта задается в application.properties. Сервис предоставляет endpoints для получения списка валют и выдачу ссылки на изображение, визуально представляющую динамику базовой валюты к запрощенной на текущий момент и вчерашний день (UTC).   

# API endpoints
## GET /api/image/currencies<br/>
Получить список валют<br/>
**Ответ**<br/>
```
// Успешный ответ (200 OK)
{
    "baseCurrency":"USD",
    "currencies": {
          "AED":"United Arab Emirates Dirham",
                     /*****/
          "ZWL":"Zimbabwean Dollar"
          }
}

// Если возникла ошибка по любой причине (400 Bad Request)
{
    "message":"openexchangerates.org executing GET https://openexchangerates.org/****",
    "error":400
}
```

## GET /api/image/{3х-символьный код валюты}<br/>
Получить ссылку на изображение, визуально представляющую динамику базовой валюты к запрощенной на текущий момент и вчерашний день (UTC)<br/>
Так как базовая валюта задана в настройках, то я отказался от схемы http://localhost:8100/api/image/{baseCurrency}/to/{сurrency} <br/>
**Ответ**<br/>
```
// Успешный ответ (200 OK)
{
    "imageUrl":"https://media3.giphy.com/****",
    "imageDescription":"You are broke"
}

// Если возникла ошибка по любой причине (400 Bad Request)
{
    "message":"openexchangerates.org executing GET https://openexchangerates.org/****",
    "error":400
}
```

# Запуск проекта
Сборка выполняется с использованием Gradle. Все команды выполнять в корневой папке проекта. Используется OpenJDK 17
## Очистить проект<br/>
`gradlew clean`

## Запуск тестов<br/>
`gradlew test`

## Запуск локальный<br/>
`gradlew bootRun` <br/>
Сервис прослушивает порт 8100:<br/>
Получить список валют: `http://localhost:8100/api/image/currencies` <br/>
Получить ссылку на изображение: `http://localhost:8100/api/image/AFN` <br/>

## Запуск локальный с трассировкой<br/>
`gradlew runDev` <br/>
Сервис прослушивает порт 8100:<br/>
Получить список валют: `http://localhost:8100/api/image/currencies` <br/>
Получить ссылку на изображение: `http://localhost:8100/api/image/AFN` <br/>

## Сборка файла jar (для Docker, например)<br/>
`gradlew bootJar`<br/>

В корневой папке появится файл **alfa-test.jar**.<br/>
Запуск из консоли:<br/>
`java -jar alfa-test.jar` <br/>
PS. Этот файл удаляется при `gradlew clean`
## Запуск Docker<br/>
Собираем jar-файл<br/>
`gradlew bootJar`<br/>
Создаем образ Docker из Dockerfile<br/>
`docker build -t alfa-test .`<br/>
Запускаем собранный образ<br/>
`docker run -p 8100:8100 alfa-test`<br/>

Сервис прослушивает порт 8100:<br/>
Получить список валют: `http://localhost:8100/api/image/currencies` <br/>
Получить ссылку на изображение: `http://localhost:8100/api/image/AFN` <br/>

# Задание (оригинальный вид)
Описание
Создать сервис, который обращается к сервису курсов валют, и отображает gif:
если курс по отношению к USD за сегодня стал выше вчерашнего, то отдаем рандомную отсюда https HYPERLINK "https://giphy.com/search/rich":// HYPERLINK "https://giphy.com/search/rich"giphy HYPERLINK "https://giphy.com/search/rich". HYPERLINK "https://giphy.com/search/rich"com HYPERLINK "https://giphy.com/search/rich"/ HYPERLINK "https://giphy.com/search/rich"search HYPERLINK "https://giphy.com/search/rich"/ HYPERLINK "https://giphy.com/search/rich"rich
если ниже - отсюда https HYPERLINK "https://giphy.com/search/broke":// HYPERLINK "https://giphy.com/search/broke"giphy HYPERLINK "https://giphy.com/search/broke". HYPERLINK "https://giphy.com/search/broke"com HYPERLINK "https://giphy.com/search/broke"/ HYPERLINK "https://giphy.com/search/broke"search HYPERLINK "https://giphy.com/search/broke"/ HYPERLINK "https://giphy.com/search/broke"broke
Ссылки
REST API курсов валют - https HYPERLINK "https://docs.openexchangerates.org/":// HYPERLINK "https://docs.openexchangerates.org/"docs HYPERLINK "https://docs.openexchangerates.org/". HYPERLINK "https://docs.openexchangerates.org/"openexchangerates HYPERLINK "https://docs.openexchangerates.org/". HYPERLINK "https://docs.openexchangerates.org/"org HYPERLINK "https://docs.openexchangerates.org/"/
REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide
Must Have
Сервис на Spring Boot 2 + Java / Kotlin
Запросы приходят на HTTP endpoint (должен быть написан в соответствии с rest conventions), туда передается код валюты по отношению с которой сравнивается USD
Для взаимодействия с внешними сервисами используется Feign
Все параметры (валюта по отношению к которой смотрится курс, адреса внешних сервисов и т.д.) вынесены в настройки
На сервис написаны тесты (для мока внешних сервисов можно использовать @mockbean или WireMock)
Для сборки должен использоваться Gradle
Результатом выполнения должен быть репо на GitHub с инструкцией по запуску
Nice to Have
Сборка и запуск Docker контейнера с этим сервисом

Срок выполнения задания - 1 неделя) удачи!
