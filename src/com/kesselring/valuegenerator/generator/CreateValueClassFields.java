package com.kesselring.valuegenerator.generator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 *
 */
public class CreateValueClassFields {
    private List<Variable> variables;
    private PsiElementFactory factory;

    public CreateValueClassFields(List<Variable> variables, Project project) {
        this.variables = variables;
        this.factory = JavaPsiFacade.getInstance(project).getElementFactory();
    }

    public String asString() {
        StringJoiner lineJoiner = new StringJoiner("\n");
        variables.forEach(variable -> lineJoiner.add(
                "private " + determinClassName(variable) + " " + variable.getName().getValue() + ";"));
        return lineJoiner.toString();
    }

    public List<PsiElement> asPsi() {
        List<PsiElement> psiStatementList = variables.stream()
                .map(variable -> {
                    PsiType type = factory.createTypeFromText(determinClassName(variable), null);
                    PsiDeclarationStatement variableDeclarationStatement = factory.createVariableDeclarationStatement(variable.getName().getValue(), type, null);
                    PsiLocalVariable psiLocalVariable = (PsiLocalVariable) variableDeclarationStatement.getDeclaredElements()[0];
                    PsiModifierList modifierList = psiLocalVariable.getModifierList();
                    modifierList.setModifierProperty(PsiModifier.PRIVATE, Boolean.TRUE);
                    return variableDeclarationStatement;
                })
                .collect(Collectors.toList());
        return psiStatementList;
    }

    private String determinClassName(Variable variable) {
        if (Type.ALL_SUPPORTED_CLASSES_AND_PRIMITIVES.values().contains(variable.getType())) {
            return variable.getUppercasedName();
        }
        return variable.getType().getClassName().getValue();
    }
}
