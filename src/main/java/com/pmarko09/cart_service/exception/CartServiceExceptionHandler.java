package com.pmarko09.cart_service.exception;

import com.pmarko09.cart_service.model.dto.ErrorInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class CartServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ErrorInfoDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorInfoDto bodyResponse = new ErrorInfoDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartNotFoundException.class)
    protected ResponseEntity<ErrorInfoDto> handleCarteNotFoundException(CartNotFoundException ex) {
        ErrorInfoDto bodyResponse = new ErrorInfoDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductServiceUnavailableException.class)
    protected ResponseEntity<ErrorInfoDto> handleProductServiceUnavailableException(ProductServiceUnavailableException ex) {
        ErrorInfoDto bodyResponse = new ErrorInfoDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.SERVICE_UNAVAILABLE);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorInfoDto> handleBadRequestException(BadRequestException ex) {
        ErrorInfoDto bodyResponse = new ErrorInfoDto(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST);
        log.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
