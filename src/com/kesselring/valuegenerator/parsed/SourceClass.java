package com.kesselring.valuegenerator.parsed;

public class SourceClass {
    private String name;

    public SourceClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SourceClass{" +
                "name='" + name + '\'' +
                '}';
    }
}
