package edu.unam.jugueteria.system.repository;

import edu.unam.jugueteria.system.model.Pago;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findAllByUsuarioEmail(String email);
    Page<Pago> findAllByUsuarioEmail(String email, Pageable pageable);

}
