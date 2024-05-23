package edu.unam.jugueteria.system.service;

import edu.unam.jugueteria.system.model.Carrito;
import edu.unam.jugueteria.system.model.Orden;
import edu.unam.jugueteria.system.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CarritoService {
    List<Carrito> findAllByOrden(Orden orden);

    Page<Carrito> findAllByOrden(Orden orden, Pageable pageable);

    List<Carrito> findAllByOrdenAndProduct(Orden orden, Producto producto);

    Page<Carrito> findAllByOrdenAndProduct(Orden orden, Producto producto, Pageable pageable);

    List<Carrito> findAllByOrdenIdAndProductId(Integer idOrden, Integer idProducto);

    Page<Carrito> findAllByOrdenIdAndProductId(Integer idOrden, Integer idProducto, Pageable pageable);

    Carrito addOne(Carrito carrito); // Aqui igual se tiene que hacer un update

    void removeOne(Carrito carrito);

    void delete(Integer id);

    Integer getTotalItemsInCartByOrder(Integer idOrden);
}
