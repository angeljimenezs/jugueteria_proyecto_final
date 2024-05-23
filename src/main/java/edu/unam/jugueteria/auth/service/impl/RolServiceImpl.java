package edu.unam.jugueteria.auth.service.impl;

import edu.unam.jugueteria.auth.dto.RolDTO;
import edu.unam.jugueteria.auth.exception.RolNotFoundException;
import edu.unam.jugueteria.auth.model.Rol;
import edu.unam.jugueteria.auth.repository.RolRepository;
import edu.unam.jugueteria.auth.service.RolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RolServiceImpl implements RolService {
    private final RolRepository rolRepository;

    @Autowired
    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public List<RolDTO> findAll() {
        log.info("Service - RolServiceImpl.findAll");
        List<Rol> theList = rolRepository.findAllByOrderByIdRolAsc();
        return theList.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public List<RolDTO> findAllOrderByRolNombre() {
        log.info("Service - RolServiceImpl.findAll");
        List<Rol> theList = rolRepository.findAllByOrderByNombreAsc();
        return theList.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public RolDTO findById(Integer id) throws RolNotFoundException {
        log.info("Service - RolServiceImpl.findById {}", id);
        Rol object = rolRepository.findById(id).orElseThrow(() ->
                new RolNotFoundException(id));
        return convertEntityToDTO(object);
    }

    @Override
    public RolDTO save(RolDTO rol) {
        log.info("Service - RolServiceImpl.save object {} ", rol);
        Rol finalStatus = convertDTOtoEntity(rol);
        finalStatus = rolRepository.save(finalStatus);
        return convertEntityToDTO(finalStatus);
    }

    public RolDTO convertEntityToDTO(Rol userInfo) {
        RolDTO dto = new RolDTO();
        dto.setIdRol(userInfo.getIdRol());
        dto.setNombre(userInfo.getNombre());
        return dto;
    }

    public Rol convertDTOtoEntity(RolDTO userInfo) {
        Rol entity = new Rol();
        entity.setIdRol(userInfo.getIdRol());
        entity.setNombre(userInfo.getNombre());
        return entity;
    }
}
