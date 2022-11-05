package com.macavi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TipoDni.
 */
@Entity
@Table(name = "tipo_dni")
public class TipoDni implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 4)
    @Column(name = "siglas_dni", length = 4, nullable = false)
    private String siglasDni;

    @NotNull
    @Size(max = 50)
    @Column(name = "nombre_dni", length = 50, nullable = false)
    private String nombreDni;

    @OneToMany(mappedBy = "tipoDni")
    @JsonIgnoreProperties(value = { "facturas", "locate", "tipoDni" }, allowSetters = true)
    private Set<Cliente> clientes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TipoDni id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiglasDni() {
        return this.siglasDni;
    }

    public TipoDni siglasDni(String siglasDni) {
        this.setSiglasDni(siglasDni);
        return this;
    }

    public void setSiglasDni(String siglasDni) {
        this.siglasDni = siglasDni;
    }

    public String getNombreDni() {
        return this.nombreDni;
    }

    public TipoDni nombreDni(String nombreDni) {
        this.setNombreDni(nombreDni);
        return this;
    }

    public void setNombreDni(String nombreDni) {
        this.nombreDni = nombreDni;
    }

    public Set<Cliente> getClientes() {
        return this.clientes;
    }

    public void setClientes(Set<Cliente> clientes) {
        if (this.clientes != null) {
            this.clientes.forEach(i -> i.setTipoDni(null));
        }
        if (clientes != null) {
            clientes.forEach(i -> i.setTipoDni(this));
        }
        this.clientes = clientes;
    }

    public TipoDni clientes(Set<Cliente> clientes) {
        this.setClientes(clientes);
        return this;
    }

    public TipoDni addCliente(Cliente cliente) {
        this.clientes.add(cliente);
        cliente.setTipoDni(this);
        return this;
    }

    public TipoDni removeCliente(Cliente cliente) {
        this.clientes.remove(cliente);
        cliente.setTipoDni(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDni)) {
            return false;
        }
        return id != null && id.equals(((TipoDni) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDni{" +
            "id=" + getId() +
            ", siglasDni='" + getSiglasDni() + "'" +
            ", nombreDni='" + getNombreDni() + "'" +
            "}";
    }
}
