package com.reparacionjava.cortes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.reparacionjava.cortes.entity.Producto;


public interface IProductoDao extends CrudRepository<Producto, Integer> {
	
	@Query("SELECT p FROM Producto p WHERE p.nombre like %?1%")
	public List<Producto> findByNombre(String term);

}
