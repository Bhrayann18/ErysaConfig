package com.reparacionjava.cortes.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.reparacionjava.cortes.entity.Producto;

/** Se toma la id de la entidad y su tipo que en este caso es Integer */
@Repository
public interface IProductoRepository extends PagingAndSortingRepository<Producto, Integer> {

	@Query("SELECT p FROM Producto p WHERE p.nombre like %?1%")
	public List<Producto> findByNombre(String term);
}
