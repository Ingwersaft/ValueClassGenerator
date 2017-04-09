package com.kesselring.valuegenerator.generator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.kesselring.valuegenerator.parsed.SourceClass;
import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class CreateValueClass {
    private List<Variable> variables;
    private SourceClass sourceClass;
    private Project project;

    public CreateValueClass(List<Variable> variables, SourceClass sourceClass, Project project) {
        this.variables = variables;
        this.sourceClass = sourceClass;
        this.project = project;
    }

    public String asString() {
        StringJoiner resultLineJointer = new StringJoiner("\n");
        resultLineJointer.add(new CreateValueClassFields(variables, project).asString());
        resultLineJointer.add("");
        resultLineJointer.add(new CreateConstructor(sourceClass, variables, project).asString());
        resultLineJointer.add("");
        variables.stream().filter(variable -> Type.ALL_SUPPORTED_CLASSES_AND_PRIMITIVES.values().contains(variable.getType()))
                .forEach(variable -> resultLineJointer.add(new CreateValueSubclass(variable, project).asString()));
        return resultLineJointer.toString();
    }

    public List<PsiElement> asPsi() {
        List<PsiElement> result = new ArrayList<>();
        result.add(new CreateConstructor(sourceClass, variables, project).asPsi());
        variables.stream().filter(variable -> Type.ALL_SUPPORTED_CLASSES_AND_PRIMITIVES.values().contains(variable.getType()))
                .peek(variable -> System.out.println("new CreateValueSubclass for: " + variable))
                .forEach(variable -> result.add(new CreateValueSubclass(variable, project).asPsi()));
        return result;
    }
}
