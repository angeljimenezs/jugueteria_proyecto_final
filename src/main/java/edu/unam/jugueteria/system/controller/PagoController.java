package edu.unam.jugueteria.system.controller;

import edu.unam.jugueteria.auth.model.Usuario;
import edu.unam.jugueteria.auth.service.UsuarioService;
import edu.unam.jugueteria.system.model.Pago;
import edu.unam.jugueteria.system.service.PagoService;
import edu.unam.jugueteria.system.util.RenderPagina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping(value = "/pago")
public class PagoController {
    @Autowired
    PagoService pagoService;

    @Autowired
    UsuarioService usuarioService;


    @GetMapping("/register")
    public String registerEntity(Model model) {
        Pago pago = new Pago();

        model.addAttribute("pago", pago);

        return "pago_form";
    }

    @PostMapping("/save")
    public String saveEntity(@AuthenticationPrincipal UserDetails userDetails,
                             Pago pago) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();

        Usuario searchedUsuario = usuarioService.findByEmail(userEmail);
        pago.setUsuario(searchedUsuario);

        pagoService.save(pago);
        return "home";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")

    public String listEntity(@RequestParam(name="page", defaultValue = "0")int page,
                             Model model
    ){
        Pageable pagReq= PageRequest.of(page,2);

        //String userEmail = userDetails.getUsername();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getPrincipal().toString();

        Page<Pago> pagos = pagoService.findAllByUserEmail(userEmail, pagReq);
        RenderPagina<Pago> render=new RenderPagina<>("list",pagos);
        model.addAttribute("pagos",pagos);
        model.addAttribute("page",render);
        model.addAttribute("contenido","Lista de Pago");
        return "pago_list";
    }

    @GetMapping("/modify/{id}")
    public String modifyEntity(@PathVariable("id") Integer id, Model model){
        Pago pago = pagoService.findById(id);


        model.addAttribute("pago", pago);
        model.addAttribute("contenido","Modificar Pago");

        return "pago_form";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") Integer id, RedirectAttributes flash){
        pagoService.delete(id);
        flash.addFlashAttribute("success","Se borró con éxito Pago");
        return "redirect:/pago/list";
    }


}
