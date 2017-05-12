# ValueClassGenerator
[![Build Status](https://travis-ci.org/Ingwersaft/ValueClassGenerator.svg?branch=master)](https://travis-ci.org/Ingwersaft/ValueClassGenerator)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1189fda83a6d4621adb76ea485ce10a1)](https://www.codacy.com/app/Ingwersaft/ValueClassGenerator?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Ingwersaft/ValueClassGenerator&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/1189fda83a6d4621adb76ea485ce10a1)](https://www.codacy.com/app/Ingwersaft/ValueClassGenerator?utm_source=github.com&utm_medium=referral&utm_content=Ingwersaft/ValueClassGenerator&utm_campaign=Badge_Coverage)

IntelliJ IDEA Plugin that helps generating Value classes without the need to add any libs to your build 
(like google autovalue or project lombok).

*Features*

*done*
 * primitives/primitive wrapper will be subclassed
 * equals/hashcode/toString
 
*soon™*
 * see enhancement issues

# Usage

1. Create your data class and define your properties
2. right-click into the editor and click onto the `Generate value class` button

# Example
## Origin
````java
import java.awt.*;
import java.util.Objects;

public class Person {
    private String name;
    private String surname;
    private Integer age;
    private Boolean alive;
    private SystemColor nonPrimitiveOrPrimitiveWrapper;
}
````

## Result
````java
import java.awt.*;
import java.util.Objects;

public class Person {
    private final Name name;
    private final Surname surname;
    private final Age age;
    private final Alive alive;
    private final SystemColor nonPrimitiveOrPrimitiveWrapper;

    public Person(Name name, Surname surname, Age age, Alive alive, SystemColor nonPrimitiveOrPrimitiveWrapper) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.alive = alive;
        this.nonPrimitiveOrPrimitiveWrapper = nonPrimitiveOrPrimitiveWrapper;
    }

    public Name getName() {
        return name;
    }

    public Surname getSurname() {
        return surname;
    }

    public Age getAge() {
        return age;
    }

    public Alive getAlive() {
        return alive;
    }

    public SystemColor getNonPrimitiveOrPrimitiveWrapper() {
        return nonPrimitiveOrPrimitiveWrapper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return java.util.Objects.equals(name, person.name) &&
                java.util.Objects.equals(surname, person.surname) &&
                java.util.Objects.equals(age, person.age) &&
                java.util.Objects.equals(alive, person.alive) &&
                java.util.Objects.equals(nonPrimitiveOrPrimitiveWrapper, person.nonPrimitiveOrPrimitiveWrapper);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, surname, age, alive, nonPrimitiveOrPrimitiveWrapper);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name:" + name.get() + "," +
                "surname:" + surname.get() + "," +
                "age:" + age.get() + "," +
                "alive:" + alive.get() + "," +
                "nonPrimitiveOrPrimitiveWrapper:" + nonPrimitiveOrPrimitiveWrapper + "," +
                "}";
    }

    public static final class Name {
        private final String name;

        public String get() {
            return name;
        }

        public Name(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Name:" + name;
        }

        public static Name of(final String name) {
            return new Name(name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Name name1 = (Name) o;
            return java.util.Objects.equals(name, name1.name);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(name);
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

        @Override
        public String toString() {
            return "Surname:" + surname;
        }

        public static Surname of(final String surname) {
            return new Surname(surname);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Surname surname1 = (Surname) o;
            return java.util.Objects.equals(surname, surname1.surname);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(surname);
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

        @Override
        public String toString() {
            return "Age:" + age;
        }

        public static Age of(final Integer age) {
            return new Age(age);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Age age1 = (Age) o;
            return java.util.Objects.equals(age, age1.age);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(age);
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

        @Override
        public String toString() {
            return "Alive:" + alive;
        }

        public static Alive of(final Boolean alive) {
            return new Alive(alive);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Alive alive1 = (Alive) o;
            return java.util.Objects.equals(alive, alive1.alive);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(alive);
        }
    }
}
````
