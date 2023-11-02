package com.reparacionjava.cortes.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.reparacionjava.cortes.entity.Producto;

/**
 * Aqui se determina la estructura para realizar los metodos de eliminar,
 * actualizar y listar
 */
public interface ProductoService {

	public List<Producto> findAll();

	public Optional<Producto> get(Integer id);

	public Page<Producto> findAll(Pageable pageable);

	public void save(Producto producto);

	public Producto findOne(Integer id);

	public void delete(Integer id);

}
