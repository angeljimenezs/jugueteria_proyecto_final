package edu.unam.jugueteria.system.service;

import edu.unam.jugueteria.system.model.Marca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MarcaService {
    List<Marca> findAll();

    Page<Marca> findAll(Pageable pageable);

    Marca findById(Integer id);

    Marca save(Marca marca);

    void delete(Integer id);
}
