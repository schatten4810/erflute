package org.dbflute.erflute.core.util;

import java.io.Serializable;

public class NameValue implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String value;

    public NameValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public NameValue clone() {
        try {
            return (NameValue) super.clone();
        } catch (final CloneNotSupportedException e) {}
        return null;
    }
}
