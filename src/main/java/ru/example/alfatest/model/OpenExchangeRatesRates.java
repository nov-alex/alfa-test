package ru.example.alfatest.model;

import lombok.Data;

import java.util.Map;

/**
 * Объектная модель курса валют которая возвращается с
 * https://openexchangerates.org/api/
 * <p>
 * Курсы валют rates соответсвует 1ой единице указанной базовой валюты base.
 **/
@Data
public class OpenExchangeRatesRates {
    private String disclaimer;
    private String license;
    private Integer timestamp;
    private String base;
    private Map<String, Double> rates;
}
