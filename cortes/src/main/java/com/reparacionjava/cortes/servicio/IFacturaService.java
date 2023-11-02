package com.reparacionjava.cortes.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.reparacionjava.cortes.controlador.dto.ClienteRegistroDTO;
import com.reparacionjava.cortes.entity.Cliente;
import com.reparacionjava.cortes.entity.Factura;
import com.reparacionjava.cortes.entity.Producto;

@Service
public interface IFacturaService {

	public List<Cliente> findAll();

	public Page<Cliente> findAll(Pageable pageable);

	public Cliente guardar(ClienteRegistroDTO clienteRegistroDTO);

	public Cliente buscarPorId(Integer id);

	Optional<Cliente> findById(Integer id);

	public void delete(Integer id);

	public List<Producto> findByNombre(String term);

	public void saveFactura(Factura factura);

	public Producto findProductoByid(Integer id);

	public Factura findFacturaById(Integer id);

	public void deleteFactura(Integer id);

	public Factura fetchByIdWithItemFacturaWithProducto(Integer id);

	public Cliente fetchByIdWithFacturas(Integer id);

}
