package com.kesselring.valuegenerator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaToken;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.kesselring.valuegenerator.generator.ValueClass;
import com.kesselring.valuegenerator.parsed.SourceClass;
import com.kesselring.valuegenerator.parsed.Variable;

import java.util.List;
import java.util.stream.Stream;

public class GeneratedValueWriter implements Runnable {
    private PsiClass sourceJavaPsiClass;
    private Project project;
    private List<Variable> extractedVariables;
    private SourceClass sourceClass;
    private PsiElement rootPsiFile;

    public GeneratedValueWriter(PsiClass sourceJavaPsiClass, Project project, List<Variable> extractedVariables, SourceClass sourceClass, PsiElement rootPsiFile) {
        this.sourceJavaPsiClass = sourceJavaPsiClass;
        this.project = project;
        this.extractedVariables = extractedVariables;
        this.sourceClass = sourceClass;
        this.rootPsiFile = rootPsiFile;
    }

    @Override
    public void run() {
        // deleting old fields
        Stream.of(sourceJavaPsiClass.getAllFields())
                .peek(psiField -> System.out.println("going to delete field: " + psiField.getText()))
                .forEach(psiField -> psiField.delete());

        // deleting orphanage COMMAs
        Stream.of(sourceJavaPsiClass.getChildren())
                .filter(psiElement -> psiElement instanceof PsiJavaToken)
                .map(psiElement -> (PsiJavaToken) psiElement)
                .filter(psiJavaToken -> "COMMA".equals(psiJavaToken.getTokenType().toString()))
                .peek(psiJavaToken -> System.out.println("going to delete token:" + psiJavaToken))
                .forEach(psiElement -> psiElement.delete());

        // start of additions
        new ValueClass(extractedVariables, sourceClass).getGeneratedPsiElements(project).forEach(
                psiElement -> sourceJavaPsiClass.add(psiElement)
        );
        CodeStyleManager.getInstance(project).reformat(rootPsiFile);
    }
}
