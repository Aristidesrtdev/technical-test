package com.twd.technicaltest.infrastructure.exception.handler;

import com.twd.technicaltest.infrastructure.exception.dto.ResponseErrorDTO;
import com.twd.technicaltest.infrastructure.exception.logger.LogException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RepositoryErrorHandler {

    @LogException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseErrorDTO> constraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getMessage();
        ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO()
                .setMessage(errorMessage);
        return new ResponseEntity<>(responseErrorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @LogException
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseErrorDTO> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        String errorMessage = e.getMostSpecificCause().getMessage();
        ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO().setMessage(errorMessage);
        return new ResponseEntity<>(responseErrorDTO, HttpStatus.BAD_REQUEST);
    }

    @LogException
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ResponseErrorDTO> entityExistsException(EntityExistsException e) {
        String errorMessage = e.getMessage();
        ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO()
                .setMessage(errorMessage);
        return new ResponseEntity<>(responseErrorDTO, HttpStatus.CONFLICT);
    }

    @LogException
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseErrorDTO> entityNotFoundException(EntityNotFoundException e) {
        String errorMessage = e.getMessage();
        ResponseErrorDTO responseErrorDTO = new ResponseErrorDTO()
                .setMessage(errorMessage);
        return new ResponseEntity<>(responseErrorDTO, HttpStatus.NOT_FOUND);
    }


}
