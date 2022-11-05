package com.macavi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Locate.
 */
@Entity
@Table(name = "locate")
public class Locate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 75)
    @Column(name = "ciudad", length = 75, nullable = false)
    private String ciudad;

    @NotNull
    @Size(max = 75)
    @Column(name = "departamento", length = 75, nullable = false)
    private String departamento;

    @NotNull
    @Size(max = 75)
    @Column(name = "pais", length = 75, nullable = false)
    private String pais;

    @OneToMany(mappedBy = "locate")
    @JsonIgnoreProperties(value = { "facturas", "locate", "tipoDni" }, allowSetters = true)
    private Set<Cliente> clientes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Locate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public Locate ciudad(String ciudad) {
        this.setCiudad(ciudad);
        return this;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return this.departamento;
    }

    public Locate departamento(String departamento) {
        this.setDepartamento(departamento);
        return this;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPais() {
        return this.pais;
    }

    public Locate pais(String pais) {
        this.setPais(pais);
        return this;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Set<Cliente> getClientes() {
        return this.clientes;
    }

    public void setClientes(Set<Cliente> clientes) {
        if (this.clientes != null) {
            this.clientes.forEach(i -> i.setLocate(null));
        }
        if (clientes != null) {
            clientes.forEach(i -> i.setLocate(this));
        }
        this.clientes = clientes;
    }

    public Locate clientes(Set<Cliente> clientes) {
        this.setClientes(clientes);
        return this;
    }

    public Locate addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.setLocate(this);
        return this;
    }

    public Locate removeCliente(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.setLocate(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Locate)) {
            return false;
        }
        return id != null && id.equals(((Locate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Locate{" +
            "id=" + getId() +
            ", ciudad='" + getCiudad() + "'" +
            ", departamento='" + getDepartamento() + "'" +
            ", pais='" + getPais() + "'" +
            "}";
    }
}
