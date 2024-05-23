package edu.unam.jugueteria.auth.service.impl;

import edu.unam.jugueteria.auth.dto.RolDTO;
import edu.unam.jugueteria.auth.dto.UsuarioDTO;
import edu.unam.jugueteria.auth.exception.UsuarioAlreadyExistsException;
import edu.unam.jugueteria.auth.exception.UsuarioNotFoundException;
import edu.unam.jugueteria.auth.model.Rol;
import edu.unam.jugueteria.auth.model.Usuario;
import edu.unam.jugueteria.auth.repository.UsuarioRepository;
import edu.unam.jugueteria.auth.service.RolService;
import edu.unam.jugueteria.auth.service.UsuarioService;
import edu.unam.jugueteria.system.model.Orden;
import edu.unam.jugueteria.system.service.OrdenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolService rolService; //inyectamos el servicio!!! (DIP)
    private final OrdenService ordenService;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              PasswordEncoder passwordEncoder,
                              RolService rolService,
                              OrdenService ordenService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolService = rolService;
        this.ordenService = ordenService;
    }

    @Override
    public List<UsuarioDTO> findAll() {
        log.info("Service - UsuarioServiceImpl.findAll");
        List<Usuario> theList = usuarioRepository.findAllByOrderByIdUsuarioAsc();
        return theList.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO findById(Integer id) throws UsuarioNotFoundException {
        log.info("Service - UserAdmin.findById {}", id);
        Usuario object = usuarioRepository.findById(id).orElseThrow(() ->
                new UsuarioNotFoundException(id));
        return convertEntityToDTO(object);
    }

    @Override
    @Transactional
    public UsuarioDTO save(UsuarioDTO userAdmin) throws UsuarioNotFoundException {
        log.info("Service - UserAdmin.save");
        log.info("Service - UserAdmin.save object {} ", userAdmin);
        if (existsByUseEmail(userAdmin.getEmail()))
            throw new UsuarioAlreadyExistsException(userAdmin.getEmail());
        userAdmin.setContrasena(passwordEncoder.encode(userAdmin.getContrasena()));
        Usuario finalStatus = convertDTOtoEntity(userAdmin);
        finalStatus = usuarioRepository.save(finalStatus);

        // Se crea la primera orden también
        Orden firstOrden = Orden.builder()
                .pago(null)
                .direccion(null)
                .estatus("Creación")
                .usuario(finalStatus)
                .fechaCreacion(null)
                .build();
        ordenService.save(firstOrden);

        return convertEntityToDTO(finalStatus);
    }

    @Override
    public UsuarioDTO findByUserEmail(String email) throws UsuarioNotFoundException {
        Usuario object = usuarioRepository.findByEmail(email);
        if(object == null)
            throw new UsuarioNotFoundException(email);
        return convertEntityToDTO(object);
    }

    @Override
    public Usuario findByEmail(String email) throws UsuarioNotFoundException {
        Usuario object = usuarioRepository.findByEmail(email);
        if(object == null)
            throw new UsuarioNotFoundException(email);
        return object;
    }

    private boolean existsByUseEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    private UsuarioDTO convertEntityToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setContrasena(usuario.getContrasena());
        dto.setTelefono(usuario.getTelefono());
        Set<RolDTO> userInfoRoles = new HashSet<>();
        for (Rol role : usuario.getRoles()) {
            userInfoRoles.add(rolService.convertEntityToDTO(role));
        }
        dto.setRoles(userInfoRoles);
        /*dto.setUseInfoRoles(usuario.getUseInfoRoles()
                .stream()
                .map(userInfoRoleService::convertEntityToDTO)
                .collect(Collectors.toSet()));*/
        return dto;
    }

    private Usuario convertDTOtoEntity(UsuarioDTO usuario) {
        Usuario entity = new Usuario();
        entity.setIdUsuario(usuario.getIdUsuario());
        entity.setNombre(usuario.getNombre());
        entity.setApellido(usuario.getApellido());
        entity.setEmail(usuario.getEmail());
        entity.setContrasena(usuario.getContrasena());
        entity.setTelefono(usuario.getTelefono());
        Set<Rol> userInfoRoles = new HashSet<>();
        for (RolDTO role : usuario.getRoles()) {
            userInfoRoles.add(rolService.convertDTOtoEntity(role));
        }
        entity.setRoles(userInfoRoles);
        /*entity.setUseInfoRoles(usuario.getUseInfoRoles()
                .stream()
                .map(userInfoRoleService::convertDTOtoEntity)
                .collect(Collectors.toSet()));*/
        return entity;
    }
}
