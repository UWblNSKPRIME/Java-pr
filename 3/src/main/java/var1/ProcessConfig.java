package var1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/** Обробляє набори CSV з config.txt, розділені порожніми рядками. */
public class ProcessConfig {
    /**
     * Читає кількість наборів та обробляє кожен набір.
     * @param fileName ім'я конфігураційного файлу
     * @return результати всіх наборів
     * @throws FileNotFoundException якщо файл не знайдено
     * @throws IllegalArgumentException якщо дані некоректні
     */
    public static String process(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        String result = "Part 2";
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            if (!scanner.hasNextLine()) throw new IllegalArgumentException("Empty config file");
            int count = Integer.parseInt(scanner.nextLine().trim());
            if (count <= 0) throw new IllegalArgumentException("Number of sets must be positive");
            for (int i = 1; i <= count; i++) {
                String block = "";
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (line.isEmpty()) {
                        if (!block.isEmpty()) break;
                        continue;
                    }
                    block += line + "\n";
                }
                if (block.isEmpty()) throw new IllegalArgumentException("Missing data set " + i);
                result += "\nSet " + i + "\n" + Solution.processAll(block);
            }
            while (scanner.hasNextLine()) {
                if (!scanner.nextLine().trim().isEmpty()) {
                    throw new IllegalArgumentException("More data sets than declared");
                }
            }
        }
        return result;
    }

    /**
     * Запускає другу частину роботи.
     * @param args прапорець --append для збереження результатів першої частини
     */
    public static void main(String[] args) {
        try {
            String result = process("config.txt");
            boolean append = args.length == 1 && args[0].equals("--append");
            Solution.displayResults(result, "output.txt", append);
        } catch (IOException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
