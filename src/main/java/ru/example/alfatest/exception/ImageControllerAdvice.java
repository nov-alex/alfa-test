package ru.example.alfatest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.example.alfatest.dto.response.ErrorResponse;

@ControllerAdvice
public class ImageControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<ErrorResponse> handleThrowableException(Throwable ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}