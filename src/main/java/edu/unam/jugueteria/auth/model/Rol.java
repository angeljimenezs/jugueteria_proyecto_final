package edu.unam.jugueteria.auth.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "rol")
public class Rol {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_rol", columnDefinition = "int", nullable = false, updatable = false)
    private Integer idRol;
    @Column(name = "nombre", columnDefinition = "varchar(30)", length = 30, nullable = false)
    private String nombre;
}
