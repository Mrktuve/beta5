package manager;

import model.Worker;

import java.io.*;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Работа с XML файлом
 */
public class FileManager {

    private String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    public PriorityQueue<Worker> read() {

        PriorityQueue<Worker> collection = new PriorityQueue<>();

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                scanner.nextLine(); // примитивное чтение XML
            }

            scanner.close();

        } catch (Exception e) {
            System.out.println("Ошибка чтения файла");
        }

        return collection;
    }

    public void write(PriorityQueue<Worker> collection) {

        try {
            PrintWriter writer = new PrintWriter(fileName);

            writer.println("<workers>");
            for (Worker w : collection) {
                writer.println("  <worker>");
                writer.println("    <id>" + w.getId() + "</id>");
                writer.println("  </worker>");
            }
            writer.println("</workers>");

            writer.close();

        } catch (Exception e) {
            System.out.println("Ошибка записи файла");
        }
    }
}