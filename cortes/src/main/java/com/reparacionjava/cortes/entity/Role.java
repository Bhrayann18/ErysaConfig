package com.reparacionjava.cortes.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "authorities"/*
                            * , uniqueConstraints = { @UniqueConstraint(columnNames = { "cliente_id",
                            * "authority" }) }
                            */)
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String authority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Role(Integer id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public Role() {

    }

}
