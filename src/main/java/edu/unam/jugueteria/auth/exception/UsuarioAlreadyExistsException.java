package edu.unam.jugueteria.auth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UsuarioAlreadyExistsException extends RuntimeException {
    public UsuarioAlreadyExistsException(Integer id) {
        super("Usuario con " + id + " NO encontrado");
    }

    public UsuarioAlreadyExistsException(String email) {
        super("¡Ya existe usuario con " + email + "!" );
    }
}
