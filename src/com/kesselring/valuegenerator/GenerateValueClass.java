package com.kesselring.valuegenerator;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.impl.source.PsiClassImpl;
import com.intellij.psi.util.PsiUtilBase;
import com.kesselring.valuegenerator.generator.CreateValueSubclass;
import com.kesselring.valuegenerator.parsed.SourceClass;
import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenerateValueClass extends EditorAction {

    public GenerateValueClass() {
        super(new EditorActionHandler() {
            @Override
            protected void doExecute(Editor editor, @Nullable Caret caret, DataContext dataContext) {
                System.out.println("doExecute called: editor = [" + editor + "], caret = [" + caret + "], " +
                        "dataContext = [" + dataContext + "]");
                super.doExecute(editor, caret, dataContext);
                Project project = (Project) dataContext.getData(DataKeys.PROJECT.getName());
                PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);

                PsiClass className = Stream.of(psiFile.getChildren())
                        .peek(psiElement -> System.out.println(psiElement.getClass()))
                        .filter(psiElement -> psiElement instanceof PsiClass)
                        .peek(System.out::println)
                        .map(psiElement -> (PsiClass) psiElement)
                        .collect(Collectors.toList()).get(0);
                SourceClass sourceClass = new SourceClass(className.getQualifiedName());
                System.out.println("class found: " + sourceClass);
                List<Variable> elements = Stream.of(psiFile.getChildren())
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
//                System.out.println(new CreateValueClass(elements, sourceClass).asString());
                //
                List<PsiVariable> psiVariables = Stream.of(psiFile.getChildren())
                        .filter(psiElement -> psiElement instanceof PsiClassImpl)
                        .map(PsiElement::getChildren)
                        .flatMap(Arrays::stream)
                        .filter(psiElement -> psiElement instanceof PsiVariable)
                        .map(psiElement -> (PsiVariable) psiElement).collect(Collectors.toList());

                final PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        PsiClass createdValueSubClassPsi =
                                new CreateValueSubclass(new Variable(new Type("java.lang.String"), new Variable.Name("surname")), project).asPsi();

                        Optional<PsiClassImpl> javaClass = Stream.of(psiFile.getChildren())
                                .filter(psiElement -> psiElement instanceof PsiClassImpl)
                                .map(psiElement -> (PsiClassImpl) psiElement).findFirst();

                        javaClass.get().add(createdValueSubClassPsi);

                        CodeStyleManager.getInstance(project).reformat(javaClass.get());
                    }
                };
                WriteCommandAction.runWriteCommandAction(project, runnable);
            }
        });
    }

    protected GenerateValueClass(EditorActionHandler defaultHandler) {
        super(defaultHandler);
    }
}
