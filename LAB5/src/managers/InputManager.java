package managers;

import models.*;
import enums.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

/**
 * Управляет вводом данных от пользователя с валидацией.
 */
public class InputManager {
    private final Scanner scanner;

    public InputManager(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Считывает Worker от пользователя.
     * @return новый работник
     */
    public Worker readWorker() {
        String name = readString("Введите name:", false, true);
        Double x = readDouble("Введите x (> -26):", -26.0, null, false);
        float y = readFloat("Введите y:", null, null, false);
        Coordinates coords = new Coordinates(x, y);
        double salary = readDouble("Введите salary (> 0):", 0.0, null, false);
        LocalDate startDate = readLocalDate("Введите startDate (yyyy-MM-dd):", false);

        System.out.println("Введите endDate (yyyy-MM-dd) или пустую строку для null:");
        Date endDate = readOptionalDate();

        Status status = readEnum("Введите status", Status.class, true);
        Person person = readPerson();

        return new Worker(name, coords, salary, startDate, endDate, status, person);
    }

    private Person readPerson() {
        System.out.println("Создать Person? (да/нет, Enter=нет):");
        if (!"да".equalsIgnoreCase(scanner.nextLine().trim())) return null;

        String passportID = readString("Введите passportID (Enter=null):", true, false);
        Color eyeColor = readEnum("Введите eyeColor", Color.class, true);
        Color hairColor = readEnum("Введите hairColor", Color.class, false);
        Country nationality = readEnum("Введите nationality", Country.class, true);

        return new Person(passportID, eyeColor, hairColor, nationality);
    }

    private String readString(String prompt, boolean allowEmpty, boolean required) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                if (!required) return null;
                if (allowEmpty) return "";
                System.out.println("Поле не может быть пустым!");
                continue;
            }
            if (required && input.trim().isEmpty()) {
                System.out.println("Поле не может быть пустым!");
                continue;
            }
            return input.trim();
        }
    }

    private Double readDouble(String prompt, Double min, Double max, boolean nullable) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                if (nullable) return null;
                System.out.println("Поле обязательно!");
                continue;
            }
            try {
                double val = Double.parseDouble(input);
                if (min != null && val <= min) {
                    System.out.println("Значение должно быть > " + min);
                    continue;
                }
                if (max != null && val >= max) {
                    System.out.println("Значение должно быть < " + max);
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число!");
            }
        }
    }

    private float readFloat(String prompt, Float min, Float max, boolean nullable) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                if (nullable) return 0;
                System.out.println("Поле обязательно!");
                continue;
            }
            try {
                float val = Float.parseFloat(input);
                if (min != null && val <= min) {
                    System.out.println("Значение должно быть > " + min);
                    continue;
                }
                if (max != null && val >= max) {
                    System.out.println("Значение должно быть < " + max);
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число!");
            }
        }
    }

    private LocalDate readLocalDate(String prompt, boolean required) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                if (!required) return null;
                System.out.println("Поле обязательно!");
                continue;
            }
            try {
                return LocalDate.parse(input, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка: формат yyyy-MM-dd!");
            }
        }
    }

    private Date readOptionalDate() {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) return null;
        try {
            LocalDate ld = LocalDate.parse(input, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
            return Date.from(ld.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            System.out.println("Ошибка даты, установлено null");
            return null;
        }
    }

    private <T extends Enum<T>> T readEnum(String prompt, Class<T> enumClass, boolean nullable) {
        T[] constants = enumClass.getEnumConstants();
        while (true) {
            System.out.println("Доступные значения: " + java.util.Arrays.toString(constants));
            System.out.println(prompt + (nullable ? " (Enter=null):" : ":"));
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                if (nullable) return null;
                System.out.println("Поле обязательно!");
                continue;
            }
            try {
                return Enum.valueOf(enumClass, input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: такого значения нет!");
            }
        }
    }

    /**
     * Считывает ID команды.
     * @return id
     */
    public int readId() {
        while (true) {
            System.out.println("Введите id:");
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число!");
            }
        }
    }
}