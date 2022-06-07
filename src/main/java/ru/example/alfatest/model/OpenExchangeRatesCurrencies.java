package ru.example.alfatest.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Объектная модель списка валют которая возвращается с
 * <a href="https://openexchangerates.org/api/">openexchangerates.org</a>
 * <p>
 *
 **/
@Getter
@Setter
public class OpenExchangeRatesCurrencies {
    private Map<String, String> currencies;

    public OpenExchangeRatesCurrencies() {
        this.currencies = new LinkedHashMap<>();
    }

    /**
     * Сохраняет JSON с динамическими ключами в Map
     */
    @JsonAnySetter
    public void anySetter(String currencyName, String currencyDesc) {
        currencies.put(currencyName.toUpperCase(), currencyDesc);
    }
}
