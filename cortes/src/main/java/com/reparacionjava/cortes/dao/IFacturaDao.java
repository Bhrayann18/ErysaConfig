package com.reparacionjava.cortes.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.reparacionjava.cortes.entity.Factura;



public interface IFacturaDao extends CrudRepository<Factura, Integer> {
	@Query("SELECT f FROM Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto p WHERE f.id=?1")
	public Factura fetchByIdWithItemFacturaWithProducto(Integer id);
}
