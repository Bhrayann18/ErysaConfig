package com.reparacionjava.cortes.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reparacionjava.cortes.entity.Categoria;



/** Se toma la id de la entidad y su tipo que en este caso es Long */
public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {

}
