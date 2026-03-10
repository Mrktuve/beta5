package managers;

import java.util.LinkedList;

/**
 * Управляет историей последних 11 команд.
 */
public class HistoryManager {
    private final LinkedList<String> history = new LinkedList<>();
    private static final int MAX_SIZE = 11;

    /**
     * Добавляет команду в историю.
     * @param command имя команды (без аргументов)
     */
    public void add(String command) {
        if (command == null || command.isEmpty()) return;
        history.addLast(command.toUpperCase());
        if (history.size() > MAX_SIZE) {
            history.removeFirst();
        }
    }

    /**
     * Возвращает историю команд.
     * @return строка с историей
     */
    public String getHistory() {
        if (history.isEmpty()) return "История пуста";
        StringBuilder sb = new StringBuilder("История команд:\n");
        for (int i = 0; i < history.size(); i++) {
            sb.append(i + 1).append(". ").append(history.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Очищает историю.
     */
    public void clear() {
        history.clear();
    }
}