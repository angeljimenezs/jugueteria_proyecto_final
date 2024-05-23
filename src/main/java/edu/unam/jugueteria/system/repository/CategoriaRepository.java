package edu.unam.jugueteria.system.repository;

import edu.unam.jugueteria.system.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    boolean existsByDescripcion(String descripcion);
}
