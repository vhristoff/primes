package org.hristov.primes.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    GlobalExceptionHandler.log.error(ex.getClass().getName() + ": " + ex.getMessage());
    FieldError fieldError = ex.getBindingResult().getFieldError();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ApiError.create(
                HttpStatus.BAD_REQUEST.value(),
                fieldError.getField() + ": " + fieldError.getDefaultMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleEverythingElse(Exception ex, WebRequest request) {
    return handleExceptionInternal(
        ex, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex,
      @Nullable Object body,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    GlobalExceptionHandler.log.error(ex.getClass().getName() + ": " + ex.getMessage());
    // A more precise exception messages should be defined
    return ResponseEntity.status(status).body(ApiError.create(status.value(), ex.getMessage()));
  }
}
