package com.reparacionjava.cortes.servicio;

import java.util.List;
import java.util.Optional;

import com.reparacionjava.cortes.entity.Cliente;
import com.reparacionjava.cortes.entity.Orden;



public interface IOrdenService {

	List<Orden> findAll();

	Optional<Orden> findById(Integer id);

	Orden save(Orden orden);

	String generarNumeroOrden();

	List<Orden> findByCliente(Cliente cliente);

}
