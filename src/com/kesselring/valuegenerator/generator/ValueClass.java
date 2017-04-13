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
        resultLineJointer.add(createEquals());
        resultLineJointer.add(createHashCode());
        String complete = resultLineJointer.toString();
        System.out.println(complete);
        return complete;
    }

    private String createHashCode() {
        StringJoiner variableHashParams = new StringJoiner(", ");
        variables.forEach(variable -> variableHashParams.add(variable.getName().getValue()));
        String s = variableHashParams.toString();
        return "@Override\n" +
                "    public int hashCode() {\n" +
                "        return Objects.hash(" +
                s +
                ");\n" +
                "    }";
    }

    public String createEquals() {
        String begin = "@Override\n" +
                "    public boolean equals(Object o) {\n" +
                "        if (this == o) return true;\n" +
                "        if (o == null || getClass() != o.getClass()) return false;\n" +
                "        " + sourceClass.getName() + " " + sourceClass.getLowerCasedName() + " = (" + sourceClass.getName() + ") o;\n" +
                "        return ";
        //
        StringJoiner equals = new StringJoiner("\n");
        variables.forEach(variable -> {
            equals.add("Objects.equals(" + variable.getName().getValue() + ", " + sourceClass.getLowerCasedName() + "." + variable.getName().getValue() + ") &&");
        });
        //
        String variables = equals.toString().substring(0, equals.length() - 3) + ";}";
        String s = begin + variables;
        return s;
    }

    public List<PsiElement> getGeneratedPsiElements(Project project) {
        PsiClass classFromText =
                JavaPsiFacade.getInstance(project).getElementFactory().createClassFromText(
                        new ValueClass(variables, sourceClass).asString(), null);
        classFromText.setName(sourceClass.getName());
        List<PsiElement> result = new ArrayList<>();
        Stream.of(classFromText.getAllFields()).forEach(psiField -> result.add(psiField));
        Stream.of(classFromText.getInnerClasses()).forEach(psiClass -> result.add(psiClass));
        Stream.of(classFromText.getMethods()).forEach(psiClass -> result.add(psiClass)); // getMethod contains all constructors
        return result;
    }
}
