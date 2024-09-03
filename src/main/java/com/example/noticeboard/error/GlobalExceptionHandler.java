package com.example.noticeboard.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Mono<String> handleRuntimeException(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return Mono.just("errorPage");
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<String> handleBindException(WebExchangeBindException  ex, Model model) {
        String errorMessages = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        model.addAttribute("error", errorMessages);
        return Mono.just("errorPage");
    }
}
