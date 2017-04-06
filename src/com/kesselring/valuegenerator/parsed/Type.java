package com.kesselring.valuegenerator.parsed;

import java.util.Arrays;

public class Type {
    private Package aPackage;
    private ClassName className;

    public Type(String canonicalName) {
        if (!canonicalName.contains(".")) {
            throw new RuntimeException("canonicalName contains no dot??");
        }
        String[] splitted = canonicalName.split("\\.");
        String packageValue = String.join(".", Arrays.asList(splitted).subList(0, splitted.length - 1));
        String className = splitted[splitted.length - 1];
        this.aPackage = new Package(packageValue);
        this.className = new ClassName(className);
    }

    public Type(Package aPackage, ClassName className) {
        this.aPackage = aPackage;
        this.className = className;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public ClassName getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return "Type{" +
                "aPackage=" + aPackage.getValue() +
                ", className=" + className.getValue() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Type type = (Type) o;

        if (!getaPackage().equals(type.getaPackage())) return false;
        return getClassName().equals(type.getClassName());
    }

    @Override
    public int hashCode() {
        int result = getaPackage().hashCode();
        result = 31 * result + getClassName().hashCode();
        return result;
    }

    public static class Package {
        private String value;

        public String getValue() {
            return value;
        }

        public Package(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Package aPackage = (Package) o;

            return getValue().equals(aPackage.getValue());
        }

        @Override
        public int hashCode() {
            return getValue().hashCode();
        }
    }

    public static class ClassName {
        private String value;

        public String getValue() {
            return value;
        }

        public ClassName(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ClassName className = (ClassName) o;

            return getValue().equals(className.getValue());
        }

        @Override
        public int hashCode() {
            return getValue().hashCode();
        }
    }
}
