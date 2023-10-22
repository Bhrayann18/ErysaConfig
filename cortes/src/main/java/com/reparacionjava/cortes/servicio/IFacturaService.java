package com.reparacionjava.cortes.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.reparacionjava.cortes.entity.Cliente;
import com.reparacionjava.cortes.entity.Factura;
import com.reparacionjava.cortes.entity.Producto;
import com.reparacionjava.cortes.entity.Usuario;

@Service
public interface IFacturaService {

	public List<Cliente> findAll();

	public Page<Cliente> findAll(Pageable pageable);

	void save(Cliente cliente);

	public Cliente buscarPorId(Long id);

	Optional<Cliente> findById(Integer id);

	public void delete(Long id);

	public List<Producto> findByNombre(String term);

	public void saveFactura(Factura factura);

	public Producto findProductoByid(Long id);

	public Factura findFacturaById(Long id);

	public void deleteFactura(Long id);

	public Factura fetchByIdWithItemFacturaWithProducto(Long id);

	public Cliente fetchByIdWithFacturas(Long id);

}
