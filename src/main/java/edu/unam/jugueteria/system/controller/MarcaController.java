package edu.unam.jugueteria.system.controller;

import edu.unam.jugueteria.system.model.Marca;
import edu.unam.jugueteria.system.service.MarcaService;
import edu.unam.jugueteria.system.util.RenderPagina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "marca")
public class MarcaController {
    @Autowired
    MarcaService marcaService;

    @GetMapping("/register")
    public String registerEntity(Model model) {
        Marca marca = new Marca();
        model.addAttribute("marca", marca);
        return "marca_form";
    }

    @PostMapping("/save")
    public String saveEntity(Marca marca) {
        marcaService.save(marca);
        return "home";
    }

    @GetMapping("/list")
    public String listEntity(@RequestParam(name="page",defaultValue = "0")int page,
                             Model model){
        Pageable pagReq= PageRequest.of(page,2);
        Page<Marca> marcas=marcaService.findAll(pagReq);
        RenderPagina<Marca> render=new RenderPagina<>("list",marcas);
        model.addAttribute("marcas",marcas);
        model.addAttribute("page",render);
        model.addAttribute("contenido","Lista de Marca");
        return "marca_list";
    }

    @GetMapping("/modify/{id}")
    public String modifyEntity(@PathVariable("id") Integer id, Model model){
        Marca marca = marcaService.findById(id);
        model.addAttribute("marca", marca);
        model.addAttribute("contenido","Modificar Marca");
        return "marca_form";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") Integer id, RedirectAttributes flash){
        marcaService.delete(id);
        flash.addFlashAttribute("success","Se borró con éxito Marca");
        return "redirect:/marca/list";
    }


}
