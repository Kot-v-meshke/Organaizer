package vsu.task.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputHelper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static LocalDate readDate(Scanner scanner, String prompt) {
        LocalDate result = LocalDate.now();
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                result = LocalDate.parse(input, FORMATTER);
                valid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка: неверный формат. Используйте дд.мм.гггг");
            }
        }

        return result;
    }

    public static long readLong(Scanner scanner, String prompt) {
        long result = 0;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                result = Long.parseLong(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число");
            }
        }
        return result;
    }

    public static int readInt(Scanner scanner, String prompt) {
        int result = 0;
        boolean valid = false;

        while (!valid) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                result = Integer.parseInt(input);
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число");
            }
        }
        return result;
    }
}