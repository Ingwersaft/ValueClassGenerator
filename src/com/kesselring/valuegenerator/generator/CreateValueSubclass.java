package com.kesselring.valuegenerator.generator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;

import java.util.StringJoiner;

public class CreateValueSubclass {
    private Variable variable;
    private Project project;
    private PsiElementFactory factory;

    public CreateValueSubclass(Variable variable, Project project) {
        this.variable = variable;
        this.project = project;
        this.factory = JavaPsiFacade.getInstance(project).getElementFactory();
    }

    public static void main(String[] args) {
        System.out.println(
                new CreateValueSubclass(new Variable(new Type("java.lang.String"),
                        new Variable.Name("name")), null).asString());
        System.out.println(
                new CreateValueSubclass(new Variable(new Type("java.lang.Integer"),
                        new Variable.Name("age")), null).asString());
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
        PsiClass psiClass = factory.createClassFromText(asString(), null);
        return psiClass.getInnerClasses()[0];
    }

    private String createOfMethode() {
        return "";
    }
}
