package edu.unam.jugueteria.auth.repository;

import edu.unam.jugueteria.auth.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Rol findByNombre(String role);
    List<Rol> findAllByOrderByIdRolAsc();
    List<Rol> findAllByOrderByNombreAsc();
}
