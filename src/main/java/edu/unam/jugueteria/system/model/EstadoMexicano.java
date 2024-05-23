package edu.unam.jugueteria.system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estado_mexicano")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoMexicano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_mexicano")
    private Integer idEstadoMexicano;
    @Column(name = "estado")
    private String estado;
    @Column(name = "abreviacion", columnDefinition = "varchar(5)", length = 5)
    private String abreviacion;
}
