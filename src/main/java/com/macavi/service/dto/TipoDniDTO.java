package com.macavi.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.macavi.domain.TipoDni} entity.
 */
public class TipoDniDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 4)
    private String siglasDni;

    @NotNull
    @Size(max = 50)
    private String nombreDni;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiglasDni() {
        return siglasDni;
    }

    public void setSiglasDni(String siglasDni) {
        this.siglasDni = siglasDni;
    }

    public String getNombreDni() {
        return nombreDni;
    }

    public void setNombreDni(String nombreDni) {
        this.nombreDni = nombreDni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDniDTO)) {
            return false;
        }

        TipoDniDTO tipoDniDTO = (TipoDniDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tipoDniDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDniDTO{" +
            "id=" + getId() +
            ", siglasDni='" + getSiglasDni() + "'" +
            ", nombreDni='" + getNombreDni() + "'" +
            "}";
    }
}
