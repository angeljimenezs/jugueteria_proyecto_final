package edu.unam.jugueteria.auth.service;

import edu.unam.jugueteria.auth.dto.UsuarioDTO;
import edu.unam.jugueteria.auth.exception.UsuarioNotFoundException;
import edu.unam.jugueteria.auth.model.Usuario;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> findAll();
    UsuarioDTO findById(Integer id) throws UsuarioNotFoundException;
    UsuarioDTO save(UsuarioDTO userAdmin) throws UsuarioNotFoundException;
    UsuarioDTO findByUserEmail(String email) throws UsuarioNotFoundException;

    Usuario findByEmail(String email) throws UsuarioNotFoundException;


}
