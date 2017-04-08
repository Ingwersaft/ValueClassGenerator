package com.kesselring.valuegenerator.generator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.kesselring.valuegenerator.parsed.SourceClass;
import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by mauer on 06.04.17.
 */
public class CreateConstructor {
    private SourceClass sourceClass;
    private List<Variable> variables;
    private PsiElementFactory factory;

    public CreateConstructor(SourceClass sourceClass, List<Variable> variables, Project project) {
        this.sourceClass = sourceClass;
        this.variables = variables;
        this.factory = JavaPsiFacade.getInstance(project).getElementFactory();
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
        System.out.println(new CreateConstructor(new SourceClass("User"), vars, null).asString());
    }

    public String asString() {
        StringJoiner resultLineJointer = new StringJoiner("\n");
        resultLineJointer.add(getConstructorLine());
        variables.stream().map(variable -> variable.getName().getValue())
                .forEach(name -> resultLineJointer.add("\tthis." + name + " = " + name + ";"));
        resultLineJointer.add("}");
        return resultLineJointer.toString();
    }

    public PsiElement asPsi() {
        StringJoiner resultJoiner = new StringJoiner("\n");
        resultJoiner.add("public class " + sourceClass.getName() + " {");
        resultJoiner.add(asString());
        resultJoiner.add("}");

        return factory.createClassFromText(resultJoiner.toString(), null).getInnerClasses()[0].getConstructors()[0];
    }

    private String getConstructorLine() {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder
                .append("public ")
                .append(sourceClass.getName())
                .append("(");
        StringJoiner constructorParams = new StringJoiner(", ");
        variables.stream().forEach(variable -> {
            String name = variable.getName().getValue();
            String nameUpperCased = name.substring(0, 1).toUpperCase() + name.substring(1);
            constructorParams.add(determinClassName(variable) + " " + name);
        });
        resultBuilder.append(constructorParams.toString());
        resultBuilder.append(
                ") {");
        return resultBuilder.toString();
    }

    private String determinClassName(Variable variable) {
        if (Type.ALL_SUPPORTED_CLASSES_AND_PRIMITIVES.values().contains(variable.getType())) {
            return variable.getUppercasedName();
        }
        return variable.getType().getClassName().getValue();
    }
}
