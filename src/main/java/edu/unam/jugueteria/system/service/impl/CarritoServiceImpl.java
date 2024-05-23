package edu.unam.jugueteria.system.service.impl;

import edu.unam.jugueteria.system.model.Carrito;
import edu.unam.jugueteria.system.model.Orden;
import edu.unam.jugueteria.system.model.Producto;
import edu.unam.jugueteria.system.repository.CarritoRepository;
import edu.unam.jugueteria.system.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CarritoServiceImpl implements CarritoService {
    @Autowired
    CarritoRepository carritoRepository;


    @Override
    public List<Carrito> findAllByOrden(Orden orden) {
        return carritoRepository.findAllByOrden(orden);
    }

    @Override
    public Page<Carrito> findAllByOrden(Orden orden, Pageable pageable) {
        return carritoRepository.findAllByOrden(orden, pageable);
    }

    @Override
    public List<Carrito> findAllByOrdenAndProduct(Orden orden, Producto producto) {
        return carritoRepository.findAllByOrdenAndProducto(orden, producto);
    }

    @Override
    public Page<Carrito> findAllByOrdenAndProduct(Orden orden, Producto producto, Pageable pageable) {
        return carritoRepository.findAllByOrdenAndProducto(orden, producto, pageable);
    }

    @Override
    public List<Carrito> findAllByOrdenIdAndProductId(Integer idOrden, Integer idProducto) {
        return carritoRepository.findAllByOrdenIdOrdenAndProductoIdProducto(idOrden, idProducto);
    }

    @Override
    public Page<Carrito> findAllByOrdenIdAndProductId(Integer idOrden, Integer idProducto, Pageable pageable) {
        return carritoRepository.findAllByOrdenIdOrdenAndProductoIdProducto(idOrden, idProducto, pageable);
    }

    @Override
    public Carrito addOne(Carrito carrito) {
        if(carritoRepository.existsByProductoAndOrden(carrito.getProducto(), carrito.getOrden())) {
            System.out.println("Se encuentra ya en el carro " + carrito.getProducto().getTitulo());
            Producto sameProducto = carrito.getProducto();
            Orden sameOrden = carrito.getOrden();
            Carrito foundCarrito = carritoRepository.findByProductoAndOrden(sameProducto, sameOrden);
            foundCarrito.setCantidad(foundCarrito.getCantidad() + 1);
            Integer cantidad = foundCarrito.getCantidad();
            foundCarrito.setSubtotal(foundCarrito.getPrecioVenta().multiply(BigDecimal.valueOf(cantidad)));
            Carrito finalStatus = carritoRepository.save(foundCarrito);

            return finalStatus;
        }
        System.out.println("No deberia entrar aqui si ya entro a founde");
        Integer cantidad = carrito.getCantidad();
        carrito.setSubtotal(carrito.getPrecioVenta().multiply(BigDecimal.valueOf(cantidad)));
        return carritoRepository.save(carrito);

    }

    @Override
    public void removeOne(Carrito carrito) {
        if(carritoRepository.existsByProductoAndOrden(carrito.getProducto(), carrito.getOrden())) {
            System.out.println("Se encuentra ya en el carro " + carrito.getProducto().getTitulo());
            Producto sameProducto = carrito.getProducto();
            Orden sameOrden = carrito.getOrden();
            Carrito foundCarrito = carritoRepository.findByProductoAndOrden(sameProducto, sameOrden);
            foundCarrito.setCantidad(foundCarrito.getCantidad() - 1);
            if(foundCarrito.getCantidad() <= 0) {
                delete(foundCarrito.getIdCarrito());
                return;
            }
            Integer cantidad = foundCarrito.getCantidad();
            foundCarrito.setSubtotal(foundCarrito.getPrecioVenta().multiply(BigDecimal.valueOf(cantidad)));
            Carrito finalStatus = carritoRepository.save(foundCarrito);

        }/*
        System.out.println("No deberia entrar aqui si ya entro a founde");
        Integer cantidad = carrito.getCantidad();
        carrito.setSubtotal(carrito.getPrecioVenta().multiply(BigDecimal.valueOf(cantidad)));
        carritoRepository.save(carrito);
        return;
        */
    }

    @Override
    public void delete(Integer id) {
        carritoRepository.deleteById(id);
    }

    @Override
    public Integer getTotalItemsInCartByOrder(Integer idOrden) {
        return carritoRepository.getCantidadTotalPorOrden(idOrden);
    }
}
