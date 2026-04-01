package vsu.task;

import vsu.task.command.AddEventCommand;
import vsu.task.command.DeleteEventCommand;
import vsu.task.command.EditEventCommand;
import vsu.task.command.CommandInvoker;
import vsu.task.command.ViewEventsCommand;
import vsu.task.repository.EventRepository;
import vsu.task.repository.InMemoryEventRepository;
import vsu.task.service.EventService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        EventRepository storage = new InMemoryEventRepository();
        EventService service = new EventService(storage);

        CommandInvoker invoker = new CommandInvoker();
        invoker.registerCommand("1", new AddEventCommand(scanner, service));
        invoker.registerCommand("2", new ViewEventsCommand(scanner, service));
        invoker.registerCommand("3", new EditEventCommand(scanner, service));
        invoker.registerCommand("4", new DeleteEventCommand(scanner, service));

        boolean running = true;
        while (running) {
            invoker.printMenu();
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();

            if ("0".equals(choice)) {
                running = false;
            } else {
                invoker.executeCommand(choice);
            }
        }

    }
}