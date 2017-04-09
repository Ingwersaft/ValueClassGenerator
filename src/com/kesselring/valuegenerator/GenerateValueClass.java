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
import com.kesselring.valuegenerator.generator.CreateValueClass;
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

                List<Variable> variables = Stream.of(psiFile.getChildren())
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

                PsiClassImpl psiJavaClass = getRootClassUnderOperation(psiFile);

                PsiClass classFromText =
                        JavaPsiFacade.getInstance(project).getElementFactory().createClassFromText(
                                new CreateValueClass(variables, sourceClass, project).asString(), null);
                classFromText.setName(sourceClass.getName());
                System.out.println("classFromText.getInnerClasses####");
                System.out.println(classFromText.getText() + "\n\n\n");
                System.out.println("####");
                Stream.of(classFromText.getAllFields()).forEach(psiField -> System.out.println(psiField.getText()));
                System.out.println("#");
                Stream.of(classFromText.getConstructors()).forEach(psiMethod -> System.out.println(psiMethod.getText()));
                System.out.println("#");
                Stream.of(classFromText.getAllInnerClasses()).forEach(psiClass -> System.out.println(psiClass.getText()));
                System.out.println("####");

                if (psiJavaClass == null) return;
                Runnable runnable = () -> {
                    // deleting old fields
                    Stream.of(psiJavaClass.getAllFields())
                            .peek(psiField -> System.out.println("going to delete field: " + psiField.getText()))
                            .forEach(psiField -> psiField.delete());
                    System.out.println("after deletion:\n" + psiJavaClass.getText());
                    // deleting orphanage COMMAs
                    Stream.of(psiJavaClass.getChildren())
                            .filter(psiElement -> psiElement instanceof PsiJavaToken)
                            .map(psiElement -> (PsiJavaToken) psiElement)
                            .filter(psiJavaToken -> "COMMA" .equals(psiJavaToken.getTokenType().toString()))
                            .peek(psiJavaToken -> System.out.println("going to delete token:" + psiJavaToken))
                            .forEach(psiElement -> psiElement.delete());

                    System.out.println("start of additions:");

                    Stream.of(classFromText.getAllFields())
                            .forEach(psiField -> psiJavaClass.add(psiField));
                    Stream.of(classFromText.getConstructors())
                            .forEach(psiMethod -> psiJavaClass.add(psiMethod));
                    Stream.of(classFromText.getAllInnerClasses())
                            .forEach(psiClass -> psiJavaClass.add(psiClass));
                    // adding constructor und value subclasses
//                    new CreateValueClass(variables, sourceClass, project).asPsi()
//                            .stream()
//                            .peek(psiElement -> System.out.println("going to add:\n" + psiElement.getText()))
//                            .forEach(psiElement ->
//                                    psiJavaClass.add(psiElement));
//
//                    System.out.println("after createvalueclass additions:\n" + psiJavaClass.getText());
//                    // adding fields
//                    PsiMethod constructor = psiJavaClass.getConstructors()[0];
//                    List<PsiElement> valueClassFields = new CreateValueClassFields(variables, project).asPsi();
//                    valueClassFields
//                            .forEach(psiElement -> {
//                                psiJavaClass.addBefore(psiElement, constructor);
//                            });
//                    System.out.println("after field additions:\n" + psiJavaClass.getText());

                    CodeStyleManager.getInstance(project).reformat(psiFile);
                };
                WriteCommandAction.runWriteCommandAction(project, runnable);
            }
        });
    }

    protected GenerateValueClass(EditorActionHandler defaultHandler) {
        super(defaultHandler);
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

    public static String getTestClass() {
        return "public class Person {\n" +
                "    private Name name;\n" +
                "    private Surname surname;\n" +
                "    private Age age;\n" +
                "    private Alive alive;\n" +
                "    private SystemColor nonPrimitiveOrPrimitiveWrapper;\n" +
                "\n" +
                "    public Person(Name name, Surname surname, Age age, Alive alive, SystemColor nonPrimitiveOrPrimitiveWrapper) {\n" +
                "        this.name = name;\n" +
                "        this.surname = surname;\n" +
                "        this.age = age;\n" +
                "        this.alive = alive;\n" +
                "        this.nonPrimitiveOrPrimitiveWrapper = nonPrimitiveOrPrimitiveWrapper;\n" +
                "    }\n" +
                "\n" +
                "    public static final class Name {\n" +
                "        private final String name;\n" +
                "\n" +
                "        public String get() {\n" +
                "            return name;\n" +
                "        }\n" +
                "\n" +
                "        public Name(String name) {\n" +
                "            this.name = name;\n" +
                "        }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    public static final class Surname {\n" +
                "        private final String surname;\n" +
                "\n" +
                "        public String get() {\n" +
                "            return surname;\n" +
                "        }\n" +
                "\n" +
                "        public Surname(String surname) {\n" +
                "            this.surname = surname;\n" +
                "        }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    public static final class Age {\n" +
                "        private final Integer age;\n" +
                "\n" +
                "        public Integer get() {\n" +
                "            return age;\n" +
                "        }\n" +
                "\n" +
                "        public Age(Integer age) {\n" +
                "            this.age = age;\n" +
                "        }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    public static final class Alive {\n" +
                "        private final Boolean alive;\n" +
                "\n" +
                "        public Boolean get() {\n" +
                "            return alive;\n" +
                "        }\n" +
                "\n" +
                "        public Alive(Boolean alive) {\n" +
                "            this.alive = alive;\n" +
                "        }\n" +
                "\n" +
                "    }\n" +
                "}";
    }
}
