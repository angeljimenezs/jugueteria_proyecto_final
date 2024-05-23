package edu.unam.jugueteria.auth.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolDTO {
    private Integer idRol;
    private String nombre;
}
