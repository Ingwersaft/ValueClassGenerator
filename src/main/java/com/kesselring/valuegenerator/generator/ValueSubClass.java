package com.kesselring.valuegenerator.generator;

import com.kesselring.valuegenerator.parsed.Type;
import com.kesselring.valuegenerator.parsed.Variable;

import java.util.Objects;
import java.util.StringJoiner;

public class ValueSubClass {
    private String className;
    private String nameValue;
    private String classType;

    public ValueSubClass(Variable variable) {
        this.classType = variable.getType().getClassName().getValue();
        this.nameValue = variable.getName().getValue();
        this.className = nameValue.substring(0, 1).toUpperCase() + nameValue.substring(1);
    }

    public static void main(String[] args) {
        System.out.println(new ValueSubClass(
                new Variable(new Type("java.lang.String"), new Variable.Name("testValue"))).createEqualsAndHash());
        System.out.println(Alive.of(Boolean.TRUE).equals(Alive.of(Boolean.TRUE)));
    }

    public String asString() {
        StringJoiner resultLineJointer = new StringJoiner("\n");
        resultLineJointer.add("public static final class " + className + " {");
        resultLineJointer.add("\tprivate final " + classType + " " + nameValue + ";");
        resultLineJointer.add("\tpublic " + classType + " get() {");
        resultLineJointer.add("\t\treturn " + nameValue + ";");
        resultLineJointer.add("\t}");
        resultLineJointer.add("\tpublic " + className + "(" + classType + " " + nameValue + ") {");
        resultLineJointer.add("\t\tthis." + nameValue + " = " + nameValue + ";");
        resultLineJointer.add("\t}");
        resultLineJointer.add(createToString());
        resultLineJointer.add(createOfMethode());
        resultLineJointer.add(createEqualsAndHash());
        resultLineJointer.add("}");
        String s = resultLineJointer.toString();
        return s;
    }

    private String createOfMethode() {
        String result =
                "\tpublic static " + this.className + " of(final " + classType + " " + nameValue + "){\n" +
                        "\t\treturn new " + this.className + "(" + nameValue + ");\n" +
                        "\t}\n";
        return result;
    }

    private String createToString() {
        return "@Override\n" +
                "        public String toString() {\n" +
                "            return \"" + this.className + ":\" + " + nameValue + ";\n" +
                "        }";
    }

    private String createEqualsAndHash() {
        return "        @Override\n" +
                "        public boolean equals(Object o) {\n" +
                "            if (this == o) return true;\n" +
                "            if (o == null || getClass() != o.getClass()) return false;\n" +
                "            " + this.className + " " + nameValue + "1 = (" + this.className + ") o;\n" +
                "            return java.util.Objects.equals(" + nameValue + ", " + nameValue + "1." + nameValue + ");\n" +
                "        }\n" +
                "\n" +
                "        @Override\n" +
                "        public int hashCode() {\n" +
                "            return java.util.Objects.hash(" + nameValue + ");\n" +
                "        }";
    }

    public static final class Alive {
        private final Boolean alive;

        public Alive(Boolean alive) {
            this.alive = alive;
        }

        public static Alive of(final Boolean alive) {
            return new Alive(alive);
        }

        public Boolean get() {
            return alive;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Alive alive1 = (Alive) o;
            return Objects.equals(alive, alive1.alive);
        }

        @Override
        public int hashCode() {
            return Objects.hash(alive);
        }
    }
}
