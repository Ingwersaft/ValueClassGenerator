package com.kesselring.valuegenerator.parsed;

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

    public String getUppercasedName() {
        return name.getValue().substring(0, 1).toUpperCase() + name.getValue().substring(1);
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

        public String getUppercasedValue() {
            return value.substring(0, 1).toUpperCase() + value.substring(1);
        }

        public Name(String value) {
            this.value = value;
        }
    }
}
