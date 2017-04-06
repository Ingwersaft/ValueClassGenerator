package com.kesselring.valuegenerator.generator;

import com.kesselring.valuegenerator.parsed.SourceClassName;
import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by mauer on 06.04.17.
 */
public class CreateConstructor {
    private SourceClassName sourceClassName;
    private List<Variable> variables;

    public CreateConstructor(SourceClassName sourceClassName, List<Variable> variables) {
        this.sourceClassName = sourceClassName;
        this.variables = variables;
    }

    public String asString() {
        StringJoiner resultLineJointer = new StringJoiner("\n");
        resultLineJointer.add(getConstructorLine());
        variables.stream().map(variable -> variable.getName().getValue())
                .forEach(name -> resultLineJointer.add("\tthis." + name + " = " + name + ";"));
        resultLineJointer.add("}");
        return resultLineJointer.toString();
    }

    private String getConstructorLine() {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder
                .append("public ")
                .append(sourceClassName.getName())
                .append("(");
        StringJoiner constructorParams = new StringJoiner(", ");
        variables.stream().forEach(variable -> {
            String name = variable.getName().getValue();
            String nameUpperCased = name.substring(0, 1).toUpperCase() + name.substring(1);
            constructorParams.add(nameUpperCased + " " + name);
        });
        resultBuilder.append(constructorParams.toString());
        resultBuilder.append(
                ") {");
        return resultBuilder.toString();
    }

    public static void main(String[] args) {
        List<Variable> vars = new ArrayList<>();
        vars.add(new Variable(new Type("java.lang.String"),
                new Variable.Name("name")));
        vars.add(new Variable(new Type("java.lang.String"),
                new Variable.Name("surname")));
        vars.add(new Variable(new Type("java.lang.String"),
                new Variable.Name("address")));
        vars.add(new Variable(new Type("java.lang.Integer"),
                new Variable.Name("age")));
        System.out.println(new CreateConstructor(new SourceClassName("User"), vars).asString());
    }
}