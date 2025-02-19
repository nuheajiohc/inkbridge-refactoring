package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.ApiError;
import com.nhnacademy.inkbridge.backend.dto.ConflictError;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.AlreadyProcessedException;
import com.nhnacademy.inkbridge.backend.exception.ConflictException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * class: ControllerAdvice.
 *
 * @author minm063
 * @version 2024/02/15
 */
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiError> handleNotFoundException(Exception e) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({AlreadyExistException.class, AlreadyProcessedException.class})
    public ResponseEntity<ApiError> handleAlreadyExistException(Exception e) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ApiError> handleValidationException(Exception e) {
        return new ResponseEntity<>(new ApiError(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException e) {
        List<String> result = e.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(new ApiError(result.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<ConflictError> handleConflictException(Exception e) {
        return new ResponseEntity<>(new ConflictError(e.getMessage()), HttpStatus.CONFLICT);
    }
}
