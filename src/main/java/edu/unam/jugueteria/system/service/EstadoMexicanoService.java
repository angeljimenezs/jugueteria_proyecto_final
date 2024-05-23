package edu.unam.jugueteria.system.service;

import edu.unam.jugueteria.system.model.EstadoMexicano;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EstadoMexicanoService {
    List<EstadoMexicano> findAll();

    Page<EstadoMexicano> findAll(Pageable pageable);

    EstadoMexicano findById(Integer id);

    EstadoMexicano save(EstadoMexicano estado);

    void delete(Integer id);
}
