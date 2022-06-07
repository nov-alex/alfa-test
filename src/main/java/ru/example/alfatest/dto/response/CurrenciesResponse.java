package ru.example.alfatest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

/**
 * Объект для сериализации при запросе списка валют
 * <p>
 * Поле baseCurrency можно использовать для исключения
 * валюты из списка валют currencies
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrenciesResponse {
    private final String baseCurrency;
    private final Map<String, String> currencies;
}
