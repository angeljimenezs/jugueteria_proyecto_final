package edu.unam.jugueteria.system.controller;

import edu.unam.jugueteria.system.model.Categoria;
import edu.unam.jugueteria.system.service.CategoriaService;
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
@RequestMapping(value = "categoria")
public class CategoriaController {
    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/register")
    public String registerEntity(Model model) {
        Categoria categoria = new Categoria();
        model.addAttribute("categoria", categoria);
        return "categoria_form";
    }

    @PostMapping("/save")
    public String saveEntity(Categoria categoria) {
        categoriaService.save(categoria);
        return "home";
    }

    @GetMapping("/list")
    public String listEntity(@RequestParam(name="page",defaultValue = "0")int page,
                             Model model){
        Pageable pagReq= PageRequest.of(page,2);
        Page<Categoria> categorias=categoriaService.findAll(pagReq);
        RenderPagina<Categoria> render=new RenderPagina<>("list",categorias);
        model.addAttribute("categorias",categorias);
        model.addAttribute("page",render);
        model.addAttribute("contenido","Lista de Categoria");
        return "categoria_list";
    }

    @GetMapping("/modify/{id}")
    public String modifyEntity(@PathVariable("id") Integer id, Model model){
        Categoria categoria = categoriaService.findById(id);
        model.addAttribute("categoria", categoria);
        model.addAttribute("contenido","Modificar Categoria");
        return "categoria_form";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") Integer id, RedirectAttributes flash){
        categoriaService.delete(id);
        flash.addFlashAttribute("success","Se borró con éxito Categoria");
        return "redirect:/categoria/list";
    }


}
