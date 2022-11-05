package com.macavi.domain.enumeration;

/**
 * The Pago enumeration.
 */
public enum Pago {
    COUNTED("contado"),
    CREDIT("credito");

    private final String value;

    Pago(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
