package com.reparacionjava.cortes.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "clientes") // esta anotación no es necesaria ponerla si la tabla se va a llamar igual que
// nuestra clase
public class Cliente implements Serializable {

	@Id // aquí le indicamos que es una clave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) // con esta anotación le indicamos que es una tabla
	// autoincremental
	private Integer id;

	@NotEmpty // con está anotación le decimos que no pueda ser vacío el campo (que venga un
	// string con length mayor de 0)
	private String nombre;

	@NotEmpty
	private String apellido;

	@NotEmpty
	private String numero_celular;

	@NotEmpty
	private String direccion;

	@Column(length = 25, unique = true)
	private String username;

	@NotEmpty
	@Email // con está anotación, le decimos que tiene que ser de email
	private String email;

	@Column(length = 150)
	private String password;

	private Boolean enabled;

	/*@NotNull // con está anotación validamos que el obejto no sea null
	@Column(name = "create_at") // la anotación column solamente necesitamos ponerla si vamos a hacer camel case
	// si el campo de la tabla se va a llamar extamente igual que en la clase, no
	// sería necesario
	@Temporal(TemporalType.DATE) // de esta forma lo convierte al formato propio del moto de nuestra base de
	// datos
	@DateTimeFormat(pattern = "dd-MM-yyyy") // Con está notación podemos especificar el formato de la fecha
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createAt;*/

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "cliente_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Factura> facturas;

	@OneToMany(mappedBy = "cliente")
	private List<Orden> ordenes;

	private String foto;

	public Cliente() {
		facturas = new ArrayList<Factura>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNumero_celular() {
		return numero_celular;
	}

	public void setNumero_celular(String numero_celular) {
		this.numero_celular = numero_celular;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/*public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}*/

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public List<Orden> getOrdenes() {
		return ordenes;
	}

	public void setOrdenes(List<Orden> ordenes) {
		this.ordenes = ordenes;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Role[] getAuthorities() {
		return null;
	}

	/*
	 * public Cliente(Integer id, @NotEmpty String nombre, @NotEmpty String
	 * apellido, @NotEmpty String numero_celular,
	 * 
	 * @NotEmpty String direccion, String username, @NotEmpty @Email String email,
	 * String password,
	 * List<Role> roles) {
	 * this.id = id;
	 * this.nombre = nombre;
	 * this.apellido = apellido;
	 * this.numero_celular = numero_celular;
	 * this.direccion = direccion;
	 * this.username = username;
	 * this.email = email;
	 * this.password = password;
	 * this.roles = roles;
	 * }
	 */

	public Cliente(@NotEmpty String nombre, @NotEmpty String apellido, @NotEmpty String numero_celular,
			@NotEmpty String direccion, String username, @NotEmpty @Email String email, String password,
			Collection<Role> roles) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.numero_celular = numero_celular;
		this.direccion = direccion;
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

}
