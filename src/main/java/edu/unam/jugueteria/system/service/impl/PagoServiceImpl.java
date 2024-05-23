package edu.unam.jugueteria.system.service.impl;

import edu.unam.jugueteria.system.model.Pago;
import edu.unam.jugueteria.system.repository.PagoRepository;
import edu.unam.jugueteria.system.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {
    @Autowired
    PagoRepository pagoRepository;

    @Override
    public List<Pago> findAllByUserEmail(String email) {
        return pagoRepository.findAllByUsuarioEmail(email);
    }

    @Override
    public Page<Pago> findAllByUserEmail(String email, Pageable pageable) {
        return pagoRepository.findAllByUsuarioEmail(email, pageable);
    }

    @Override
    public Pago findById(Integer id) {
        return pagoRepository.findById(id).orElse(null);
    }

    @Override
    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public void delete(Integer id) {
        pagoRepository.deleteById(id);
    }
}
