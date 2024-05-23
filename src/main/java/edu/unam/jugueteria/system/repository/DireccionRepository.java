package edu.unam.jugueteria.system.repository;

import edu.unam.jugueteria.system.model.Direccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    List<Direccion> findAllByUsuarioEmail(String email);
    Page<Direccion> findAllByUsuarioEmail(String email, Pageable pageable);

}
