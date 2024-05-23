package edu.unam.jugueteria.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.unam.jugueteria.auth.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;
    @Column(name = "numero_tarjeta")
    private String numeroTarjeta;
    @Column(name = "titular_nombre")
    private String titularNombre;
    @Column(name = "titular_apellido")
    private String titularApellido;
    @Column(name = "caducidad")
    private String caducidad;
    @Column(name = "cvv")
    private String cvv;
    @Column(name = "tipo_de_tarjeta")
    private String tipoDeTarjeta;
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Override
    public String toString() {
        return String.format("Tarjeta: %s Caducidad: %s",
                numeroTarjeta.substring(numeroTarjeta.length()-3),
                caducidad);
    }
}
