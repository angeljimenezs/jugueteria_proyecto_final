package edu.unam.jugueteria.system.repository;

import edu.unam.jugueteria.system.model.Orden;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Integer> {
    List<Orden> findAllByUsuarioEmailOrderByFechaCreacionDesc(String email);
    Page<Orden> findAllByUsuarioEmailOrderByFechaCreacionDesc(String email, Pageable pageable);
    Orden findFirstByUsuarioEmailOrderByFechaCreacionDesc(String email);


}
