package com.kesselring.valuegenerator;

public class Variable {

    private Type type;
    private Name name;

    public Variable(Type type, Name name) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public Name getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "type=" + type +
                ", name=" + name.getValue() +
                '}';
    }

    public static class Name {
        private String value;

        public String getValue() {
            return value;
        }

        public Name(String value) {
            this.value = value;
        }
    }
}
