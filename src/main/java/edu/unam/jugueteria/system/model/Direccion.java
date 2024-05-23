package edu.unam.jugueteria.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.unam.jugueteria.auth.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "direccion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Integer idDireccion;
    @Column(name = "calle")
    private String calle;
    @Column(name = "numero_exterior")
    private String numeroExterior;
    @Column(name = "numero_interior")
    private String numeroInterior;
    @Column(name = "codigo_postal")
    private String codigoPostal;
    @Column(name = "municipio")
    private String municipio;
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_estado_mexicano")
    private EstadoMexicano estadoMexicano;


    @Override
    public String toString() {
        return String.format(
                "%s %s, %s, %s, %s, %s",
                calle, numeroExterior, numeroInterior,
                municipio, estadoMexicano.getEstado(), codigoPostal
        );
    }
}
