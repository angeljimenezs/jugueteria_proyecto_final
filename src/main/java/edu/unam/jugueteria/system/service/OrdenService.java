package edu.unam.jugueteria.system.service;

import edu.unam.jugueteria.system.model.Orden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrdenService {
    List<Orden> findAllByUserEmail(String email);

    Page<Orden> findAllByUserEmail(String email, Pageable pageable);

    Orden findActiveOrder(String email);

    Orden findById(Integer id);

    Orden send(Orden orden);

    Orden save(Orden orden);

    void delete(Integer id);
}
