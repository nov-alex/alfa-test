package ru.example.alfatest.infra;

public class BadResponseOpenExchangeRatesException extends RuntimeException {
    public BadResponseOpenExchangeRatesException(String message) {
        super(message);
    }
}
