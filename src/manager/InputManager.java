package manager;

import model.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

/**
 * Отвечает за ввод данных
 */
public class InputManager {

    private Scanner scanner;

    public InputManager(Scanner scanner) {
        this.scanner = scanner;
    }

    public Worker readWorker() {

        System.out.println("Введите имя:");
        String name = scanner.nextLine();

        System.out.println("Введите x (> -26):");
        Double x = Double.parseDouble(scanner.nextLine());

        System.out.println("Введите y:");
        float y = Float.parseFloat(scanner.nextLine());

        Coordinates coordinates = new Coordinates(x, y);

        System.out.println("Введите salary (>0):");
        double salary = Double.parseDouble(scanner.nextLine());

        System.out.println("Введите startDate (yyyy-mm-dd):");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());

        return new Worker(name, coordinates, salary,
                startDate, new Date(), null, null);
    }
}