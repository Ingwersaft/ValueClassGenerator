package com.kesselring.valuegenerator.generator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.kesselring.valuegenerator.parsed.SourceClass;
import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class ValueClass {
    private List<Variable> variables;
    private SourceClass sourceClass;

    public ValueClass(List<Variable> variables, SourceClass sourceClass) {
        this.variables = variables;
        this.sourceClass = sourceClass;
    }

    public String asString() {
        StringJoiner resultLineJointer = new StringJoiner("\n");
        resultLineJointer.add(new ValueFields(variables).asString());
        resultLineJointer.add("");
        resultLineJointer.add(new Constructor(sourceClass, variables).asString());
        resultLineJointer.add("");
        variables.stream().filter(variable -> Type.ALL_SUPPORTED_CLASSES_AND_PRIMITIVES.values().contains(variable.getType()))
                .forEach(variable -> resultLineJointer.add(new ValueSubClass(variable).asString()));
        return resultLineJointer.toString();
    }

    public List<PsiElement> getGeneratedPsiElements(Project project) {
        PsiClass classFromText =
                JavaPsiFacade.getInstance(project).getElementFactory().createClassFromText(
                        new ValueClass(variables, sourceClass).asString(), null);
        classFromText.setName(sourceClass.getName());
        List<PsiElement> result = new ArrayList<>();
        Stream.of(classFromText.getAllFields()).forEach(psiField -> result.add(psiField));
        Stream.of(classFromText.getConstructors()).forEach(psiConstructor -> result.add(psiConstructor));
        Stream.of(classFromText.getInnerClasses()).forEach(psiClass -> result.add(psiClass));
        return result;
    }
}
