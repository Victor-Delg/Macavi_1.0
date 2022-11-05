package com.macavi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.macavi.domain.Locate} entity.
 */
public class LocateDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 75)
    private String ciudad;

    @NotNull
    @Size(max = 75)
    private String departamento;

    @NotNull
    @Size(max = 75)
    private String pais;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocateDTO)) {
            return false;
        }

        LocateDTO locateDTO = (LocateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, locateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocateDTO{" +
            "id=" + getId() +
            ", ciudad='" + getCiudad() + "'" +
            ", departamento='" + getDepartamento() + "'" +
            ", pais='" + getPais() + "'" +
            "}";
    }
}
