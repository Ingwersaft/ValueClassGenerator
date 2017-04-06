package com.kesselring.valuegenerator.generator;

import com.kesselring.valuegenerator.parsed.Variable;

import java.util.List;
import java.util.StringJoiner;

public class CreateValueClass {
    private List<Variable> variables;

    public CreateValueClass(List<Variable> variables) {
        this.variables = variables;
    }

    public String asString() {
        StringJoiner resultLineJointer = new StringJoiner("\n");

        return resultLineJointer.toString();
    }
}
