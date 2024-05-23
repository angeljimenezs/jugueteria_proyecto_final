package edu.unam.jugueteria.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private Integer idCarrito;
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    @Digits(integer = 5,fraction = 2)
    @Column(name = "precio_venta")
    private BigDecimal precioVenta;
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private Orden orden;

}
