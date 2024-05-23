package edu.unam.jugueteria.system.service;

import edu.unam.jugueteria.system.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoriaService {
    List<Categoria> findAll();

    Page<Categoria> findAll(Pageable pageable);
    Categoria findById(Integer id);
    Categoria save(Categoria categoria);

    void delete(Integer id);
}
