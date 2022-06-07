package ru.example.alfatest.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Объект для сериализации при ошибках
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final String message;
    private final int error;
}