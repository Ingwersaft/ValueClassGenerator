package com.kesselring.valuegenerator.parsed;

public class SourceClassName {
    private String name;

    public SourceClassName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SourceClassName{" +
                "name='" + name + '\'' +
                '}';
    }
}
