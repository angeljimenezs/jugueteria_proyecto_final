package edu.unam.jugueteria.auth.repository;

import edu.unam.jugueteria.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findAllByOrderByIdUsuarioAsc();
    Usuario findByEmail(String email);
    boolean existsByEmail(String email);
}
