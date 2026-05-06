package com.itc.uniwallet.config;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Map<String, String>> handleClientError(
        HttpClientErrorException e
    ) {
        log.error("Client error: {}", e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(
            Map.of("responseCode", "400", "responseMessage", e.getMessage())
        );
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Map<String, String>> handleServerError(
        HttpServerErrorException e
    ) {
        log.error("Server error: {}", e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(
            Map.of("responseCode", "500", "responseMessage", e.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAll(Exception e) {
        log.error("Unexpected error: {}", e.getMessage());
        return ResponseEntity.internalServerError().body(
            Map.of(
                "responseCode",
                "500",
                "responseMessage",
                (e.getMessage() != null)
                    ? e.getMessage()
                    : "An unexpected error occurred"
            )
        );
    }
}
