package edu.unam.jugueteria.system.service.impl;

import edu.unam.jugueteria.system.model.EstadoMexicano;
import edu.unam.jugueteria.system.repository.EstadoMexicanoRepository;
import edu.unam.jugueteria.system.service.EstadoMexicanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoMexicanoServiceImpl implements EstadoMexicanoService {
    @Autowired
    EstadoMexicanoRepository estadoMexicanoRepository;

    @Override
    public List<EstadoMexicano> findAll() {
        return estadoMexicanoRepository.findAll();
    }

    @Override
    public Page<EstadoMexicano> findAll(Pageable pageable) {
        return estadoMexicanoRepository.findAll(pageable);
    }

    @Override
    public EstadoMexicano findById(Integer id) {
        return estadoMexicanoRepository.findById(id).orElse(null);
    }

    @Override
    public EstadoMexicano save(EstadoMexicano estado) {
        return estadoMexicanoRepository.save(estado);
    }

    @Override
    public void delete(Integer id) {
        estadoMexicanoRepository.deleteById(id);
    }
}
