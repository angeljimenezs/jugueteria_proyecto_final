package edu.unam.jugueteria.system.controller;

import edu.unam.jugueteria.auth.model.Usuario;
import edu.unam.jugueteria.auth.service.UsuarioService;
import edu.unam.jugueteria.system.model.Direccion;
import edu.unam.jugueteria.system.model.EstadoMexicano;
import edu.unam.jugueteria.system.service.DireccionService;
import edu.unam.jugueteria.system.service.EstadoMexicanoService;
import edu.unam.jugueteria.system.util.RenderPagina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "direccion")
public class DireccionController {
    @Autowired
    DireccionService direccionService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EstadoMexicanoService estadoMexicanoService;


    @GetMapping("/register")
    public String registerEntity(Model model) {
        Direccion direccion = new Direccion();
        List<EstadoMexicano> estadoList = estadoMexicanoService.findAll();

        model.addAttribute("direccion", direccion);
        model.addAttribute("estadoList", estadoList);

        return "direccion_form";
    }

    @PostMapping("/save")
    public String saveEntity(Direccion direccion) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();

        Usuario searchedUsuario = usuarioService.findByEmail(userEmail);
        direccion.setUsuario(searchedUsuario);

        direccionService.save(direccion);
        return "home";
    }

    @GetMapping("/list")
    public String listEntity(@RequestParam(name="page", defaultValue = "0")int page,
                             Model model){
        Pageable pagReq= PageRequest.of(page,2);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();


        Page<Direccion> direcciones = direccionService.findAllByUserEmail(userEmail, pagReq);
        RenderPagina<Direccion> render=new RenderPagina<>("list",direcciones);
        model.addAttribute("direcciones",direcciones);
        model.addAttribute("page",render);
        model.addAttribute("contenido","Lista de Direccion");
        return "direccion_list";
    }

    @GetMapping("/modify/{id}")
    public String modifyEntity(@PathVariable("id") Integer id, Model model){
        Direccion direccion = direccionService.findById(id);

        List<EstadoMexicano> estadoList=estadoMexicanoService.findAll();

        model.addAttribute("direccion", direccion);
        model.addAttribute("contenido","Modificar Direccion");

        model.addAttribute("estadoList",estadoList);

        return "direccion_form";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") Integer id, RedirectAttributes flash){
        direccionService.delete(id);
        flash.addFlashAttribute("success","Se borró con éxito Direccion");
        return "redirect:/direccion/list";
    }


}
