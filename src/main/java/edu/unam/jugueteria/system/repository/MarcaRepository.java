package edu.unam.jugueteria.system.repository;

import edu.unam.jugueteria.system.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {
    boolean existsByDescripcion(String descripcion);
}
