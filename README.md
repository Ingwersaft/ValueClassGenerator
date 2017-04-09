# ValueClassGenerator
IntelliJ IDEA Plugin that helps generating Value classes without the need to add any libs to your build (like google autovalue)

:exclamation:WARNING: project is TBD, see [#2](/../../issues/2) and [#3](/../../issues/3) for main features

# Source
````java
public class Person {
    private String name;
    private String surname;
    private Integer age;
    private Boolean alive;
    private SystemColor nonPrimitiveOrPrimitiveWrapper;
}
````

# Result
````java
public class Person {
    private Name name;
    private Surname surname;
    private Age age;
    private Alive alive;
    private SystemColor nonPrimitiveOrPrimitiveWrapper;

    public Person(Name name, Surname surname, Age age, Alive alive, SystemColor nonPrimitiveOrPrimitiveWrapper) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.alive = alive;
        this.nonPrimitiveOrPrimitiveWrapper = nonPrimitiveOrPrimitiveWrapper;
    }

    public static final class Name {
        private final String name;

        public String get() {
            return name;
        }

        public Name(String name) {
            this.name = name;
        }

    }

    public static final class Surname {
        private final String surname;

        public String get() {
            return surname;
        }

        public Surname(String surname) {
            this.surname = surname;
        }

    }

    public static final class Age {
        private final Integer age;

        public Integer get() {
            return age;
        }

        public Age(Integer age) {
            this.age = age;
        }

    }

    public static final class Alive {
        private final Boolean alive;

        public Boolean get() {
            return alive;
        }

        public Alive(Boolean alive) {
            this.alive = alive;
        }

    }
}
````
