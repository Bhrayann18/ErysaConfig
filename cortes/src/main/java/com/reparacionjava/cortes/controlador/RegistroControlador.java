package com.reparacionjava.cortes.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.reparacionjava.cortes.controlador.dto.ClienteRegistroDTO;
import com.reparacionjava.cortes.servicio.IFacturaService;

@Controller
@RequestMapping("/registro")
public class RegistroControlador {

    private IFacturaService facturaService;

    public RegistroControlador(IFacturaService facturaService) {
        super();
        this.facturaService = facturaService;
    }

    @ModelAttribute("cliente")
	public ClienteRegistroDTO retornarNuevoUsuarioRegistroDTO() {
		return new ClienteRegistroDTO();
	}

    @GetMapping
    public String mostrarFormularioDeRegistro() {
        return "/registro";
    }

    @PostMapping
    public String registrarCuentaDeCliente(@ModelAttribute("cliente") ClienteRegistroDTO registroDTO) {
        facturaService.guardar(registroDTO);
        return "redirect:/registro?exito";
    }
}
