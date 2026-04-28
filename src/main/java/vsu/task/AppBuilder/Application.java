package vsu.task.AppBuilder;

import vsu.task.command.CommandInvoker;
import vsu.task.command.CommandType;

import java.util.Scanner;

public class Application {
    private final Scanner scanner;
    private final CommandInvoker invoker;

    public Application(Scanner scanner, CommandInvoker invoker) {
        this.scanner = scanner;
        this.invoker = invoker;
    }

    public void start() {
        boolean running = true;
        while (running) {
            invoker.printMenu();
            System.out.print("Выберите действие: ");
            String choice = scanner.nextLine();
            CommandType type = CommandType.fromCode(choice);

            if (type == CommandType.EXIT_COMMAND) {
                running = false;
            } else if (type != null) {
                invoker.executeCommand(choice);
            } else {
                System.out.println("Неверная команда.");
            }
        }
    }
}