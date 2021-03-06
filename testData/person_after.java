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