package edu.unam.jugueteria.system.service;

import edu.unam.jugueteria.system.model.Direccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DireccionService {
    List<Direccion> findAllByUserEmail(String email);

    Page<Direccion> findAllByUserEmail(String email, Pageable pageable);

    Direccion findById(Integer id);

    Direccion save(Direccion direccion);

    void delete(Integer id);
}
