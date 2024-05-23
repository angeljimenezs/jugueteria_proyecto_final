package edu.unam.jugueteria.system.service;

import edu.unam.jugueteria.system.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService {
    List<Producto> findAll();

    Page<Producto> findAll(Pageable pageable);

    Producto findById(Integer id);

    Producto save(Producto producto);

    void delete(Integer id);
}
