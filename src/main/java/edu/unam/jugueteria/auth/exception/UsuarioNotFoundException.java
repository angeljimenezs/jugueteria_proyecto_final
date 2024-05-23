package edu.unam.jugueteria.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(Integer id) {
        super("Usuario con " + id + " NO encontrado");
    }

    public UsuarioNotFoundException(String email) {
        super("¡Usuario con " + email + " NO existe!" );
    }
}
