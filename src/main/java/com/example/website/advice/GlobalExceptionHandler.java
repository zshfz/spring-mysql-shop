package com.example.website.advice;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("status", 400);
        return "error";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUsernameNotFound(UsernameNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("status", 400);
        return "error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("status", 403);
        return "error";
    }
}
