package managers;

import models.Worker;
import java.util.PriorityQueue;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Управляет коллекцией работников.
 */
public class CollectionManager {
    private final PriorityQueue<Worker> collection;
    private final String collectionType;
    private final java.time.LocalDateTime initializationDate;

    public CollectionManager() {
        this.collection = new PriorityQueue<>();
        this.collectionType = "PriorityQueue<Worker>";
        this.initializationDate = java.time.LocalDateTime.now();
    }

    /**
     * Добавляет работника.
     * @param worker работник
     * @return true если добавлен
     */
    public boolean add(Worker worker) {
        return worker != null && collection.add(worker);
    }

    /**
     * Обновляет работника по id.
     * @param id идентификатор
     * @param newWorker новые данные
     * @return true если обновлён
     */
    public boolean update(int id, Worker newWorker) {
        for (Worker w : collection) {
            if (w.getId() == id) {
                w.setName(newWorker.getName());
                w.setCoordinates(newWorker.getCoordinates());
                w.setSalary(newWorker.getSalary());
                w.setStartDate(newWorker.getStartDate());
                w.setEndDate(newWorker.getEndDate());
                w.setStatus(newWorker.getStatus());
                w.setPerson(newWorker.getPerson());
                return true;
            }
        }
        return false;
    }

    /**
     * Удаляет по id.
     * @param id идентификатор
     * @return true если удалён
     */
    public boolean removeById(int id) {
        return collection.removeIf(w -> w.getId() == id);
    }

    /**
     * Очищает коллекцию.
     */
    public void clear() {
        collection.clear();
    }

    /**
     * Возвращает информацию о коллекции.
     * @return строка с информацией
     */
    public String getInfo() {
        return "Тип: " + collectionType + "\n" +
                "Дата инициализации: " + initializationDate + "\n" +
                "Количество элементов: " + collection.size();
    }

    /**
     * Возвращает все элементы.
     * @return строковое представление
     */
    public String show() {
        if (collection.isEmpty()) return "Коллекция пуста";
        StringBuilder sb = new StringBuilder();
        for (Worker w : collection) {
            sb.append(w).append("\n");
        }
        return sb.toString();
    }

    /**
     * Добавляет если элемент больше максимального.
     * @param worker элемент для добавления
     * @return true если добавлен
     */
    public boolean addIfMax(Worker worker) {
        if (collection.isEmpty()) return add(worker);
        Worker max = collection.stream().max(Worker::compareTo).orElse(null);
        if (max != null && worker != null && worker.compareTo(max) > 0) {
            return add(worker);
        }
        return false;
    }

    /**
     * Удаляет все элементы меньше заданного.
     * @param worker эталон
     * @return количество удалённых
     */
    public int removeLower(Worker worker) {
        if (worker == null) return 0;
        Collection<Worker> toRemove = collection.stream()
                .filter(w -> w.compareTo(worker) < 0)
                .collect(Collectors.toList());
        collection.removeAll(toRemove);
        return toRemove.size();
    }

    /**
     * Удаляет один элемент по статусу.
     * @param status статус
     * @return true если удалён
     */
    public boolean removeAnyByStatus(enums.Status status) {
        return collection.removeIf(w -> status.equals(w.getStatus()));
    }

    /**
     * Фильтрует по началу имени.
     * @param name подстрока
     * @return отфильтрованные элементы
     */
    public String filterStartsWithName(String name) {
        if (name == null) return "";
        return collection.stream()
                .filter(w -> w.getName() != null && w.getName().startsWith(name))
                .map(Worker::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Возвращает элементы в порядке убывания.
     * @return строка с элементами
     */
    public String printDescending() {
        return collection.stream()
                .sorted((a, b) -> -a.compareTo(b))
                .map(Worker::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Возвращает коллекцию для сохранения.
     * @return коллекция работников
     */
    public Collection<Worker> getCollection() {
        return collection;
    }

    /**
     * Загружает коллекцию извне.
     * @param workers коллекция для загрузки
     */
    public void loadCollection(Collection<Worker> workers) {
        if (workers != null) {
            collection.addAll(workers);
        }
    }
}