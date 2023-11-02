package com.reparacionjava.cortes.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.management.relation.Role;
import javax.servlet.http.HttpSession;

import org.aspectj.apache.bcel.generic.InstructionConstants.Clinit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.reparacionjava.cortes.entity.Cliente;
import com.reparacionjava.cortes.entity.Orden;
import com.reparacionjava.cortes.servicio.IFacturaService;
import com.reparacionjava.cortes.servicio.IOrdenService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private IFacturaService facturaService;

	@Autowired
	private IOrdenService ordenService;

	@GetMapping("/compras")
	public String obtenerCompras(@PathVariable(name = "id", required = false)  Integer id, Model model, HttpSession session) {
		model.addAttribute("sesion");

		Cliente cliente = facturaService.buscarPorId(1);
		List<Orden> ordenes = ordenService.findByCliente(cliente);
		logger.info("ordenes {}", ordenes);

		model.addAttribute("ordenes", ordenes);

		return "usuario/compras";
	}

	@GetMapping("/detalle/{id}")
	public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model) {
		logger.info("Id de la orden: {}", id);
		Optional<Orden> orden = ordenService.findById(id);

		model.addAttribute("detalles", orden.get().getDetalle());

		model.addAttribute("sesion", session.getAttribute("idcliente"));
		return "usuario/detallecompra";
	}

	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		session.removeAttribute("idusuario");
		return "redirect:/";
	}
}