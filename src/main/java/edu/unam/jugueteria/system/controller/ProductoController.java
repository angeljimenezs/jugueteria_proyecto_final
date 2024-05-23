package edu.unam.jugueteria.system.controller;

import edu.unam.jugueteria.system.model.Categoria;
import edu.unam.jugueteria.system.model.Marca;
import edu.unam.jugueteria.system.model.Producto;
import edu.unam.jugueteria.system.service.CategoriaService;
import edu.unam.jugueteria.system.service.MarcaService;
import edu.unam.jugueteria.system.service.ProductoService;
import edu.unam.jugueteria.system.util.RenderPagina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "producto")
public class ProductoController {
    @Autowired
    ProductoService productoService;

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    MarcaService marcaService;

    @GetMapping("/compras")
    public String compras(@RequestParam(name="page",defaultValue = "0")int page, Model model) {

        Pageable pagReq= PageRequest.of(page,6);
        Page<Producto> productos = productoService.findAll(pagReq);
        RenderPagina<Producto> render=new RenderPagina<>("compras",productos);
        model.addAttribute("productos",productos);
        model.addAttribute("page",render);

        return "compras";
    }

    @GetMapping("/register")
    public String registerEntity(Model model) {
        Producto producto = new Producto();
        List<Marca> marcaList=marcaService.findAll();
        List<Categoria> categoriaList= categoriaService.findAll();

        model.addAttribute("producto", producto);
        model.addAttribute("marcaList",marcaList);
        model.addAttribute("categoriaList",categoriaList);

        return "producto_form";
    }

    @PostMapping("/save")
    public String saveEntity(Producto producto) {
        //List<Categoria> selectedCategoria= categoriaService.findAll();
        //List<Marca> selectedMarca=marcaService.findAll();
        productoService.save(producto);
        return "home";
    }

    @GetMapping("/list")
    public String listEntity(@RequestParam(name="page",defaultValue = "0")int page,
                             Model model){
        Pageable pagReq= PageRequest.of(page,2);
        Page<Producto> productos = productoService.findAll(pagReq);
        RenderPagina<Producto> render=new RenderPagina<>("list",productos);
        model.addAttribute("productos",productos);
        model.addAttribute("page",render);
        model.addAttribute("contenido","Lista de Producto");
        return "producto_list";
    }

    @GetMapping("/modify/{id}")
    public String modifyEntity(@PathVariable("id") Integer id, Model model){
        Producto producto = productoService.findById(id);

        List<Marca> marcaList=marcaService.findAll();
        List<Categoria> categoriaList= categoriaService.findAll();

        model.addAttribute("producto", producto);
        model.addAttribute("contenido","Modificar Producto");

        model.addAttribute("marcaList",marcaList);
        model.addAttribute("categoriaList",categoriaList);

        return "producto_form";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") Integer id, RedirectAttributes flash){
        productoService.delete(id);
        flash.addFlashAttribute("success","Se borró con éxito Producto");
        return "redirect:/producto/list";
    }


}
