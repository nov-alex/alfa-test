package ru.example.alfatest.browserless;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.example.alfatest.model.OpenExchangeRatesCurrencies;
import ru.example.alfatest.model.OpenExchangeRatesRates;

@FeignClient(name = "oer-client", url = "${openexchangerates.path}")
public interface OpenExchangeRatesClient {

    /**
     * Выполняет запрос на получение списка всех поддерживаемых валют.
     * Название валюты представляет собой 3х символьное значение
     *
     * @param appId Ключ для авторизации Open Exchange Rates API App ID
     * @return {@link OpenExchangeRatesCurrencies}
     * @see <a href="https://docs.openexchangerates.org/docs/currencies-json">currencies.json</a>
     */
    @GetMapping(value = "/currencies.json")
    OpenExchangeRatesCurrencies getCurrencies(
            @RequestParam("app_id") String appId
    );

    /**
     * Выполняет запрос актуальных данных (текущие сутки в UTC) по курсам валют относительно
     * базовой валюты.
     *
     * @param appId        Ключ для авторизации Open Exchange Rates API App ID
     * @param baseCurrency Базовая валют, 3х символьное значение
     * @return {@link OpenExchangeRatesRates}
     * @see <a href="https://docs.openexchangerates.org/docs/latest-json">latest.json</a>
     */
    @GetMapping(value = "/latest.json")
    OpenExchangeRatesRates getLatest(
            @RequestParam("app_id") String appId,
            @RequestParam("base") String baseCurrency
    );

    /**
     * Выполняет запрос на получение архивных данным по курсам валют относительно
     * базовой валюты
     *
     * @param date         Дата, на которую запрашивается архив курсов в формате YYYY-MM-DD (часовой пояс UTC)
     * @param appId        Ключ для авторизации Open Exchange Rates API App ID
     * @param baseCurrency Базовая валют, 3х символьное значение
     * @return {@link OpenExchangeRatesRates}
     * @see <a href="https://docs.openexchangerates.org/docs/historical-json">historical/*.json</a>
     */
    @GetMapping(value = "/historical/{date}.json")
    OpenExchangeRatesRates getHistorical(
            @PathVariable String date,
            @RequestParam("app_id") String appId,
            @RequestParam("base") String baseCurrency
    );
}
