package com.reparacionjava.cortes.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reparacionjava.cortes.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    public Cliente findByUsername(String username);
}