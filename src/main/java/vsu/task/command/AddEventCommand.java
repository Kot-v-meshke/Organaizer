package vsu.task.command;

import vsu.task.domain.EventType;
import vsu.task.service.EventService;

import java.time.LocalDate;
import java.util.Scanner;

import static vsu.task.util.InputHelper.readDate;
import static vsu.task.util.InputHelper.readInt;

public class AddEventCommand implements Command {

    private Scanner scanner;
    private EventService eventService;

    public AddEventCommand(Scanner scanner, EventService eventService) {
        this.scanner = scanner;
        this.eventService = eventService;
    }

    @Override
    public void execute() {
        System.out.println("\n--- Добавление события ---");

        EventType type = null;
        String extra = null;

        boolean trueFormat = false;
        while (!trueFormat) {
            System.out.println("1. День рождения");
            System.out.println("2. Важная встреча");
            String typeChoice = String.valueOf(readInt(scanner, "Тип события: "));
            if (EventType.BIRTHDAY.getMenuCode().equals(typeChoice)) {
                type = EventType.BIRTHDAY;
                System.out.print("Имя именинника: ");
                extra = scanner.nextLine();
                trueFormat = true;
            } else if (EventType.MEETING.getMenuCode().equals(typeChoice)) {
                type = EventType.MEETING;
                System.out.print("Место встречи: ");
                extra = scanner.nextLine();
                trueFormat = true;
            } else {
                System.out.println("Неверный тип события.");
            }
        }

        LocalDate date = readDate(scanner, "Введите дату (дд.мм.гггг): ");
        System.out.print("Описание: ");
        String desc = scanner.nextLine();

        eventService.addEvent(type, date, desc, extra);
        System.out.println("Событие успешно добавлено!");

    }

    @Override
    public String getDescription() {
        return "Добавление события";
    }


}
