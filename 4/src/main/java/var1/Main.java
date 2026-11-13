package var1;

import java.util.Scanner;

/** Запитує кількість предметів одягу та заповнює масив з клавіатури. */
public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in, "UTF-8")) {
            int count = 0;
            while (count <= 0) {
                System.out.print("Number of clothes: ");
                try {
                    count = Integer.parseInt(readLine(scanner));
                } catch (NumberFormatException e) {
                    count = 0;
                }
                if (count <= 0) System.out.println("Enter a positive integer.");
            }

            Clothes[] clothes = new Clothes[count];
            for (int i = 0; i < clothes.length; i++) {
                System.out.println("Clothes " + (i + 1));
                try {
                    System.out.print("Name: ");
                    String name = readLine(scanner);
                    System.out.print("Size: ");
                    String size = readLine(scanner);
                    System.out.print("Price: ");
                    double price = Double.parseDouble(readLine(scanner));
                    clothes[i] = new Clothes(name, size, price);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid data. Enter this item again. Use a dot for decimals.");
                    i--;
                }
            }

            System.out.println("All clothes:");
            for (Clothes item : clothes) {
                System.out.println(item.toString());
            }
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    /** Читає рядок або повідомляє про передчасне завершення введення. */
    private static String readLine(Scanner scanner) {
        if (!scanner.hasNextLine()) throw new IllegalStateException("Input ended before all items were entered.");
        return scanner.nextLine().trim();
    }
}
