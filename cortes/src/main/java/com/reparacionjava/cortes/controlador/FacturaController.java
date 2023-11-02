package com.reparacionjava.cortes.controlador;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.reparacionjava.cortes.dao.CategoriaDao;
import com.reparacionjava.cortes.entity.Categoria;
import com.reparacionjava.cortes.entity.Cliente;
import com.reparacionjava.cortes.entity.Factura;
import com.reparacionjava.cortes.entity.ItemFactura;
import com.reparacionjava.cortes.entity.Producto;
import com.reparacionjava.cortes.servicio.IFacturaService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {

	@Autowired
	private IFacturaService facturaService;

	@Autowired
	private CategoriaDao categoriaDao;

	private final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping({ "/ver/{id}" })
	public String ver(@PathVariable Integer id, Model model, RedirectAttributes flash) {

		Factura factura = facturaService.fetchByIdWithItemFacturaWithProducto(id); // facturaService.findFacturaById(id);

		if (factura == null) {
			flash.addFlashAttribute("danger", "La factura no existe en la base de datos");
			return "redirect:/listar";
		}

		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Factura: ".concat(factura.getDescripcion()));

		return "factura/ver";
	}

	@GetMapping({ "/form/{clienteId}" })
	public String crear(@PathVariable Integer clienteId, Model model, RedirectAttributes flash) {

		Cliente cliente = facturaService.buscarPorId(clienteId);

		List<Categoria> listarCategorias = categoriaDao.findAll();

		if (cliente == null) {
			flash.addFlashAttribute("danger", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}

		Factura factura = new Factura();
		factura.setCliente(cliente);

		model.addAttribute("listarCategorias", listarCategorias);
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Crear Factura");
		return "factura/form";
	}

	// @ResponseBody lo que haces es suprimir, evitar cargar una vista o sea un
	// .html y guardar en este caso
	// el json en el body del metodo

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		return facturaService.findByNombre(term);
	}

	@PostMapping("/form")
	public String guardar(@Valid Factura factura, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Integer[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Long[] cantidad, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Factura");
			return "factura/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Factura");
			model.addAttribute("error", "Error: La factura tiene que tener algún registro");
			return "factura/form";
		}

		for (int i = 0; i < itemId.length; i++) {
			Producto producto = facturaService.findProductoByid(itemId[i]);

			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			factura.addItemFactura(linea);

			log.info("ID: " + itemId[i].toString() + ", cantidad: " + cantidad[i].toString());
		}

		facturaService.saveFactura(factura);

		status.setComplete();

		flash.addFlashAttribute("success", "Factura creada con éxito");

		return "redirect:/ver/" + factura.getCliente().getId();
	}

	@GetMapping("eliminar/{id}")
	public String eliminar(@PathVariable Integer id, RedirectAttributes flash) {

		Factura factura = facturaService.findFacturaById(id);

		if (factura != null) {
			facturaService.deleteFactura(id);
			flash.addFlashAttribute("success", "La factura se ha eliminado correctamente");
			return "redirect:/ver/" + factura.getCliente().getId();
		}

		flash.addFlashAttribute("danger", "La factura no existe en la base de datos, no se pudo eliminar");
		return "redirect:/listar";
	}

}
