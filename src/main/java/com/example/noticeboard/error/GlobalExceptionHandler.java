package com.example.noticeboard.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Mono<String> handleRuntimeException(Exception ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return Mono.just("errorPage");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<String> handleBindException(MethodArgumentNotValidException ex, Model model) {
        model.addAttribute("error", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return Mono.just("errorPage");
    }
}
