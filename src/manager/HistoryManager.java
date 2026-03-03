package manager;

import java.util.LinkedList;

/**
 * Хранит последние 11 команд
 */
public class HistoryManager {

    private LinkedList<String> history = new LinkedList<>();

    public void add(String command) {
        history.add(command);
        if (history.size() > 11)
            history.removeFirst();
    }

    public void show() {
        history.forEach(System.out::println);
    }
}