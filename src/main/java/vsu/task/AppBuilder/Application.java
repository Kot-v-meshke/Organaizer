package vsu.task.AppBuilder;

import vsu.task.command.CommandInvoker;

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

            if ("0".equals(choice)) {
                running = false;
            } else {
                invoker.executeCommand(choice);
            }
        }
    }
}