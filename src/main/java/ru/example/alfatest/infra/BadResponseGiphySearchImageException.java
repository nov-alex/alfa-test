package ru.example.alfatest.infra;

public class BadResponseGiphySearchImageException extends RuntimeException {
    public BadResponseGiphySearchImageException(String message) {
        super(message);
    }
}
