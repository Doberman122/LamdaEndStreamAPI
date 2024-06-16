package assets;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Person {
    private String name;
    private int age;
    private double salary;
    private Department department;

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getAge() {return age;}

    public void setAge(int age) {this.age = age;}

    public double getSalary() {return salary;}

    public void setSalary(double salary) {this.salary = salary;}

    public Department getDepartment() {return department;}

    public void setDepartment(Department department) {this.department = department;}

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", department=" + department +
                '}';
    }

        /*
        Задача. Найти самого молодого сотрудника используя stream() - потоки.
        Сотрудника искать будем по возрасту, допустим зададим ограничение самого молодого
        сотрудника от 20 - 23 лет.
         */
    public static Optional<Person> findMostYoungestPerson(List<Person> people) {
          return people.stream()
                  .min((o1, o2) -> Integer.compare(o1.getAge(), o2.getAge()));
    }

    /*
    Задача. Найти департамент, в котором работает сотрудник с самой большой зарплатой.
     */
    public static Optional<Person> findMostExpensiveDepartment(List<Person> people) {
     return people.stream()
             .sorted((o1, o2) -> Double.compare(o2.getSalary(), o1.getSalary()))
             .findFirst();
    }

    /**
      Задача. Сгруппировать сотрудников по департаментам
     */
    static Map<Department, List<Person>> groupByDepartment(List<Person> people) {
       return people.stream().collect(Collectors.groupingBy(Person::getDepartment));
    }

    /**
     * Задача. Сгруппировать сотрудников по названиям департаментов
     */
    static Map<String, List<Person>> groupByDepartmentName(List<Person> people) {
        return people.stream().collect(Collectors.groupingBy(Person::getName));
    }

    /**
     * Задача. В каждом департаменте найти самого старшего сотрудника
     */
    static Map<String, Person> getDepartmentOldestPerson(List<Person> people) {
        return people.stream().collect(Collectors.toMap(
                Person::getName,
                p -> p,
                (a, b) -> {
                    if (a.getAge() > b.getAge()) {
                        return a;
                    }
                    return b;
                }
        ));
    }

    /**
     * *Найти сотрудников с минимальными зарплатами в своем отделе
     * (прим. можно реализовать в два запроса)
     */
    static List<Person> cheapPersonsInDepartment(List<Person> people) {
      return  people.stream().sorted((o1, o2) -> Double.compare(o2.getSalary(), o1.getSalary())).toList();
    }

    private static <T> T getRandom(List<? extends T> items) {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, items.size());
        return items.get(randomIndex);
    }

    public static void main(String[] args) {


        List<String> author = List.of("Igor", "Ivan", "Andrey", "Vlad", "Ruslan", "Kostya", "Dima");
        List<Double> salarydouble = List.of(123.2 , 232.2, 56.2, 123.22, 525.2, 12.2);
        List<Integer> ageint = List.of(19, 20, 21, 23, 24, 33, 50);
        List<Department> departament = List.of(
                new Department("Condition)"),
                new Department("Exchequer"),
                new Department("Interior"),
                new Department("Agriculture"),
                new Department("Justice"),
                new Department("Commerce"));

        List<Person> people = new ArrayList<Person>();
            for (int i = 0; i < 20; i++) {

            Person person = new Person();

            person.setName(getRandom(author));
            person.setSalary(getRandom(salarydouble));
            person.setAge(getRandom(ageint));
            person.setDepartment(getRandom(departament));
            people.add(person);
        }


        System.out.println(findMostYoungestPerson(people));
        System.out.println(findMostExpensiveDepartment(people));
        System.out.println(groupByDepartment(people));
        System.out.println(groupByDepartmentName(people));
        System.out.println(getDepartmentOldestPerson(people));
        System.out.println(cheapPersonsInDepartment(people));

    }
}
