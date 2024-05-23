package edu.unam.jugueteria.system.controller;


import edu.unam.jugueteria.system.model.Carrito;
import edu.unam.jugueteria.system.model.Direccion;
import edu.unam.jugueteria.system.model.Orden;
import edu.unam.jugueteria.system.model.Pago;
import edu.unam.jugueteria.system.service.CarritoService;
import edu.unam.jugueteria.system.service.DireccionService;
import edu.unam.jugueteria.system.service.OrdenService;
import edu.unam.jugueteria.system.service.PagoService;
import edu.unam.jugueteria.system.util.RenderPagina;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "orden")
public class OrdenController {
    @Autowired
    OrdenService ordenService;

    @Autowired
    DireccionService direccionService;

    @Autowired
    PagoService pagoService;

    @Autowired
    CarritoService carritoService;




    @GetMapping("/summary")
    public String summaryOrder(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();

        Orden orden = ordenService.findActiveOrder(userEmail);
        List<Direccion> direccionList=direccionService.findAllByUserEmail(userEmail);
        List<Pago> pagoList= pagoService.findAllByUserEmail(userEmail);
        List<Carrito> carritoList = carritoService.findAllByOrden(orden);

        model.addAttribute("orden", orden);
        model.addAttribute("direccionList",direccionList);
        model.addAttribute("pagoList",pagoList);
        model.addAttribute("carritoList",carritoList);


        return "orden_form";
    }

    @PostMapping("/send")
    public String summaryOrder(Orden orden) {
        orden.setEstatus("Enviada...");
        System.out.println(orden);
        ordenService.send(orden);
        //return "home";
        return "redirect:/orden/list";
    }

    @GetMapping("/list")
    public String listEntity(@RequestParam(name="page",defaultValue = "0")int page,
                             Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();

        Pageable pagReq= PageRequest.of(page,5);
        Page<Orden> ordenes = ordenService.findAllByUserEmail(userEmail, pagReq);
        List<Orden> ordenList = ordenService.findAllByUserEmail(userEmail);
        RenderPagina<Orden> render=new RenderPagina<>("list",ordenes);
        model.addAttribute("ordenes",ordenes);
        model.addAttribute("page",render);
        model.addAttribute("contenido","Mis Ordenes");
        return "orden_list";
    }

}
