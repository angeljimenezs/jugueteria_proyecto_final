package edu.unam.jugueteria.system.service.impl;

import edu.unam.jugueteria.system.model.Marca;
import edu.unam.jugueteria.system.repository.MarcaRepository;
import edu.unam.jugueteria.system.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaServiceImpl implements MarcaService {
    @Autowired
    MarcaRepository marcaRepository;

    @Override
    public List<Marca> findAll() {
        return marcaRepository.findAll();
    }

    @Override
    public Page<Marca> findAll(Pageable pageable) {
        return marcaRepository.findAll(pageable);
    }

    @Override
    public Marca findById(Integer id) {
        return marcaRepository.findById(id).orElse(null);
    }

    @Override
    public Marca save(Marca marca) {
        return marcaRepository.save(marca);
    }

    @Override
    public void delete(Integer id) {
        marcaRepository.deleteById(id);
    }
}
