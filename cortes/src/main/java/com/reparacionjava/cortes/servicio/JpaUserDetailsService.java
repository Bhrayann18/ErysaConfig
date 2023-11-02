package com.reparacionjava.cortes.servicio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reparacionjava.cortes.dao.IClienteDao;
import com.reparacionjava.cortes.entity.Cliente;
import com.reparacionjava.cortes.entity.Role;
import com.reparacionjava.cortes.repositorio.ClienteRepository;

@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Cliente cliente = clienteRepository.findByUsername(username);

		if (cliente == null) {
			throw new UsernameNotFoundException("Usuario o password inválidos");
		}
		return new User(cliente.getUsername(), cliente.getPassword(), mapearAutoridadesRoles(cliente.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
	}

}
