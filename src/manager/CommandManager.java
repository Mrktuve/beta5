package manager;

import model.Status;
import model.Worker;

import java.io.File;
import java.util.Scanner;

/**
 * Управляет командами
 */
public class CommandManager {

    private CollectionManager collectionManager;
    private FileManager fileManager;
    private HistoryManager history = new HistoryManager();

    public CommandManager(CollectionManager cm, FileManager fm) {
        this.collectionManager = cm;
        this.fileManager = fm;
    }

    public void startInteractiveMode() {

        Scanner scanner = new Scanner(System.in);
        InputManager inputManager = new InputManager(scanner);

        while (true) {

            System.out.print("> ");
            String line = scanner.nextLine();
            String[] parts = line.split(" ");

            history.add(parts[0]);

            switch (parts[0]) {

                case "help":
                    help();
                    break;

                case "info":
                    collectionManager.info();
                    break;

                case "show":
                    collectionManager.show();
                    break;

                case "add":
                    collectionManager.add(inputManager.readWorker());
                    break;

                case "remove_by_id":
                    collectionManager.removeById(Integer.parseInt(parts[1]));
                    break;

                case "clear":
                    collectionManager.clear();
                    break;

                case "save":
                    fileManager.write(collectionManager.getCollection());
                    break;

                case "history":
                    history.show();
                    break;

                case "add_if_max":
                    collectionManager.addIfMax(inputManager.readWorker());
                    break;

                case "remove_lower":
                    collectionManager.removeLower(inputManager.readWorker());
                    break;

                case "remove_any_by_status":
                    collectionManager.removeAnyByStatus(Status.valueOf(parts[1]));
                    break;

                case "filter_starts_with_name":
                    collectionManager.filterStartsWithName(parts[1]);
                    break;

                case "print_descending":
                    collectionManager.printDescending();
                    break;

                case "execute_script":
                    executeScript(parts[1]);
                    break;

                case "exit":
                    return;

                default:
                    System.out.println("Неизвестная команда");
            }
        }
    }

    private void executeScript(String fileName) {
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                System.out.println("Скрипт: " + scanner.nextLine());
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("Ошибка выполнения скрипта");
        }
    }

    private void help() {
        System.out.println("help");
        System.out.println("info");
        System.out.println("show");
        System.out.println("add");
        System.out.println("update id");
        System.out.println("remove_by_id id");
        System.out.println("clear");
        System.out.println("save");
        System.out.println("execute_script file_name");
        System.out.println("exit");
        System.out.println("add_if_max");
        System.out.println("remove_lower");
        System.out.println("history");
        System.out.println("remove_any_by_status status");
        System.out.println("filter_starts_with_name name");
        System.out.println("print_descending");
    }
}