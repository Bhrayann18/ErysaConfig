package com.reparacionjava.cortes.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reparacionjava.cortes.entity.Cliente;
import com.reparacionjava.cortes.entity.Orden;
import com.reparacionjava.cortes.entity.Usuario;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer> {
	List<Orden> findByCliente(Cliente cliente);
}
