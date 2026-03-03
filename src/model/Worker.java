package model;

import java.time.LocalDate;
import java.util.Date;

/**
 * Класс Worker
 */
public class Worker implements Comparable<Worker> {

    private static int nextId = 1;

    private int id; // >0, уникальный, генерируется автоматически
    private String name; // не null, не пустой
    private Coordinates coordinates; // не null
    private LocalDate creationDate; // генерируется автоматически
    private double salary; // >0
    private LocalDate startDate; // не null
    private Date endDate; // может быть null
    private Status status; // может быть null
    private Person person; // может быть null

    public Worker(String name, Coordinates coordinates, double salary,
                  LocalDate startDate, Date endDate,
                  Status status, Person person) {

        this.id = nextId++;
        this.creationDate = LocalDate.now();

        this.name = name;
        this.coordinates = coordinates;
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.person = person;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getSalary() { return salary; }
    public Status getStatus() { return status; }

    public void setName(String name) { this.name = name; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public int compareTo(Worker o) {
        return Double.compare(this.salary, o.salary);
    }

    @Override
    public String toString() {
        return "Worker{id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", status=" + status +
                '}';
    }
}