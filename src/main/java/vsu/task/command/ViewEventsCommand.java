package vsu.task.command;

import vsu.task.domain.Event;
import vsu.task.service.EventService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static vsu.task.util.InputHelper.readDate;

public class ViewEventsCommand implements Command {

    private Scanner scanner;
    private EventService eventService;

    public ViewEventsCommand(Scanner scanner, EventService eventService) {
        this.scanner = scanner;
        this.eventService = eventService;
    }

    @Override
    public void execute() {

        System.out.println("\n--- Просмотр событий ---");
        System.out.println("1. Все события");
        System.out.println("2. Поиск по конкретной дате");
        System.out.print("Выбор: ");
        String choice = scanner.nextLine();

        List<Event> events;
        if ("2".equals(choice)) {
            LocalDate date = readDate(scanner, "Введите дату для поиска (дд.мм.гггг): ");
            events = eventService.getEventsByDate(date);
        } else {
            events = eventService.getAllEvents();
        }

        if (events.isEmpty()) {
            System.out.println("Событий не найдено.");
        } else {
            events.sort((e1, e2) -> e1.getDate().compareTo(e2.getDate()));
            for (Event e : events) {
                System.out.println("ID: " + e.getId() + " | " + e.getDetails());
                System.out.println("-------------------------");
            }
        }
    }

    @Override
    public String getDescription() {
        return "Просмотр событий";
    }
}
