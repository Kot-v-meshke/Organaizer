package vsu.task.command.factory;

import vsu.task.command.AddEventCommand;
import vsu.task.command.Command;
import vsu.task.command.CommandType;
import vsu.task.command.EditEventCommand;
import vsu.task.command.ExitCommand;
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
    public Command createCommand(CommandType type) {
        return switch (type) {
            case ADD_EVENT_COMMAND -> new AddEventCommand(scanner, service);
            case VIEW_EVENT_COMMAND -> new ViewEventsCommand(scanner, service);
            case EDIT_EVENT_COMMAND -> new EditEventCommand(scanner, service);
            case DELETE_EVENT_COMMAND -> new DeleteEventCommand(scanner, service);
            case EXIT_COMMAND -> new ExitCommand();
        };
    }
}

