package com.macavi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.macavi.domain.Cliente} entity.
 */
public class ClienteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 60)
    private String direccion;

    @NotNull
    private Integer telefono;

    private LocateDTO locate;

    private TipoDniDTO tipoDni;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public LocateDTO getLocate() {
        return locate;
    }

    public void setLocate(LocateDTO locate) {
        this.locate = locate;
    }

    public TipoDniDTO getTipoDni() {
        return tipoDni;
    }

    public void setTipoDni(TipoDniDTO tipoDni) {
        this.tipoDni = tipoDni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClienteDTO)) {
            return false;
        }

        ClienteDTO clienteDTO = (ClienteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clienteDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteDTO{" +
            "id=" + getId() +
            ", direccion='" + getDireccion() + "'" +
            ", telefono=" + getTelefono() +
            ", locate=" + getLocate() +
            ", tipoDni=" + getTipoDni() +
            "}";
    }
}
