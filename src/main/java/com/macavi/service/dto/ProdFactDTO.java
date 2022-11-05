package com.macavi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.macavi.domain.ProdFact} entity.
 */
public class ProdFactDTO implements Serializable {

    private Long id;

    private FacturaDTO factura;

    private ProductoDTO producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FacturaDTO getFactura() {
        return factura;
    }

    public void setFactura(FacturaDTO factura) {
        this.factura = factura;
    }

    public ProductoDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoDTO producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProdFactDTO)) {
            return false;
        }

        ProdFactDTO prodFactDTO = (ProdFactDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, prodFactDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProdFactDTO{" +
            "id=" + getId() +
            ", factura=" + getFactura() +
            ", producto=" + getProducto() +
            "}";
    }
}
