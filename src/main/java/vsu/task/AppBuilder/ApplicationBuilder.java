package vsu.task.AppBuilder;

import vsu.task.command.CommandInvoker;
import vsu.task.command.factory.CommandFactory;
import vsu.task.command.factory.ConsoleCommandFactory;
import vsu.task.repository.EventRepository;
import vsu.task.repository.InMemoryEventRepository;
import vsu.task.service.EventService;

import java.util.Scanner;

public class ApplicationBuilder {
    private Scanner scanner;
    private EventRepository eventRepository;
    private EventService eventService;
    private CommandFactory factory;

    public ApplicationBuilder withScanner(Scanner scanner) {
        this.scanner = scanner;
        return this;
    }

    public ApplicationBuilder withRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        return this;
    }

    public ApplicationBuilder withEventService(EventService eventService) {
        this.eventService = eventService;
        return this;
    }

    public Application build() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        if (eventRepository == null) {
            eventRepository = new InMemoryEventRepository();
        }
        if (eventService == null) {
            eventService = new EventService(eventRepository);
        }
        if (factory == null) {
            factory = new ConsoleCommandFactory(scanner, eventService);
        }

        CommandInvoker invoker = new CommandInvoker(factory);

        return new Application(scanner, invoker);
    }



}
