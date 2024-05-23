package edu.unam.jugueteria.system.controller;

import edu.unam.jugueteria.auth.exception.UsuarioNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice {
    /*
    @ExceptionHandler(DuplicateKeyException.class)
    public String handleDuplicateKeyException(DuplicateKeyException e, Model model) {
        model.addAttribute("errorMessage", "Usuario duplicado: " + e.getMessage());
        return "error"; // nombre de la vista de error
    }*/

    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFoundException(NoResourceFoundException e, Model model) {
        log.info(e.getMessage());
        model.addAttribute("errorMessage", "¡Recurso no encontrado!");

        return "error"; // nombre de la vista de error
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public String handleUsuarioNotFoundException(UsuarioNotFoundException e, Model model) {
        log.info(e.getClass().getSimpleName());
        model.addAttribute("errorMessage", e.getMessage());

        return "error"; // nombre de la vista de error
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception e, Model model) {
        log.info(e.getClass().getSimpleName());
        model.addAttribute("errorMessage", "Ocurrió un error: " + e.getMessage());

        return "error"; // nombre de la vista de error
    }


}