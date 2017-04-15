# ValueClassGenerator
[![Build Status](https://travis-ci.org/Ingwersaft/ValueClassGenerator.svg?branch=master)](https://travis-ci.org/Ingwersaft/ValueClassGenerator)

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(surname, person.surname) &&
                Objects.equals(age, person.age) &&
                Objects.equals(alive, person.alive) &&
                Objects.equals(nonPrimitiveOrPrimitiveWrapper, person.nonPrimitiveOrPrimitiveWrapper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, age, alive, nonPrimitiveOrPrimitiveWrapper);
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
            return Objects.equals(name, name1.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
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
            return Objects.equals(surname, surname1.surname);
        }

        @Override
        public int hashCode() {
            return Objects.hash(surname);
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
            return Objects.equals(age, age1.age);
        }

        @Override
        public int hashCode() {
            return Objects.hash(age);
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
            return Objects.equals(alive, alive1.alive);
        }

        @Override
        public int hashCode() {
            return Objects.hash(alive);
        }
    }
}
````
