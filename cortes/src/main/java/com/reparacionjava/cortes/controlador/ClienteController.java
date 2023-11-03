package com.reparacionjava.cortes.controlador;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reparacionjava.cortes.entity.Cliente;
import com.reparacionjava.cortes.servicio.IFacturaService;
import com.reparacionjava.cortes.servicio.IUploadFileService;
import com.reparacionjava.cortes.util.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IFacturaService facturaService;

	@GetMapping({ "/ver/{id}" })
	public String ver(@PathVariable(value = "id") Integer id, Model model, RedirectAttributes flash) {

		Cliente cliente = facturaService.fetchByIdWithFacturas(id);

		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}

		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());

		return "cliente/ver";

	}

	@GetMapping({ "/listar" })
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
			HttpServletRequest request) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Cliente> clientes = facturaService.findAll(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);

		return "cliente/listar";
	}

	@GetMapping({ "/formFactura" })
	public String crear(Map<String, Object> map) {
		Cliente cliente = new Cliente();
		map.put("titulo", "Formulario de Cliente");
		map.put("cliente", cliente);
		return "cliente/formFactura";
	}

	/*
	 * @Secured("ROLE_ADMIN")
	 * 
	 * @PostMapping({ "/form" }) public String crear(@Valid Cliente cliente,
	 * BindingResult result, Model model,
	 * 
	 * @RequestParam("file") MultipartFile foto, RedirectAttributes flash,
	 * SessionStatus status) {
	 * 
	 * String uniqueFilename = null;
	 * 
	 * if (result.hasErrors()) { model.addAttribute("titulo",
	 * "Formulario de Cliente"); return "cliente/form"; }
	 * 
	 * if (!foto.isEmpty()) {
	 * 
	 * if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() !=
	 * null && cliente.getFoto().length() > 0) {
	 * 
	 * uploadFileService.delete(cliente.getFoto());
	 * 
	 * }
	 * 
	 * try { uniqueFilename = uploadFileService.copy(foto); } catch (IOException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * flash.addFlashAttribute("info", "Ha subido correctamente '" + uniqueFilename
	 * + "'"); cliente.setFoto(uniqueFilename); }
	 * 
	 * String messageFlash = (cliente.getId() != null) ? "Cliente editado con exito"
	 * : "Cliente creado con exito"; facturaService.save(cliente);
	 * 
	 * status.setComplete(); flash.addFlashAttribute("success", messageFlash);
	 * return "redirect:/listar"; }
	 */

	@GetMapping({ "/formFactura/{id}" })
	public String modificar(@PathVariable(value = "id") Integer id, Map<String, Object> map, RedirectAttributes flash) {

		Cliente cliente = null;
		if (id > 0) {
			cliente = facturaService.buscarPorId(id);
			if (cliente == null) {
				flash.addFlashAttribute("warning", "El ID del cliente no existe en la BBDD");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("danger", "El ID del cliente no existe!");
			return "redirect:/listar";
		}

		map.put("cliente", cliente);
		map.put("titulo", "Editar cliente");

		return "cliente/formFactura";
	}

	/*
	 * @Secured({ "ROLE_ADMIN" })
	 * 
	 * @GetMapping({ "/eliminarFactura/{id}" }) public String
	 * eliminar(@PathVariable(value = "id") Integer id, RedirectAttributes flash) {
	 * 
	 * if (id > 0) { Cliente cliente = facturaService.findById(id).get();
	 * facturaService.delete(id); if (uploadFileService.delete(cliente.getFoto())) {
	 * flash.addFlashAttribute("success", "Cliente eliminado con exito"); }
	 * 
	 * }
	 * 
	 * return "redirect:/listar"; }
	 */

	private boolean hasRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();

		if (context == null) {
			return false;
		}

		Authentication auth = context.getAuthentication();

		if (auth == null) {
			return false;
		}

		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

		return authorities.contains(new SimpleGrantedAuthority(role));

		// for (GrantedAuthority authority : authorities) {
		// if (role.equals(authority.getAuthority())) {
		//
		// logger.info("Hola ".concat(auth.getName().concat(" tu rol es:
		// ").concat(authority.getAuthority())));
		// return true;
		// }
		// }
		//
		// return false;
	}
}
