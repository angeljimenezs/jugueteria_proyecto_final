package edu.unam.jugueteria.system.repository;

import edu.unam.jugueteria.system.model.Carrito;
import edu.unam.jugueteria.system.model.Orden;
import edu.unam.jugueteria.system.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    List<Carrito> findAllByOrden(Orden orden);
    Page<Carrito> findAllByOrden(Orden orden, Pageable pageable);

    List<Carrito> findAllByOrdenIdOrden(Integer idOrden);
    Page<Carrito> findAllByOrdenIdOrden(Integer idOrden, Pageable pageable);


    List<Carrito> findAllByOrdenAndProducto(Orden orden, Producto producto);
    Page<Carrito> findAllByOrdenAndProducto(Orden orden, Producto producto, Pageable pageable);

    List<Carrito> findAllByOrdenIdOrdenAndProductoIdProducto(Integer idOrden, Integer idProducto);
    Page<Carrito> findAllByOrdenIdOrdenAndProductoIdProducto(Integer idOrden, Integer idProducto, Pageable pageable);

    Carrito findByProductoAndOrden(Producto producto, Orden orden);

    boolean existsByProductoAndOrden(Producto producto, Orden orden);

    @Query("SELECT SUM(c.cantidad) FROM Carrito c WHERE c.orden.idOrden = :idOrden")
    Integer getCantidadTotalPorOrden(@Param("idOrden") Integer idOrden);
}
