package com.pmarko09.cart_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorInfoDto {

    private String message;
    private LocalDateTime time;
    private HttpStatus httpStatus;
}
