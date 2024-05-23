package edu.unam.jugueteria.system.service;

import edu.unam.jugueteria.system.model.Pago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PagoService {
    List<Pago> findAllByUserEmail(String email);

    Page<Pago> findAllByUserEmail(String email, Pageable pageable);

    Pago findById(Integer id);

    Pago save(Pago pago);

    void delete(Integer id);
}
