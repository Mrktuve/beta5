package managers;

import models.Worker;
import java.io.File;
import java.util.Scanner;

/**
 * Обрабатывает команды пользователя.
 */
public class CommandManager {
    private final CollectionManager collectionManager;
    private final InputManager inputManager;
    private final HistoryManager historyManager;
    private final String fileName;
    private boolean running = true;

    public CommandManager(CollectionManager cm, InputManager im, HistoryManager hm, String fn) {
        this.collectionManager = cm;
        this.inputManager = im;
        this.historyManager = hm;
        this.fileName = fn;
    }

    /**
     * Запускает интерактивный режим.
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Приложение запущено. Введите 'help' для справки.");

        while (running) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+", 2);
            String cmd = parts[0].toLowerCase();
            historyManager.add(cmd);

            try {
                processCommand(cmd, parts.length > 1 ? parts[1] : null, scanner);
            } catch (Exception e) {
                System.err.println("Ошибка выполнения команды: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void processCommand(String cmd, String arg, Scanner interactiveScanner) {
        switch (cmd) {
            case "help" -> showHelp();
            case "info" -> System.out.println(collectionManager.getInfo());
            case "show" -> System.out.println(collectionManager.show());
            case "add" -> {
                Worker w = inputManager.readWorker();
                if (collectionManager.add(w)) System.out.println("Добавлено с id=" + w.getId());
            }
            case "update" -> {
                if (arg == null) { System.out.println("Укажите id"); return; }
                String[] parts = arg.split("\\s+", 2);
                int id = Integer.parseInt(parts[0]);
                Worker w = inputManager.readWorker();
                if (collectionManager.update(id, w)) System.out.println("Обновлено");
                else System.out.println("Не найдено");
            }
            case "remove_by_id" -> {
                if (arg == null) { System.out.println("Укажите id"); return; }
                int id = Integer.parseInt(arg.trim());
                if (collectionManager.removeById(id)) System.out.println("Удалено");
                else System.out.println("Не найдено");
            }
            case "clear" -> { collectionManager.clear(); System.out.println("Очищено"); }
            case "save" -> {
                try {
                    FileManager.write(fileName, collectionManager.getCollection());
                    System.out.println("Сохранено в " + fileName);
                } catch (Exception e) {
                    System.err.println("Ошибка сохранения: " + e.getMessage());
                }
            }
            case "execute_script" -> {
                if (arg == null) { System.out.println("Укажите имя файла"); return; }
                executeScript(arg.trim());
            }
            case "exit" -> { running = false; System.out.println("Завершение..."); }
            case "add_if_max" -> {
                Worker w = inputManager.readWorker();
                if (collectionManager.addIfMax(w)) System.out.println("Добавлено");
                else System.out.println("Не добавлено (не максимум)");
            }
            case "remove_lower" -> {
                Worker w = inputManager.readWorker();
                int count = collectionManager.removeLower(w);
                System.out.println("Удалено элементов: " + count);
            }
            case "history" -> System.out.println(historyManager.getHistory());
            case "remove_any_by_status" -> {
                if (arg == null) { System.out.println("Укажите статус"); return; }
                try {
                    enums.Status status = enums.Status.valueOf(arg.trim().toUpperCase());
                    if (collectionManager.removeAnyByStatus(status)) System.out.println("Удалено");
                    else System.out.println("Не найдено");
                } catch (IllegalArgumentException e) {
                    System.out.println("Неверный статус. Доступные: " + java.util.Arrays.toString(enums.Status.values()));
                }
            }
            case "filter_starts_with_name" -> {
                if (arg == null) { System.out.println("Укажите подстроку"); return; }
                String result = collectionManager.filterStartsWithName(arg.trim());
                System.out.println(result.isEmpty() ? "Ничего не найдено" : result);
            }
            case "print_descending" -> System.out.println(collectionManager.printDescending());
            default -> System.out.println("Неизвестная команда. Введите 'help'.");
        }
    }

    private void executeScript(String fileName) {
        File file = new File(fileName);
        if (!file.exists() || !file.canRead()) {
            System.err.println("Файл скрипта не доступен: " + fileName);
            return;
        }
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split("\\s+", 2);
                String cmd = parts[0].toLowerCase();
                historyManager.add(cmd);
                processCommand(cmd, parts.length > 1 ? parts[1] : null, fileScanner);
            }
        } catch (Exception e) {
            System.err.println("Ошибка скрипта: " + e.getMessage());
        }
    }

    private void showHelp() {
        System.out.println("""
                Доступные команды:
                help - показать справку
                info - информация о коллекции
                show - показать все элементы
                add {element} - добавить элемент
                update id {element} - обновить элемент по id
                remove_by_id id - удалить по id
                clear - очистить коллекцию
                save - сохранить в файл
                execute_script file_name - выполнить скрипт
                exit - завершить программу
                add_if_max {element} - добавить если максимум
                remove_lower {element} - удалить меньшие
                history - показать последние 11 команд
                remove_any_by_status status - удалить по статусу
                filter_starts_with_name name - фильтр по имени
                print_descending - вывести по убыванию
                """);
    }
}