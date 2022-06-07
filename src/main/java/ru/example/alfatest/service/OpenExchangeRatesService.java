package ru.example.alfatest.service;

import ru.example.alfatest.dto.GiphyImageDto;
import ru.example.alfatest.dto.response.CurrenciesResponse;
import ru.example.alfatest.infra.BadResponseOpenExchangeRatesException;

public interface OpenExchangeRatesService {
    /**
     * Выдает список валют в формате 3х-буквенный кода валюты и полного названия
     */
    CurrenciesResponse getCurrencies();

    /**
     * Выдает объект с типом требуемого изображения, показывающим динамику курса валют
     */
    GiphyImageDto receiveImageType(String currency) throws BadResponseOpenExchangeRatesException;
}
