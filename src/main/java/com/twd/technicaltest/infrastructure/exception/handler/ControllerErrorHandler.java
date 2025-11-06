package com.twd.technicaltest.infrastructure.exception.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.twd.technicaltest.infrastructure.exception.dto.ResponseErrorDTO;
import com.twd.technicaltest.infrastructure.exception.logger.LogException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ControllerErrorHandler {

    @LogException
    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ResponseErrorDTO> jsonParseException(JsonParseException e) {
        String errorMessage = e.getMessage();
        ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO()
                .setMessage(errorMessage);
        return new ResponseEntity<>(responseErrorDTO, HttpStatus.BAD_REQUEST);
    }

    @LogException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorDTO> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : fieldErrors) {
            errorMessage.append(fieldError.getField()).append(": ");
            errorMessage.append(fieldError.getDefaultMessage()).append(", ");
        }

        errorMessage.replace(errorMessage.length()-2, errorMessage.length(), ".");
        ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO()
                .setMessage(String.valueOf(errorMessage));
        return new ResponseEntity<>(responseErrorDTO, HttpStatus.BAD_REQUEST);
    }

    @LogException
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseErrorDTO> methodArgumentNotValidException(BadCredentialsException e) {
        String errorMessage = e.getMessage();
        ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO()
                .setMessage(errorMessage);
        return new ResponseEntity<>(responseErrorDTO, HttpStatus.BAD_REQUEST);
    }

}
