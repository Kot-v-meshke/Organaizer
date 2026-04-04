package vsu.task.command.factory;

import vsu.task.command.AddEventCommand;
import vsu.task.command.Command;
import vsu.task.command.EditEventCommand;
import vsu.task.command.ViewEventsCommand;
import vsu.task.command.DeleteEventCommand;
import vsu.task.service.EventService;

import java.util.Scanner;

public class ConsoleCommandFactory implements CommandFactory {
    private final Scanner scanner;
    private final EventService service;

    public ConsoleCommandFactory(Scanner scanner, EventService service) {
        this.scanner = scanner;
        this.service = service;
    }

    @Override
    public Command createAddCommand() {
        return new AddEventCommand(scanner, service);
    }

    @Override
    public Command createViewCommand() {
        return new ViewEventsCommand(scanner, service);
    }

    @Override
    public Command createEditCommand() {
        return new EditEventCommand(scanner, service);
    }

    @Override
    public Command createDeleteCommand() {
        return new DeleteEventCommand(scanner, service);
    }
}

