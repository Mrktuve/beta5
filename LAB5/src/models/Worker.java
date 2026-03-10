package models;

import enums.Status;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * Представляет работника организации.
 * Реализует Comparable для сортировки по зарплате (по возрастанию для PriorityQueue).
 */
public class Worker implements Comparable<Worker> {
    private static int nextId = 1;

    private final int id; // > 0, уникальное, автогенерация
    private String name; // не null, не пустая
    private Coordinates coordinates; // не null
    private final LocalDate creationDate; // не null, автогенерация
    private double salary; // > 0
    private LocalDate startDate; // не null
    private Date endDate; // может быть null
    private Status status; // может быть null
    private Person person; // может быть null

    /**
     * Создаёт нового работника.
     * @param name имя (не null, не пустое)
     * @param coordinates координаты (не null)
     * @param salary зарплата (> 0)
     * @param startDate дата начала работы (не null)
     * @param endDate дата окончания (может быть null)
     * @param status статус (может быть null)
     * @param person информация о человеке (может быть null)
     * @throws IllegalArgumentException при нарушении ограничений полей
     */
    public Worker(String name, Coordinates coordinates, double salary,
                  LocalDate startDate, Date endDate, Status status, Person person) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name не может быть null или пустым");
        }
        if (coordinates == null) {
            throw new IllegalArgumentException("coordinates не может быть null");
        }
        if (salary <= 0) {
            throw new IllegalArgumentException("salary должен быть > 0");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("startDate не может быть null");
        }

        this.id = nextId++;
        this.name = name.trim();
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.person = person;
    }

    // Для десериализации из XML
    public Worker(int id, String name, Coordinates coordinates, LocalDate creationDate,
                  double salary, LocalDate startDate, Date endDate, Status status, Person person) {
        this.name = name;
        this.coordinates = coordinates;
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.person = person;
        this.id = id;
        this.creationDate = creationDate != null ? creationDate : LocalDate.now();
        if (id >= nextId) nextId = id + 1;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public LocalDate getCreationDate() { return creationDate; }
    public double getSalary() { return salary; }
    public LocalDate getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public Status getStatus() { return status; }
    public Person getPerson() { return person; }

    // Setters для update
    public void setName(String name) {
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("name invalid");
        this.name = name;
    }
    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) throw new IllegalArgumentException("coordinates invalid");
        this.coordinates = coordinates;
    }
    public void setSalary(double salary) {
        if (salary <= 0) throw new IllegalArgumentException("salary invalid");
        this.salary = salary;
    }
    public void setStartDate(LocalDate startDate) {
        if (startDate == null) throw new IllegalArgumentException("startDate invalid");
        this.startDate = startDate;
    }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public void setStatus(Status status) { this.status = status; }
    public void setPerson(Person person) { this.person = person; }

    /**
     * Сравнивает работников по зарплате для PriorityQueue.
     * @param other другой работник
     * @return отрицательное, если этот меньше other
     */
    @Override
    public int compareTo(Worker other) {
        if (other == null) return 1;
        return Double.compare(this.salary, other.salary);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker)) return false;
        Worker worker = (Worker) o;
        return id == worker.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", salary=" + salary +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", person=" + person +
                '}';
    }
}