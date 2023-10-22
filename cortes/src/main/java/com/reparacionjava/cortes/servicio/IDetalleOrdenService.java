package com.reparacionjava.cortes.servicio;

import java.util.Optional;

import com.reparacionjava.cortes.entity.DetalleOrden;

public interface IDetalleOrdenService {

	DetalleOrden save(DetalleOrden detalleOrden);

	Optional<DetalleOrden> findById(Integer id);
}
