package ru.example.alfatest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.example.alfatest.browserless.OpenExchangeRatesClient;
import ru.example.alfatest.dto.GiphyImageDto;
import ru.example.alfatest.dto.GiphyImageType;
import ru.example.alfatest.dto.response.CurrenciesResponse;
import ru.example.alfatest.infra.BadResponseOpenExchangeRatesException;
import ru.example.alfatest.model.OpenExchangeRatesCurrencies;
import ru.example.alfatest.model.OpenExchangeRatesRates;
import ru.example.alfatest.service.OpenExchangeRatesService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

@Service
@Log4j2
@RequiredArgsConstructor
public class OpenExchangeRatesServiceImpl implements OpenExchangeRatesService {

    @Autowired
    private final OpenExchangeRatesClient openExchangeRatesClient;
    @Value("${openexchangerates.appId}")
    private String appId;
    @Value("${openexchangerates.baseCurrency}")
    private String baseCurrency;

    /**
     * Выдает список валют
     *
     * @return {@link CurrenciesResponse}
     */
    @Override
    public CurrenciesResponse getCurrencies() {
        OpenExchangeRatesCurrencies openExchangeRatesCurrencies = openExchangeRatesClient.getCurrencies(appId);
        return new CurrenciesResponse(baseCurrency.toUpperCase(), openExchangeRatesCurrencies.getCurrencies());
    }

    /**
     * Выдает актуальные курсы валют относительно базовой валюты
     *
     * @return {@link OpenExchangeRatesRates}
     */
    public OpenExchangeRatesRates getLatest() {
        return openExchangeRatesClient.getLatest(appId, baseCurrency);
    }

    /**
     * Выдает архивные курсы валют относительно базовой валюты в указанную дату
     *
     * @param date Дата, на которую запрашивается архив курсов в формате YYYY-MM-DD (часовой пояс UTC)
     * @return {@link OpenExchangeRatesRates}
     */
    public OpenExchangeRatesRates getHistorical(String date) {
        return openExchangeRatesClient.getHistorical(date, appId, baseCurrency);
    }

    /**
     * Выдает объект с типом требуемого изображения, показывающим динамику курса валют
     *
     * @param currency 3х-буквенный код валюты
     * @return {@link GiphyImageDto}
     * @throws {@link BadResponseOpenExchangeRatesException}
     */
    @Override
    public GiphyImageDto receiveImageType(String currency) throws BadResponseOpenExchangeRatesException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        String prevDate = dateFormat.format(calendar.getTime());
        OpenExchangeRatesRates yesterdayRates = getHistorical(prevDate);
        OpenExchangeRatesRates latestRates = getLatest();
        return compareRates(latestRates, yesterdayRates, currency);
    }

    /**
     * Сравнивает курсы валют относительно базовой валюты
     *
     * @param currentRates Актуальный курс валют
     * @param prevRates    Архивный курс валют
     * @param currency     Валюта, которая сравнивается с базовой валютой
     * @return {@link GiphyImageDto}
     * @throws {@link BadResponseOpenExchangeRatesException}
     */
    private GiphyImageDto compareRates(
            OpenExchangeRatesRates currentRates,
            OpenExchangeRatesRates prevRates,
            String currency) throws BadResponseOpenExchangeRatesException {
        String message;
        if (prevRates.getRates().containsKey(currency)
                && currentRates.getRates().containsKey(currency)) {
            if (currentRates.getRates().get(currency) > prevRates.getRates().get(currency)) {
                return new GiphyImageDto(GiphyImageType.GIPHY_RICH);
            } else {
                return new GiphyImageDto(GiphyImageType.GIPHY_BROKE);
            }
        } else {
            if (!prevRates.getRates().containsKey(currency)) {
                message = "Currency exchange rate for '" + currency + "' absent for yesterday";
            } else {
                message = "Currency exchange rate for '" + currency + "' absent for today";
            }
            throw new BadResponseOpenExchangeRatesException(message);
        }
    }
}
