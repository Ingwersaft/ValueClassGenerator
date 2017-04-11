package com.kesselring.valuegenerator.generator;

import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;

import java.util.StringJoiner;

public class ValueSubClass {
    private Variable variable;
    private String className;
    private String nameValue;
    private String classType;

    public ValueSubClass(Variable variable) {
        this.variable = variable;
        this.classType = variable.getType().getClassName().getValue();
        this.nameValue = variable.getName().getValue();
        this.className = nameValue.substring(0, 1).toUpperCase() + nameValue.substring(1);
    }

    public static void main(String[] args) {
        System.out.println(new ValueSubClass(
                new Variable(new Type("java.lang.String"), new Variable.Name("testValue"))).asString());
    }

    public String asString() {
        StringJoiner resultLineJointer = new StringJoiner("\n");
        resultLineJointer.add("public static final class " + className + " {");
        resultLineJointer.add("\tprivate final " + classType + " " + nameValue + ";");
        resultLineJointer.add("\tpublic " + classType + " get() {");
        resultLineJointer.add("\t\treturn " + nameValue + ";");
        resultLineJointer.add("\t}");
        resultLineJointer.add("\tpublic " + className + "(" + classType + " " + nameValue + ") {");
        resultLineJointer.add("\t\tthis." + nameValue + " = " + nameValue + ";");
        resultLineJointer.add("\t}");
        resultLineJointer.add(createOfMethode());
        resultLineJointer.add("}");
        String s = resultLineJointer.toString();
        return s;
    }

    private String createOfMethode() {
        String result =
                "\tpublic static " + this.className + " of(final " + classType + " " + nameValue + "){\n" +
                        "\t\treturn new " + this.className + "(" + nameValue + ");\n" +
                        "\t}\n";
        return result;
    }
}
