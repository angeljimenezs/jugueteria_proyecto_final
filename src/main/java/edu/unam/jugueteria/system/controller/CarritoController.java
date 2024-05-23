package edu.unam.jugueteria.system.controller;


import edu.unam.jugueteria.system.model.Carrito;
import edu.unam.jugueteria.system.model.Orden;
import edu.unam.jugueteria.system.model.Producto;
import edu.unam.jugueteria.system.service.CarritoService;
import edu.unam.jugueteria.system.service.OrdenService;
import edu.unam.jugueteria.system.service.ProductoService;
import edu.unam.jugueteria.system.util.RenderPagina;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "carrito")
public class CarritoController {
    @Autowired
    CarritoService carritoService;

    @Autowired
    ProductoService productoService;

    @Autowired
    OrdenService ordenService;

    @PostMapping("/addToCart/{pid}/{qty}")
    public String addOneToShoppingCartFromView(@PathVariable("pid") Integer productId,
                                               @PathVariable("qty") Integer quantity, RedirectAttributes flash) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();


        Producto producto = productoService.findById(productId);
        Orden orden = ordenService.findActiveOrder(userEmail);
        log.info("Controller: Intentanto agregar producto " + producto.getTitulo() + " con Orden: \n" + orden);
        Carrito carrito = new Carrito(null, 1, producto.getPrecio(), producto.getPrecio().multiply(BigDecimal.valueOf(1)),  producto, orden);
        carritoService.addOne(carrito);

        flash.addFlashAttribute("success","Se agregó al carrito " + carrito.getProducto().getTitulo());

        return "redirect:/compras";
    }

    @PostMapping("/add/{pid}/{qty}")
    public String addOneToShoppingCart(@PathVariable("pid") Integer productId,
                                    @PathVariable("qty") Integer quantity, RedirectAttributes flash) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();


        Producto producto = productoService.findById(productId);
        Orden orden = ordenService.findActiveOrder(userEmail);
        log.info("Controller: Intentanto agregar producto " + producto.getTitulo() + " con Orden: \n" + orden);
        Carrito carrito = new Carrito(null, 1, producto.getPrecio(), producto.getPrecio().multiply(BigDecimal.valueOf(1)),  producto, orden);
        carritoService.addOne(carrito);

        return "redirect:/carrito/list";
    }



    @PostMapping("/remove/{pid}/{qty}")
    public String removeOneToShoppingCart(@PathVariable("pid") Integer productId,
                                       @PathVariable("qty") Integer quantity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();

        Producto producto = productoService.findById(productId);
        Orden orden = ordenService.findActiveOrder(userEmail);
        List<Carrito> foundCarrito = carritoService.findAllByOrdenAndProduct(orden, producto);
        System.out.println("Intentanto remover producto " + producto + " con Orden: \n" + orden);
        System.out.println(foundCarrito);
        carritoService.removeOne(foundCarrito.get(0));
        return "redirect:/carrito/list";
    }

    @GetMapping("/list")
    public String listEntity(@RequestParam(name="page",defaultValue = "0")int page,
                             Model model){
        Pageable pagReq= PageRequest.of(page,5);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();

        Page<Carrito> carritos = carritoService
                .findAllByOrden(
                        ordenService.findActiveOrder(userEmail),
                        pagReq);

        // Obtener la lista de carritos desde el servicio
        List<Carrito> carritoList = carritoService.findAllByOrden(
                ordenService.findActiveOrder(userEmail)
        );
        // Calcular el acumulado total
        BigDecimal total = BigDecimal.ZERO;
        for (Carrito carrito : carritoList) {
            BigDecimal subtotal = carrito.getPrecioVenta().multiply(BigDecimal.valueOf(carrito.getCantidad()));
            total = total.add(subtotal);
        }

        RenderPagina<Carrito> render=new RenderPagina<>("list",carritos);
        model.addAttribute("carritos",carritos);
        model.addAttribute("total",total);
        model.addAttribute("page",render);
        model.addAttribute("contenido","Carrito");
        return "carrito_list";
    }


}
