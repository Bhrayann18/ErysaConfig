package com.reparacionjava.cortes.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.reparacionjava.cortes.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Integer> {

	@Query("select c from Cliente c left join fetch c.facturas f where c.id = ?1")
	public Cliente fetchByIdWithFacturas(Integer id);

}
