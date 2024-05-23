package edu.unam.jugueteria.auth.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", columnDefinition = "int", nullable = false, updatable = false)
    private Integer idUsuario;
    private String email;
    private String contrasena;
    private String nombre;
    private String apellido;
    private String telefono;

    //ROLES
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "user_adm",
            name = "usuario_rol_relation",
            joinColumns = @JoinColumn(name = "urr_id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "urr_id_rol")
    )
    @JsonManagedReference
    private Set<Rol> roles = new HashSet<>();

    public String getFullName(){
        return this.nombre + " " + this.apellido;
    }

}
