package com.kesselring.valuegenerator.generator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;

import java.util.StringJoiner;

public class CreateValueSubclass {
    private Variable variable;
    private Project project;

    public CreateValueSubclass(Variable variable, Project project) {
        this.variable = variable;
        this.project = project;
    }

    public String asString() {
        StringJoiner resultLineJointer = new StringJoiner("\n");
        String classType = variable.getType().getClassName().getValue();
        String nameValue = variable.getName().getValue();
        String className = nameValue.substring(0, 1).toUpperCase() + nameValue.substring(1);
        resultLineJointer.add("public static final class " + className + " {");
        resultLineJointer.add("\tprivate final " + classType + " " + nameValue + ";");
        resultLineJointer.add("\tpublic " + classType + " get() {");
        resultLineJointer.add("\t\treturn " + nameValue + ";");
        resultLineJointer.add("\t}");
        resultLineJointer.add("\tpublic " + className + "(" + classType + " " + nameValue + ") {");
        resultLineJointer.add("\t\tthis." + nameValue + " = " + nameValue + ";");
        resultLineJointer.add("\t}");
        resultLineJointer.add(createOfMethode());
        resultLineJointer.add("}");
        return resultLineJointer.toString();
    }

    public PsiClass asPsi() {
        try {
            PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();

            PsiClass resultingValueSubClass = factory.createClass(variable.getName().getUppercasedValue());
            System.out.println(resultingValueSubClass);
            PsiType psiType = factory.createTypeByFQClassName(variable.getType().getaPackage().getValue() + "." + variable.getType().getClassName().getValue());
            System.out.println(psiType);
            PsiVariable variableDeclarationStatement = factory.createField(variable.getName().getValue(), psiType);
//                    factory.createVariableDeclarationStatement(variable.getName().getValue(), psiType, null);
            System.out.println(variableDeclarationStatement);
            resultingValueSubClass.add(variableDeclarationStatement);
            System.out.println(resultingValueSubClass);
            return resultingValueSubClass;
        } catch (IncorrectOperationException e) {
            System.out.println(e.getClass());
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private String createOfMethode() {
        return "";
    }

    public static void main(String[] args) {
        System.out.println(
                new CreateValueSubclass(new Variable(new Type("java.lang.String"),
                        new Variable.Name("name")), null).asString());
        System.out.println(
                new CreateValueSubclass(new Variable(new Type("java.lang.Integer"),
                        new Variable.Name("age")), null).asString());
    }
}
