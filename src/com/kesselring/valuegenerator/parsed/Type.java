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

    @Override
    public String toString() {
        return "Type{" +
                "aPackage=" + aPackage.getValue() +
                ", className=" + className.getValue() +
                '}';
    }

    public static class Package {
        private String value;

        public String getValue() {
            return value;
        }

        public Package(String value) {
            this.value = value;
        }
    }

    private class ClassName {
        private String value;

        public String getValue() {
            return value;
        }

        public ClassName(String value) {
            this.value = value;
        }
    }
}
