package com.reparacionjava.cortes.servicio;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reparacionjava.cortes.controlador.dto.ClienteRegistroDTO;
import com.reparacionjava.cortes.dao.IClienteDao;
import com.reparacionjava.cortes.dao.IFacturaDao;
import com.reparacionjava.cortes.dao.IProductoDao;
import com.reparacionjava.cortes.entity.Cliente;
import com.reparacionjava.cortes.entity.Factura;
import com.reparacionjava.cortes.entity.Producto;
import com.reparacionjava.cortes.entity.Role;
import com.reparacionjava.cortes.repositorio.ClienteRepository;

import net.bytebuddy.asm.Advice.Return;

@Service
public class FacturaServiceImpl implements IFacturaService {

	@Autowired
	private IClienteDao clienteDao;

	@Autowired
	private IProductoDao productoDao;

	@Autowired
	private IFacturaDao facturaDao;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	public Cliente guardar(ClienteRegistroDTO registroDTO) {

		Cliente cliente = new Cliente(registroDTO.getNombre(), registroDTO.getApellido(),
				registroDTO.getNumero_celular(), registroDTO.getDireccion(), registroDTO.getUsername(),
				registroDTO.getEmail(), passwordEncoder.encode(registroDTO.getPassword()),
				Arrays.asList(new Role("ROLE_ADMIN")));
		return clienteRepository.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente buscarPorId(Integer id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Cliente> findById(Integer id) {
		return clienteRepository.findById(id);

	}

	@Override
	@Transactional
	public void delete(Integer id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findByNombre(String term) {
		return productoDao.findByNombre(term);
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoByid(Integer id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Integer id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteFactura(Integer id) {
		facturaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura fetchByIdWithItemFacturaWithProducto(Integer id) {
		return facturaDao.fetchByIdWithItemFacturaWithProducto(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente fetchByIdWithFacturas(Integer id) {
		return clienteDao.fetchByIdWithFacturas(id);
	}

}
