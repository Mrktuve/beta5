package managers;

import models.*;
import enums.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Управляет чтением/записью XML-файла.
 */
public class FileManager {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Читает коллекцию из XML-файла.
     * @param fileName имя файла
     * @return коллекция работников
     * @throws IOException если ошибка чтения
     */
    public static Collection<Worker> read(String fileName) throws IOException {
        Collection<Worker> workers = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists() || !file.canRead()) {
            System.err.println("Файл не доступен для чтения: " + fileName);
            return workers;
        }

        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.contains("<worker>")) {
                    Worker worker = parseWorker(scanner);
                    if (worker != null) workers.add(worker);
                }
            }
        }
        return workers;
    }

    private static Worker parseWorker(Scanner scanner) {
        try {
            int id = 0;
            String name = null;
            Coordinates coords = null;
            LocalDate creationDate = null;
            double salary = 0;
            LocalDate startDate = null;
            Date endDate = null;
            Status status = null;
            Person person = null;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.contains("</worker>")) break;

                if (line.matches("<id>\\d+</id>")) {
                    id = Integer.parseInt(line.replaceAll("<[^>]+>", ""));
                } else if (line.matches("<name>.+</name>")) {
                    name = line.replaceAll("<[^>]+>", "");
                } else if (line.contains("<coordinates>")) {
                    coords = parseCoordinates(scanner);
                } else if (line.matches("<creationDate>.+</creationDate>")) {
                    String val = line.replaceAll("<[^>]+>", "");
                    creationDate = LocalDate.parse(val, DATE_FORMAT);
                } else if (line.matches("<salary>.+</salary>")) {
                    salary = Double.parseDouble(line.replaceAll("<[^>]+>", ""));
                } else if (line.matches("<startDate>.+</startDate>")) {
                    String val = line.replaceAll("<[^>]+>", "");
                    startDate = LocalDate.parse(val, DATE_FORMAT);
                } else if (line.matches("<endDate>.+</endDate>")) {
                    String val = line.replaceAll("<[^>]+>", "");
                    if (!val.isEmpty()) {
                        LocalDate ld = LocalDate.parse(val, DATE_FORMAT);
                        endDate = Date.from(ld.atStartOfDay().atZone(java.time.ZoneId.systemDefault()).toInstant());
                    }
                } else if (line.matches("<status>.+</status>")) {
                    String val = line.replaceAll("<[^>]+>", "");
                    if (!val.isEmpty()) status = Status.valueOf(val);
                } else if (line.contains("<person>")) {
                    person = parsePerson(scanner);
                }
            }

            if (name != null && coords != null && startDate != null) {
                Worker w = new Worker(id, name, coords, creationDate, salary, startDate, endDate, status, person);
                // Обновим nextId в Worker
                return w;
            }
        } catch (Exception e) {
            System.err.println("Ошибка парсинга работника: " + e.getMessage());
        }
        return null;
    }

    private static Coordinates parseCoordinates(Scanner scanner) {
        Double x = null;
        float y = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.contains("</coordinates>")) break;
            if (line.matches("<x>.+</x>")) {
                x = Double.parseDouble(line.replaceAll("<[^>]+>", ""));
            } else if (line.matches("<y>.+</y>")) {
                y = Float.parseFloat(line.replaceAll("<[^>]+>", ""));
            }
        }
        return new Coordinates(x, y);
    }

    private static Person parsePerson(Scanner scanner) {
        String passportID = null;
        Color eyeColor = null, hairColor = null;
        Country nationality = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.contains("</person>")) break;
            if (line.matches("<passportID>.+</passportID>")) {
                passportID = line.replaceAll("<[^>]+>", "");
            } else if (line.matches("<eyeColor>.+</eyeColor>")) {
                String val = line.replaceAll("<[^>]+>", "");
                if (!val.isEmpty()) eyeColor = Color.valueOf(val);
            } else if (line.matches("<hairColor>.+</hairColor>")) {
                hairColor = Color.valueOf(line.replaceAll("<[^>]+>", ""));
            } else if (line.matches("<nationality>.+</nationality>")) {
                String val = line.replaceAll("<[^>]+>", "");
                if (!val.isEmpty()) nationality = Country.valueOf(val);
            }
        }
        if (hairColor != null) {
            return new Person(passportID, eyeColor, hairColor, nationality);
        }
        return null;
    }

    /**
     * Записывает коллекцию в XML-файл.
     * @param fileName имя файла
     * @param workers коллекция
     * @throws IOException если ошибка записи
     */
    public static void write(String fileName, Collection<Worker> workers) throws IOException {
        File file = new File(fileName);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, false), true)) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<collection>");
            for (Worker w : workers) {
                writer.println("  <worker>");
                writer.println("    <id>" + w.getId() + "</id>");
                writer.println("    <name>" + escapeXml(w.getName()) + "</name>");
                writer.println("    <coordinates>");
                writer.println("      <x>" + w.getCoordinates().getX() + "</x>");
                writer.println("      <y>" + w.getCoordinates().getY() + "</y>");
                writer.println("    </coordinates>");
                writer.println("    <creationDate>" + w.getCreationDate().format(DATE_FORMAT) + "</creationDate>");
                writer.println("    <salary>" + w.getSalary() + "</salary>");
                writer.println("    <startDate>" + w.getStartDate().format(DATE_FORMAT) + "</startDate>");
                if (w.getEndDate() != null) {
                    LocalDate ld = w.getEndDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                    writer.println("    <endDate>" + ld.format(DATE_FORMAT) + "</endDate>");
                } else {
                    writer.println("    <endDate/>");
                }
                if (w.getStatus() != null) {
                    writer.println("    <status>" + w.getStatus() + "</status>");
                }
                if (w.getPerson() != null) {
                    Person p = w.getPerson();
                    writer.println("    <person>");
                    if (p.getPassportID() != null) {
                        writer.println("      <passportID>" + escapeXml(p.getPassportID()) + "</passportID>");
                    }
                    if (p.getEyeColor() != null) {
                        writer.println("      <eyeColor>" + p.getEyeColor() + "</eyeColor>");
                    }
                    writer.println("      <hairColor>" + p.getHairColor() + "</hairColor>");
                    if (p.getNationality() != null) {
                        writer.println("      <nationality>" + p.getNationality() + "</nationality>");
                    }
                    writer.println("    </person>");
                }
                writer.println("  </worker>");
            }
            writer.println("</collection>");
        }
    }

    private static String escapeXml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}