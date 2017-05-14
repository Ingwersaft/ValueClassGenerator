package com.kesselring.valuegenerator;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.util.PsiUtilBase;
import com.kesselring.valuegenerator.parsed.SourceClass;
import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenerateValueClassHandler extends EditorActionHandler {
    @Override
    protected void doExecute(Editor editor, @Nullable Caret caret, DataContext dataContext) {
        System.out.println("doExecute called: editor = [" + editor + "], caret = [" + caret + "], " +
                "dataContext = [" + dataContext + "]");
        super.doExecute(editor, caret, dataContext);

        Project project = (Project) dataContext.getData(DataKeys.PROJECT.getName());
        if (project == null) return;

        PsiFile rootPsiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        if (rootPsiFile == null) return;

        PsiClass sourceClassName = Stream.of(rootPsiFile.getChildren())
                .peek(psiElement -> System.out.println(psiElement.getClass()))
                .filter(psiElement -> psiElement instanceof PsiClass)
                .peek(System.out::println)
                .map(psiElement -> (PsiClass) psiElement)
                .collect(Collectors.toList()).get(0);
        SourceClass sourceClass = new SourceClass(sourceClassName.getName());

        List<Variable> extractedVariables = Stream.of(rootPsiFile.getChildren())
                .filter(psiElement -> psiElement instanceof PsiClassImpl)
                .map(PsiElement::getChildren)
                .flatMap(Arrays::stream)
                .filter(psiElement -> psiElement instanceof PsiVariable)
                .map(psiElement -> (PsiVariable) psiElement)
                .map(psiVariable -> new Variable(
                        new Type(psiVariable.getType().getCanonicalText()),
                        new Variable.Name(psiVariable.getName())))
                .peek(System.out::println)
                .collect(Collectors.toList());
        if (extractedVariables.isEmpty()) return;

        PsiClassImpl sourceJavaPsiClass = getRootClassUnderOperation(rootPsiFile);
        if (sourceJavaPsiClass == null) return;

        GeneratedValueWriter writeActionRunner = new GeneratedValueWriter(
                sourceJavaPsiClass, project, extractedVariables, sourceClass, rootPsiFile
        );
        WriteCommandAction.runWriteCommandAction(project, writeActionRunner);
    }

    @Nullable
    private static PsiClassImpl getRootClassUnderOperation(PsiFile psiFile) {
        Optional<PsiClassImpl> javaClass = Stream.of(psiFile.getChildren())
                .filter(psiElement -> psiElement instanceof PsiClassImpl)
                .map(psiElement -> (PsiClassImpl) psiElement).findFirst();
        if (!javaClass.isPresent()) {
            return null;
        }
        PsiClassImpl psiJavaClass = javaClass.get();
        return psiJavaClass;
    }
}
