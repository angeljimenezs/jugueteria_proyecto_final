package edu.unam.jugueteria.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RolNotFoundException extends Exception {
    public RolNotFoundException(Integer id) {
        super("Rol with id " + id + " is NOT found");
    }
}
