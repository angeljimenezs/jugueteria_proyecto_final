package edu.unam.jugueteria.auth.controller;

import edu.unam.jugueteria.auth.dto.RolDTO;
import edu.unam.jugueteria.auth.dto.UsuarioDTO;
import edu.unam.jugueteria.auth.exception.UsuarioNotFoundException;
import edu.unam.jugueteria.auth.service.UsuarioService;
import edu.unam.jugueteria.security.jwt.JWTTokenProvider;
import edu.unam.jugueteria.security.request.JwtRequest;
import edu.unam.jugueteria.security.request.LoginUserRequest;
import edu.unam.jugueteria.security.service.UserDetailsServiceImpl;
import edu.unam.jugueteria.system.model.Producto;
import edu.unam.jugueteria.system.service.AdminService;
import edu.unam.jugueteria.system.service.HomeService;
import edu.unam.jugueteria.system.service.ProductoService;
import edu.unam.jugueteria.system.service.UserService;
import edu.unam.jugueteria.system.util.RenderPagina;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Controller
public class HomeController {
	private final HomeService homeService;
	private final UserService userService;
	private final AdminService adminService;
	private final UsuarioService usuarioService;
	private final AuthenticationManager authenticationManager;
	private final JWTTokenProvider jwtTokenProvider;
	private final UserDetailsServiceImpl userDetailsService;

	@Autowired
	private  ProductoService productoService;

	// Controller Injection
	public HomeController(HomeService homeService, UserService userService, AdminService adminService, UsuarioService usuarioService,
						  AuthenticationManager authenticationManager, JWTTokenProvider jwtTokenProvider, UserDetailsServiceImpl userDetailsService) {
		this.homeService = homeService;
		this.userService = userService;
		this.adminService = adminService;
		this.usuarioService = usuarioService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.userDetailsService = userDetailsService;
	}

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("text", homeService.getText());
		return "home";
	}

	@GetMapping("/home")
	public String home1() {
		return "redirect:/";
	}

	@GetMapping("/index")
	public String index() {
		return "redirect:/";
	}

	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public String user(Model model) {
		model.addAttribute("text", userService.getText());
		return "user";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String admin(Model model) {
		model.addAttribute("text", adminService.getText());
		return "admin";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login_success_handler")
	public String loginSuccessHandler() {
		System.out.println("Logging user login success...");
		return "home";
		//return "index";
	}

	@PostMapping("/login_failure_handler")
	public String loginFailureHandler() {
		System.out.println("Login failure handler....");
		return "login";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new UsuarioDTO());
		return "signup_form";
	}

	@PostMapping("/process_register")
	public String processRegister(UsuarioDTO user) throws UsuarioNotFoundException {
		Set<RolDTO> roles = new HashSet<>();
		roles.add(RolDTO.builder().idRol(2).build()); // Aqui el USER es el integer 2
		user.setRoles(roles);
		usuarioService.save(user);
		return "register_success";
	}

	@PostMapping("/token")
	public String createAuthenticationToken(Model model, HttpSession session,
											@ModelAttribute LoginUserRequest loginUserRequest, HttpServletResponse res) throws Exception {
		log.info("LoginUserRequest {}", loginUserRequest);
		try {
			UsuarioDTO user = usuarioService.findByUserEmail(loginUserRequest.getUsername());

			Authentication authentication = authenticate(loginUserRequest.getUsername(),
					loginUserRequest.getPassword());
			log.info("authentication {}", authentication);
			String jwtToken = jwtTokenProvider.generateJwtToken(authentication, user);
			log.info("jwtToken {}", jwtToken);
			JwtRequest jwtRequest = new JwtRequest(jwtToken, user.getIdUsuario(), user.getEmail(),
					jwtTokenProvider.getExpiryDuration(), authentication.getAuthorities());
			log.info("jwtRequest {}", jwtRequest);
			Cookie cookie = new Cookie("token",jwtToken);
			cookie.setMaxAge(Integer.MAX_VALUE);
			res.addCookie(cookie);
			session.setAttribute("msg","Login OK!");

		} catch (UsernameNotFoundException | BadCredentialsException e) {
			session.setAttribute("msg","Bad Credentials");
			return "redirect:/login";
		}
		return "redirect:/index";
	}

	private Authentication authenticate(String username, String password) throws Exception {
		try {
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}


	@GetMapping("/compras")
	public String compras(@RequestParam(name="page",defaultValue = "0")int page, Model model) {

		Pageable pagReq= PageRequest.of(page,6);
		Page<Producto> productos = productoService.findAll(pagReq);
		RenderPagina<Producto> render=new RenderPagina<>("compras",productos);
		model.addAttribute("productos",productos);
		model.addAttribute("page",render);

		return "compras";
	}


}
