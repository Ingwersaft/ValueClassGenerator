package com.kesselring.valuegenerator.generator;

import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;

import java.util.List;
import java.util.StringJoiner;

/**
 *
 */
public class CreateValueClassFields {
    private List<Variable> variables;

    public CreateValueClassFields(List<Variable> variables) {
        this.variables = variables;
    }

    public String asString() {
        StringJoiner lineJoiner = new StringJoiner("\n");
        variables.stream().forEach(variable -> lineJoiner.add(
                "private " + determinClassName(variable) + " " + variable.getName().getValue() + ";"));
        return lineJoiner.toString();
    }

    private String determinClassName(Variable variable) {
        if (Type.ALL_SUPPORTED_CLASSES.contains(variable.getType())) {
            return variable.getUppercasedName();
        }
        return variable.getType().getClassName().getValue();
    }
}
