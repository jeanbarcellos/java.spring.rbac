package com.jeanbarcellos.processmanager.dtos;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse {

    private Integer status;
    private String message;

    public static SuccessResponse create(String message) {
        return SuccessResponse
                .builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .build();
    }
}