package edu.unam.jugueteria.system.service.impl;

import edu.unam.jugueteria.system.model.Orden;
import edu.unam.jugueteria.system.repository.OrdenRepository;
import edu.unam.jugueteria.system.service.OrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrdenServiceImpl implements OrdenService {
    @Autowired
    OrdenRepository ordenRepository;

    @Override
    public List<Orden> findAllByUserEmail(String email) {
        return ordenRepository.findAllByUsuarioEmailOrderByFechaCreacionDesc(email);
    }

    @Override
    public Page<Orden> findAllByUserEmail(String email, Pageable pageable) {
        return ordenRepository.findAllByUsuarioEmailOrderByFechaCreacionDesc(email, pageable);
    }

    @Override
    public Orden findActiveOrder(String email) {
        return ordenRepository.findFirstByUsuarioEmailOrderByFechaCreacionDesc(email);
    }

    @Override
    public Orden findById(Integer id) {
        return ordenRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Orden send(Orden orden) {
        Orden sentOrden = ordenRepository.save(orden);
        Orden newOrden = Orden.builder()
                .pago(null)
                .direccion(null)
                .estatus("Creación")
                .usuario(sentOrden.getUsuario())
                .fechaCreacion(null)
                .build();
        System.out.println("Orden enviada...");
        ordenRepository.save(newOrden);
        return sentOrden;
    }

    @Override
    public Orden save(Orden orden) {
        return ordenRepository.save(orden);
    }

    @Override
    public void delete(Integer id) {
        ordenRepository.deleteById(id);
    }
}
