package edu.unam.jugueteria.auth.service;

import edu.unam.jugueteria.auth.dto.RolDTO;
import edu.unam.jugueteria.auth.exception.RolNotFoundException;
import edu.unam.jugueteria.auth.model.Rol;

import java.util.List;

public interface RolService {
    List<RolDTO> findAll();
    List<RolDTO> findAllOrderByRolNombre();
    RolDTO findById(Integer id) throws RolNotFoundException;
    RolDTO save(RolDTO rol);
    RolDTO convertEntityToDTO(Rol rol);
    Rol convertDTOtoEntity(RolDTO rolDto);
}
