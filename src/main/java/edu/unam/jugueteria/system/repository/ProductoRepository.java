package edu.unam.jugueteria.system.repository;

import edu.unam.jugueteria.system.model.Categoria;
import edu.unam.jugueteria.system.model.Marca;
import edu.unam.jugueteria.system.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findAllByMarca(Marca marca);
    List<Producto> findAllByCategoria(Categoria categoria);

}
