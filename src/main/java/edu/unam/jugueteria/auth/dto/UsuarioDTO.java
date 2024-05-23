package edu.unam.jugueteria.auth.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private Integer idUsuario;
    private String email;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String telefono;
    private Set<RolDTO> roles = new HashSet<>();
}
