package edu.unam.jugueteria.system.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UserSession {
    private Integer totalCantidadCarrito = 0;

    public Integer getTotalCantidadCarrito() {
        return totalCantidadCarrito;
    }

    public void setTotalCantidadCarrito(Integer totalCantidadCarrito) {
        this.totalCantidadCarrito = totalCantidadCarrito;
    }
}