package com.kesselring.valuegenerator.generator;

import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.StringJoiner;

/**
 *
 */
public class ValueFields {
    private List<Variable> variables;
    private ValueClass.Access access;

    public ValueFields(List<Variable> variables, ValueClass.Access access) {
        this.variables = variables;
        this.access = access;
    }

    public String asString() {
        StringJoiner lineJoiner = new StringJoiner("\n");
        variables.forEach(variable -> lineJoiner.add(
                getAccessModificator() + " final " + determinClassName(variable) + " " + variable.getName().getValue() + ";"));
        return lineJoiner.toString();
    }

    @NotNull
    private String getAccessModificator() {
        switch (access) {
            case METHOD:
                return "private";
            case FIELD:
                return "public";
        }
        return "bug";
    }

    private String determinClassName(Variable variable) {
        if (Type.ALL_SUPPORTED_CLASSES_AND_PRIMITIVES.values().contains(variable.getType())) {
            return variable.getUppercasedName();
        }
        return variable.getType().getClassName().getValue();
    }
}
