package com.kesselring.valuegenerator.parsed;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Type {
    // only supported classes will be transformed to value subclasses
    public static final Map<String, Type> ALL_SUPPORTED_CLASSES_AND_PRIMITIVES = Stream.of(
            new AbstractMap.SimpleEntry<>("NA", new Type("java.lang.String")),
            new AbstractMap.SimpleEntry<>("int", new Type("java.lang.Integer")),
            new AbstractMap.SimpleEntry<>("boolean", new Type("java.lang.Boolean")),
            new AbstractMap.SimpleEntry<>("byte", new Type("java.lang.Byte")),
            new AbstractMap.SimpleEntry<>("char", new Type("java.lang.Character")),
            new AbstractMap.SimpleEntry<>("float", new Type("java.lang.Float")),
            new AbstractMap.SimpleEntry<>("long", new Type("java.lang.Long")),
            new AbstractMap.SimpleEntry<>("short", new Type("java.lang.Short")),
            new AbstractMap.SimpleEntry<>("double", new Type("java.lang.Double"))
    ).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()));

    private Package aPackage;
    private ClassName className;

    public Type(String canonicalName) {
        String primitivesHandled = convertPrimitiveToWrapper(canonicalName);
        String[] splitted = primitivesHandled.split("\\.");
        String packageValue = String.join(".", Arrays.asList(splitted).subList(0, splitted.length - 1));
        String className = splitted[splitted.length - 1];
        this.aPackage = new Package(packageValue);
        this.className = new ClassName(className);
    }

    private String convertPrimitiveToWrapper(String canonicalName) {
        if (!canonicalName.contains(".")) {
            System.out.println("going to handle primitive: " + canonicalName);
            List<String> primitiveEquivalents = ALL_SUPPORTED_CLASSES_AND_PRIMITIVES.entrySet().stream()
                    .filter(stringTypeEntry -> stringTypeEntry.getKey().equals(canonicalName))
                    .map(
                            stringTypeEntry -> stringTypeEntry.getValue().getaPackage().getValue()
                                    + "."
                                    + stringTypeEntry.getValue().getClassName().getValue())
                    .collect(Collectors.toList());
            if (primitiveEquivalents.size() != 1) {
                throw new IllegalStateException("no equivalent found: " + primitiveEquivalents);
            } else {
                return primitiveEquivalents.get(0);
            }
        } else {
            return canonicalName;
        }
    }

    private boolean isPrimitive(String canonicalName) {
        return false;
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
