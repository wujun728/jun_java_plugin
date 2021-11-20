package com.jun.plugin.poi.test.excel.core.common;


public final class FieldRelation {
    private final String name;
    private final String declaringClass;

    public FieldRelation(String definedIn, String name) {
        this.name = name;
        this.declaringClass = definedIn;
    }

    public FieldRelation(Class definedIn, String name) {
        this(definedIn == null ? null : definedIn.getName(), name);
    }

    public String getName() {
        return this.name;
    }

    public String getDeclaringClass() {
        return this.declaringClass;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof FieldRelation) {
            final FieldRelation field = (FieldRelation)obj;
            if ((declaringClass == null && field.declaringClass != null)
                || (declaringClass != null && field.declaringClass == null)) {
                return false;
            }
            return name.equals(field.getName())
                && (declaringClass == null || declaringClass.equals(field.getDeclaringClass()));
        }
        return false;
    }

    public int hashCode() {
        return name.hashCode() ^ (declaringClass == null ? 0 : declaringClass.hashCode());
    }

    public String toString() {
        return (declaringClass == null ? "" : declaringClass + ".") + name;
    }
}