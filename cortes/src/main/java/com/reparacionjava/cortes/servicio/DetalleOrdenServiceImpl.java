package com.reparacionjava.cortes.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reparacionjava.cortes.entity.DetalleOrden;
import com.reparacionjava.cortes.repositorio.IDetalleOrdenRepository;



@Service
public class DetalleOrdenServiceImpl implements IDetalleOrdenService {

	@Autowired
	private IDetalleOrdenRepository detalleOrdenRepository;

	@Override
	public DetalleOrden save(DetalleOrden detalleOrden) {
		return detalleOrdenRepository.save(detalleOrden);
	}

	@Override
	public Optional<DetalleOrden> findById(Integer id) {
		return detalleOrdenRepository.findById(id);
	}
}