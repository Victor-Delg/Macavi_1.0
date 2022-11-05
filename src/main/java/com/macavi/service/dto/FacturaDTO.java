package com.macavi.service.dto;

import com.macavi.domain.enumeration.Pago;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.macavi.domain.Factura} entity.
 */
public class FacturaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String descripcion;

    @NotNull
    private ZonedDateTime fechaFactura;

    private ZonedDateTime fechaVencimiento;

    @NotNull
    private Pago tipoPago;

    @NotNull
    private Integer totalFactura;

    private ClienteDTO cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(ZonedDateTime fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public ZonedDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(ZonedDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Pago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(Pago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Integer getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Integer totalFactura) {
        this.totalFactura = totalFactura;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FacturaDTO)) {
            return false;
        }

        FacturaDTO facturaDTO = (FacturaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, facturaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FacturaDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaFactura='" + getFechaFactura() + "'" +
            ", fechaVencimiento='" + getFechaVencimiento() + "'" +
            ", tipoPago='" + getTipoPago() + "'" +
            ", totalFactura=" + getTotalFactura() +
            ", cliente=" + getCliente() +
            "}";
    }
}
