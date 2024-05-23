package edu.unam.jugueteria.system.service.impl;

import edu.unam.jugueteria.system.model.Direccion;
import edu.unam.jugueteria.system.repository.DireccionRepository;
import edu.unam.jugueteria.system.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionServiceImpl implements DireccionService {
    @Autowired
    DireccionRepository direccionRepository;

    @Override
    public List<Direccion> findAllByUserEmail(String email) {
        return direccionRepository.findAllByUsuarioEmail(email);
    }

    @Override
    public Page<Direccion> findAllByUserEmail(String email, Pageable pageable) {
        return direccionRepository.findAllByUsuarioEmail(email, pageable);
    }

    @Override
    public Direccion findById(Integer id) {
        return direccionRepository.findById(id).orElse(null);
    }

    @Override
    public Direccion save(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    @Override
    public void delete(Integer id) {
        direccionRepository.deleteById(id);
    }
}
