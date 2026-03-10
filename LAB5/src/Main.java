import managers.*;
import java.util.Scanner;

/**
 * Точка входа в приложение.
 */
public class Main {
    /**
     * Запускает приложение.
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        String fileName = System.getenv("WORKER_FILE");
        if (fileName == null || fileName.isEmpty()) {
            System.err.println("Установите переменную окружения WORKER_FILE с именем файла");
            System.exit(1);
        }

        CollectionManager collectionManager = new CollectionManager();
        HistoryManager historyManager = new HistoryManager();

        // Загрузка из файла
        try {
            var workers = FileManager.read(fileName);
            collectionManager.loadCollection(workers);
            System.out.println("Загружено элементов: " + workers.size());
        } catch (Exception e) {
            System.err.println("Ошибка загрузки: " + e.getMessage() + ". Начинаем с пустой коллекции.");
        }

        InputManager inputManager = new InputManager(new Scanner(System.in));
        CommandManager commandManager = new CommandManager(
                collectionManager, inputManager, historyManager, fileName);

        commandManager.start();

        // Автосохранение при выходе можно добавить при необходимости
    }
}