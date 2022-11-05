package com.macavi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.macavi.domain.enumeration.Pago;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Factura.
 */
@Entity
@Table(name = "factura")
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "fecha_factura", nullable = false)
    private ZonedDateTime fechaFactura;

    @Column(name = "fecha_vencimiento")
    private ZonedDateTime fechaVencimiento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago", nullable = false)
    private Pago tipoPago;

    @NotNull
    @Column(name = "total_factura", nullable = false)
    private Integer totalFactura;

    @OneToMany(mappedBy = "factura")
    @JsonIgnoreProperties(value = { "factura", "producto" }, allowSetters = true)
    private Set<ProdFact> prodFacts = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "facturas", "locate", "tipoDni" }, allowSetters = true)
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Factura id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Factura descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getFechaFactura() {
        return this.fechaFactura;
    }

    public Factura fechaFactura(ZonedDateTime fechaFactura) {
        this.setFechaFactura(fechaFactura);
        return this;
    }

    public void setFechaFactura(ZonedDateTime fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public ZonedDateTime getFechaVencimiento() {
        return this.fechaVencimiento;
    }

    public Factura fechaVencimiento(ZonedDateTime fechaVencimiento) {
        this.setFechaVencimiento(fechaVencimiento);
        return this;
    }

    public void setFechaVencimiento(ZonedDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Pago getTipoPago() {
        return this.tipoPago;
    }

    public Factura tipoPago(Pago tipoPago) {
        this.setTipoPago(tipoPago);
        return this;
    }

    public void setTipoPago(Pago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Integer getTotalFactura() {
        return this.totalFactura;
    }

    public Factura totalFactura(Integer totalFactura) {
        this.setTotalFactura(totalFactura);
        return this;
    }

    public void setTotalFactura(Integer totalFactura) {
        this.totalFactura = totalFactura;
    }

    public Set<ProdFact> getProdFacts() {
        return this.prodFacts;
    }

    public void setProdFacts(Set<ProdFact> prodFacts) {
        if (this.prodFacts != null) {
            this.prodFacts.forEach(i -> i.setFactura(null));
        }
        if (prodFacts != null) {
            prodFacts.forEach(i -> i.setFactura(this));
        }
        this.prodFacts = prodFacts;
    }

    public Factura prodFacts(Set<ProdFact> prodFacts) {
        this.setProdFacts(prodFacts);
        return this;
    }

    public Factura addProdFact(ProdFact prodFact) {
        this.prodFacts.add(prodFact);
        prodFact.setFactura(this);
        return this;
    }

    public Factura removeProdFact(ProdFact prodFact) {
        this.prodFacts.remove(prodFact);
        prodFact.setFactura(null);
        return this;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Factura cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Factura)) {
            return false;
        }
        return id != null && id.equals(((Factura) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Factura{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaFactura='" + getFechaFactura() + "'" +
            ", fechaVencimiento='" + getFechaVencimiento() + "'" +
            ", tipoPago='" + getTipoPago() + "'" +
            ", totalFactura=" + getTotalFactura() +
            "}";
    }
}
