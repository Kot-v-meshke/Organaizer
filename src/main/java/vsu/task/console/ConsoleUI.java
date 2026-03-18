package vsu.task.console;

import vsu.task.domain.*;
import vsu.task.storage.EventStorage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final EventStorage storage;
    private final Scanner scanner;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public ConsoleUI(EventStorage storage) {
        this.storage = storage;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=== Добро пожаловать в Органайзер ===");
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addEvent();
                    break;
                case "2":
                    viewEvents();
                    break;
                case "3":
                    editEvent();
                    break;
                case "4":
                    deleteEvent();
                    break;
                case "5":
                    System.out.println("Выход из программы.");
                    running = false;
                    break;
                default:
                    System.out.println("Введите число от 1 до 5");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- Меню ---");
        System.out.println("1. Добавить событие");
        System.out.println("2. Просмотреть события");
        System.out.println("3. Редактировать событие");
        System.out.println("4. Удалить событие");
        System.out.println("5. Выход");
    }

    private void addEvent() {
        System.out.println("\n--- Добавление события ---");
        System.out.println("1. День рождения");
        System.out.println("2. Важная встреча");
        System.out.print("Тип события: ");
        String typeChoice = scanner.nextLine();

        LocalDate date = readDate("Введите дату (дд.мм.гггг): ");
        System.out.print("Описание: ");
        String desc = scanner.nextLine();

        Event event = null;

        if ("1".equals(typeChoice)) {
            System.out.print("Имя именинника: ");
            String name = scanner.nextLine();
            event = new Birthday(date, desc, name);
        } else if ("2".equals(typeChoice)) {
            System.out.print("Место встречи: ");
            String location = scanner.nextLine();
            event = new Meeting(date, desc, location);
        } else {
            System.out.println("Неверный тип события.");
            return;
        }

        storage.addEvent(event);
        System.out.println("Событие успешно добавлено!");
    }

    private void viewEvents() {
        System.out.println("\n--- Просмотр событий ---");
        System.out.println("1. Все события");
        System.out.println("2. Поиск по конкретной дате");
        System.out.print("Выбор: ");
        String choice = scanner.nextLine();

        List<Event> events;
        if ("2".equals(choice)) {
            LocalDate date = readDate("Введите дату для поиска (дд.мм.гггг): ");
            events = storage.getEventsByDate(date);
        } else {
            events = storage.getAllEvents();
        }

        if (events.isEmpty()) {
            System.out.println("Событий не найдено.");
        } else {
            // Сортировка по дате для удобства
            events.sort((e1, e2) -> e1.getDate().compareTo(e2.getDate()));
            for (Event e : events) {
                System.out.println("ID: " + e.getId() + " | " + e.getDetails());
                System.out.println("-------------------------");
            }
        }
    }

    private void editEvent() {
        System.out.print("Введите ID события для редактирования: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Event event = storage.getEventById(id);

            if (event == null) {
                System.out.println("Событие не найдено.");
                return;
            }

            System.out.println("Редактирование: " + event.getDetails());
            System.out.print("Новая дата (дд.мм.гггг) или Enter, чтобы оставить: ");
            String dateStr = scanner.nextLine();
            if (!dateStr.isEmpty()) {
                try {
                    event.setDate(LocalDate.parse(dateStr, formatter));
                } catch (DateTimeParseException e) {
                    System.out.println("Неверный формат даты, дата не изменена.");
                }
            }

            System.out.print("Новое описание или Enter, чтобы оставить: ");
            String desc = scanner.nextLine();
            if (!desc.isEmpty()) {
                event.setDescription(desc);
            }

            // Специфичные поля
            if (event instanceof Birthday) {
                Birthday b = (Birthday) event;
                System.out.print("Новое имя или Enter, чтобы оставить: ");
                String name = scanner.nextLine();
                if (!name.isEmpty()) b.setName(name);
            } else if (event instanceof Meeting) {
                Meeting m = (Meeting) event;
                System.out.print("Новое место или Enter, чтобы оставить: ");
                String loc = scanner.nextLine();
                if (!loc.isEmpty()) m.setLocation(loc);
            }

            storage.updateEvent(event);
            System.out.println("Событие обновлено.");

        } catch (NumberFormatException e) {
            System.out.println("ID должен быть числом.");
        }
    }

    private void deleteEvent() {
        System.out.print("Введите ID события для удаления: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            if (storage.getEventById(id) != null) {
                storage.removeEvent(id);
                System.out.println("Событие удалено.");
            } else {
                System.out.println("Событие с таким ID не найдено.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть числом.");
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return LocalDate.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Неверный формат. Используйте дд.мм.гггг (например, 25.12.2023)");
            }
        }
    }
}
