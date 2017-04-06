package com.kesselring.valuegenerator.generator;

import com.kesselring.valuegenerator.parsed.Variable;

import java.util.StringJoiner;

public class CreateValueSubclass {
    private Variable variable;

    public CreateValueSubclass(Variable variable) {
        this.variable = variable;
    }

    public String asString() {
        StringJoiner resultLineJointer = new StringJoiner("\n");
        String classType = variable.getType().getClassName().getValue();
        String nameValue = variable.getName().getValue();
        String className = nameValue.substring(0, 1).toUpperCase() + nameValue.substring(1);
        resultLineJointer.add("public static final class " + className + " {");
        resultLineJointer.add("\tprivate final " + classType + " " + nameValue + ";");
        resultLineJointer.add("\tpublic " + classType + " get() {");
        resultLineJointer.add("\t\treturn " + nameValue + ";");
        resultLineJointer.add("\t}");
        resultLineJointer.add("\tpublic " + className + "(" + classType + " " + nameValue + ") {");
        resultLineJointer.add("\t\tthis." + nameValue + " = " + nameValue + ";");
        resultLineJointer.add("\t}");
        resultLineJointer.add("}");
        return resultLineJointer.toString();
    }
}
