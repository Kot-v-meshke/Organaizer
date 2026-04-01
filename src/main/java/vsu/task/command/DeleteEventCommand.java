package vsu.task.command;

import vsu.task.service.EventService;

import java.util.Scanner;

public class DeleteEventCommand implements Command {

    private Scanner scanner;
    private EventService eventService;

    public DeleteEventCommand(Scanner scanner, EventService eventService) {
        this.scanner = scanner;
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID события для удаления: ");
        try {
            long id = Long.parseLong(scanner.nextLine());
            if (eventService.getEventById(id) != null) {
                eventService.deleteEvent(id);
                System.out.println("Событие удалено.");
            } else {
                System.out.println("Событие с таким ID не найдено.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID должен быть числом.");
        }
    }

    @Override
    public String getDescription() {
        return "Удаление события";
    }
}
