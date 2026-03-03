package manager;

import model.Worker;

import java.time.LocalDate;
import java.util.*;

/**
 * Управляет коллекцией
 */
public class CollectionManager {

    private PriorityQueue<Worker> collection = new PriorityQueue<>();
    private LocalDate initDate = LocalDate.now();

    public void add(Worker w) { collection.add(w); }

    public void clear() { collection.clear(); }

    public void removeById(int id) {
        collection.removeIf(w -> w.getId() == id);
    }

    public void removeLower(Worker worker) {
        collection.removeIf(w -> w.compareTo(worker) < 0);
    }

    public void removeAnyByStatus(model.Status status) {
        for (Worker w : collection) {
            if (w.getStatus() == status) {
                collection.remove(w);
                break;
            }
        }
    }

    public void addIfMax(Worker worker) {
        if (collection.isEmpty() ||
                worker.compareTo(Collections.max(collection)) > 0) {
            collection.add(worker);
        }
    }

    public void show() {
        collection.forEach(System.out::println);
    }

    public void printDescending() {
        collection.stream()
                .sorted(Collections.reverseOrder())
                .forEach(System.out::println);
    }

    public void filterStartsWithName(String name) {
        collection.stream()
                .filter(w -> w.getName().startsWith(name))
                .forEach(System.out::println);
    }

    public void info() {
        System.out.println("Тип: " + collection.getClass());
        System.out.println("Дата инициализации: " + initDate);
        System.out.println("Количество элементов: " + collection.size());
    }

    public PriorityQueue<Worker> getCollection() {
        return collection;
    }
}