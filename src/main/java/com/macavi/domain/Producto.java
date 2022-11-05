package com.macavi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "talla", nullable = false)
    private Integer talla;

    @Column(name = "porcentaje_iva")
    private Float porcentajeIva;

    @NotNull
    @Size(max = 20)
    @Column(name = "marca", length = 20, nullable = false)
    private String marca;

    @NotNull
    @Size(max = 20)
    @Column(name = "genero", length = 20, nullable = false)
    private String genero;

    @NotNull
    @Size(max = 20)
    @Column(name = "estilo", length = 20, nullable = false)
    private String estilo;

    @NotNull
    @Size(max = 100)
    @Column(name = "descripcion_producto", length = 100, nullable = false)
    private String descripcionProducto;

    @NotNull
    @Column(name = "cantidad_producto", nullable = false)
    private Integer cantidadProducto;

    @OneToMany(mappedBy = "producto")
    @JsonIgnoreProperties(value = { "factura", "producto" }, allowSetters = true)
    private Set<ProdFact> prodFacts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Producto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTalla() {
        return this.talla;
    }

    public Producto talla(Integer talla) {
        this.setTalla(talla);
        return this;
    }

    public void setTalla(Integer talla) {
        this.talla = talla;
    }

    public Float getPorcentajeIva() {
        return this.porcentajeIva;
    }

    public Producto porcentajeIva(Float porcentajeIva) {
        this.setPorcentajeIva(porcentajeIva);
        return this;
    }

    public void setPorcentajeIva(Float porcentajeIva) {
        this.porcentajeIva = porcentajeIva;
    }

    public String getMarca() {
        return this.marca;
    }

    public Producto marca(String marca) {
        this.setMarca(marca);
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getGenero() {
        return this.genero;
    }

    public Producto genero(String genero) {
        this.setGenero(genero);
        return this;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstilo() {
        return this.estilo;
    }

    public Producto estilo(String estilo) {
        this.setEstilo(estilo);
        return this;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getDescripcionProducto() {
        return this.descripcionProducto;
    }

    public Producto descripcionProducto(String descripcionProducto) {
        this.setDescripcionProducto(descripcionProducto);
        return this;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public Integer getCantidadProducto() {
        return this.cantidadProducto;
    }

    public Producto cantidadProducto(Integer cantidadProducto) {
        this.setCantidadProducto(cantidadProducto);
        return this;
    }

    public void setCantidadProducto(Integer cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public Set<ProdFact> getProdFacts() {
        return this.prodFacts;
    }

    public void setProdFacts(Set<ProdFact> prodFacts) {
        if (this.prodFacts != null) {
            this.prodFacts.forEach(i -> i.setProducto(null));
        }
        if (prodFacts != null) {
            prodFacts.forEach(i -> i.setProducto(this));
        }
        this.prodFacts = prodFacts;
    }

    public Producto prodFacts(Set<ProdFact> prodFacts) {
        this.setProdFacts(prodFacts);
        return this;
    }

    public Producto addProdFact(ProdFact prodFact) {
        this.prodFacts.add(prodFact);
        prodFact.setProducto(this);
        return this;
    }

    public Producto removeProdFact(ProdFact prodFact) {
        this.prodFacts.remove(prodFact);
        prodFact.setProducto(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", talla=" + getTalla() +
            ", porcentajeIva=" + getPorcentajeIva() +
            ", marca='" + getMarca() + "'" +
            ", genero='" + getGenero() + "'" +
            ", estilo='" + getEstilo() + "'" +
            ", descripcionProducto='" + getDescripcionProducto() + "'" +
            ", cantidadProducto=" + getCantidadProducto() +
            "}";
    }
}
