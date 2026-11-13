package var1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/** Обчислює середній вік мешканців кожного міста з CSV-файлу. */
public class Solution {

    /**
     * Читає текст файлу в UTF-8.
     * @param fileName ім'я вхідного файлу
     * @return текст файлу
     * @throws FileNotFoundException якщо файл не знайдено
     */
    public static String readFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        String data = "";
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            while (scanner.hasNextLine()) {
                data += scanner.nextLine() + "\n";
            }
        }
        return data;
    }

    /**
     * Перевіряє запис CSV без лапок та вкладених роздільників.
     * @param line запис у форматі name,age,city
     * @return місто та вік, розділені символом |
     * @throws IllegalArgumentException якщо запис некоректний
     */
    public static String processRow(String line) {
        int first = line.indexOf(',');
        int second = first < 0 ? -1 : line.indexOf(',', first + 1);
        if (first < 0 || second < 0 || line.indexOf(',', second + 1) >= 0) {
            throw new IllegalArgumentException("Expected exactly three CSV fields: " + line);
        }
        String name = line.substring(0, first).trim();
        String ageText = line.substring(first + 1, second).trim();
        String city = line.substring(second + 1).trim();
        if (name.isEmpty() || city.isEmpty() || city.indexOf('|') >= 0) {
            throw new IllegalArgumentException("Name and city must be nonempty; city cannot contain |: " + line);
        }
        int age = Integer.parseInt(ageText);
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be between 0 and 150: " + ageText);
        }
        return city + "|" + age;
    }

    /**
     * Перевіряє заголовок і всі записи та обчислює середній вік по містах.
     * @param data CSV-текст із заголовком name,age,city
     * @return результати у порядку першої появи міст
     * @throws IllegalArgumentException якщо набір порожній або некоректний
     */
    public static String processAll(String data) {
        String rows = "";
        String cities = "|";
        try (Scanner scanner = new Scanner(data)) {
            if (!scanner.hasNextLine()) {
                throw new IllegalArgumentException("Empty data set");
            }
            String header = scanner.nextLine().trim();

            if (!header.equals("name,age,city")) {
                throw new IllegalArgumentException("Expected header name,age,city");
            }

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.isEmpty()) continue;
                String row = processRow(line);
                rows = rows + row + "\n";
                String city = row.substring(0, row.indexOf('|'));
                if (!cities.contains("|" + city + "|")) cities = cities + city + "|";
            }
        }
        if (rows.isEmpty()) throw new IllegalArgumentException("Data set contains no records");
        String result = "";
        String remainingCities = cities.substring(1);
        while (!remainingCities.isEmpty()) {
            int separator = remainingCities.indexOf('|');
            String city = remainingCities.substring(0, separator);
            remainingCities = remainingCities.substring(separator + 1);
            long sum = 0;
            long count = 0;
            try (Scanner scanner = new Scanner(rows)) {
                while (scanner.hasNextLine()) {
                    String row = scanner.nextLine();
                    int pipe = row.indexOf('|');
                    if (row.substring(0, pipe).equals(city)) {
                        sum += Integer.parseInt(row.substring(pipe + 1));
                        count++;
                    }
                }
            }
            if (!result.isEmpty()) result += "\n";
            result += city + " - avg age: " + (double) sum / count;
        }
        return result;
    }

    /**
     * Виводить результат у консоль і файл у UTF-8.
     * @param result текст результату
     * @param outFile вихідний файл
     * @param append чи дописувати результат у кінець файлу
     * @throws IOException якщо запис неможливий
     */
    public static void displayResults(String result, String outFile, boolean append) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outFile, append))) {
            writer.println(result);
            if (writer.checkError()) throw new IOException("Cannot write " + outFile);
        }
        System.out.println(result);
    }

    /**
     * Запускає першу частину роботи.
     * @param args аргументи командного рядка не використовуються
     */
    public static void main(String[] args) {
        try {
            String data = readFile("input.txt");
            String result = processAll(data);
            displayResults("Part 1\n" + result, "output.txt", false);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
