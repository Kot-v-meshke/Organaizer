package vsu.task.command;

import vsu.task.domain.Birthday;
import vsu.task.domain.Event;
import vsu.task.domain.Meeting;
import vsu.task.service.EventService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class EditEventCommand implements Command {

    private Scanner scanner;
    private EventService eventService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public EditEventCommand(Scanner scanner, EventService eventService) {
        this.scanner = scanner;
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID события для редактирования: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            Event event = eventService.getEventById(id);

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

            eventService.updateEvent(event);
            System.out.println("Событие обновлено.");

        } catch (NumberFormatException e) {
            System.out.println("ID должен быть числом.");
        }

    }

    @Override
    public String getDescription() {
        return "Изменение события";
    }
}
