package com.kesselring.valuegenerator.parsed;

import java.util.*;
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
            new AbstractMap.SimpleEntry<>("double", new Type("java.lang.Double")),
            // also fix without package:
            new AbstractMap.SimpleEntry<>("String", new Type("java.lang.String")),
            new AbstractMap.SimpleEntry<>("Integer", new Type("java.lang.Integer")),
            new AbstractMap.SimpleEntry<>("Boolean", new Type("java.lang.Boolean")),
            new AbstractMap.SimpleEntry<>("Byte", new Type("java.lang.Byte")),
            new AbstractMap.SimpleEntry<>("Character", new Type("java.lang.Character")),
            new AbstractMap.SimpleEntry<>("Float", new Type("java.lang.Float")),
            new AbstractMap.SimpleEntry<>("Long", new Type("java.lang.Long")),
            new AbstractMap.SimpleEntry<>("Short", new Type("java.lang.Short")),
            new AbstractMap.SimpleEntry<>("Double", new Type("java.lang.Double"))
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

    public static Boolean isSupportedPrimitiveOrWrapper(Type type) {
        Set<String> supported = ALL_SUPPORTED_CLASSES_AND_PRIMITIVES.values()
                .stream()
                .map(t -> t.getClassName().getValue())
                .collect(Collectors.toSet());
        boolean result = supported.contains(type.className.getValue());
        System.out.println("isSupportedPrimitiveOrWrapper for " + type + ": " + result);
        return result;
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
                System.out.println("no equivalent found: " + primitiveEquivalents + " for canonicalName=" + canonicalName);
                return canonicalName;
            } else {
                String s = primitiveEquivalents.get(0);
                System.out.println("found: " + s);
                return s;
            }
        } else {
            return canonicalName;
        }
    }

    public Package getaPackage() {
        return aPackage;
    }

    public ClassName getClassName() {
        return className;
    }

//    public static Boolean is

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

        public Package(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
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

        public ClassName(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
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
