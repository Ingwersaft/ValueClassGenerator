package com.kesselring.valuegenerator.generator;

import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 *
 */
public class ValueGetter {
    private List<Variable> variables;

    public ValueGetter(List<Variable> variables) {
        this.variables = variables;
    }

    public static void main(String[] args) {
        List<Variable> vars = new ArrayList<>();
        vars.add(new Variable(new Type("java.lang.String"), new Variable.Name("surname")));
        vars.add(new Variable(new Type("java.lang.Integer"), new Variable.Name("age")));
        vars.add(new Variable(new Type("java.lang.String"), new Variable.Name("name")));
        vars.add(new Variable(new Type("java.awt.SystemColor"), new Variable.Name("nonPrimitiveOrPrimitiveWrapper")));
        System.out.println(new ValueGetter(vars).asString());
    }

    public String asString() {
        StringJoiner joiner = new StringJoiner("\n");
        variables.forEach(variable -> {
            String type;
            if (Type.isSupportedPrimitiveOrWrapper(variable.getType())) {
                type = variable.getName().getUppercasedValue();
            } else {
                type = variable.getType().getClassName().getValue();
            }
            String fieldName = variable.getName().getValue();
            String upperCasedName = variable.getUppercasedName();
            joiner.add("public " + type + " get" + upperCasedName + "(){");
            joiner.add("    return " + fieldName + ";");
            joiner.add("}");
        });
        return joiner.toString();
    }
}
