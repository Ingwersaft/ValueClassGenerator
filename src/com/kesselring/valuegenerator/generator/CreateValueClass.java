package com.kesselring.valuegenerator.generator;

import com.kesselring.valuegenerator.parsed.SourceClass;
import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;

import java.util.List;
import java.util.StringJoiner;

public class CreateValueClass {
    private List<Variable> variables;
    private SourceClass sourceClass;

    public CreateValueClass(List<Variable> variables, SourceClass sourceClass) {
        this.variables = variables;
        this.sourceClass = sourceClass;
    }

    public String asString() {
        StringJoiner resultLineJointer = new StringJoiner("\n");
        resultLineJointer.add(new CreateValueClassFields(variables).asString());
        resultLineJointer.add("");
        resultLineJointer.add(new CreateConstructor(sourceClass, variables).asString());
        resultLineJointer.add("");
        variables.stream().filter(variable -> Type.ALL_SUPPORTED_CLASSES.contains(variable.getType())).forEach(variable -> resultLineJointer.add(new CreateValueSubclass(variable).asString()));
        return resultLineJointer.toString();
    }
}
