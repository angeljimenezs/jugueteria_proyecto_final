package edu.unam.jugueteria.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.unam.jugueteria.auth.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "orden")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Integer idOrden;
    @Column(name = "fecha_creacion", columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private String fechaCreacion;
    @Column(name = "estatus")
    private String estatus;
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_pago")
    private Pago pago;
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_direccion")
    private Direccion direccion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
