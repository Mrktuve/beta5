import manager.CollectionManager;
import manager.CommandManager;
import manager.FileManager;

/**
 * Точка входа
 */
public class Main {

    public static void main(String[] args) {

        String fileName = System.getenv("WORKER_FILE");

        if (fileName == null) {
            System.out.println("Переменная окружения WORKER_FILE не задана");
            return;
        }

        FileManager fileManager = new FileManager(fileName);
        CollectionManager collectionManager = new CollectionManager();

        collectionManager.getCollection().addAll(fileManager.read());

        CommandManager commandManager =
                new CommandManager(collectionManager, fileManager);

        commandManager.startInteractiveMode();
    }
}