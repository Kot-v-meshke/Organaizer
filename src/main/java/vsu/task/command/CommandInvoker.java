package vsu.task.command;

import vsu.task.command.factory.CommandFactory;

import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {

    private final Map<String, Command> commands = new HashMap<>();
    private final CommandFactory factory;

    public CommandInvoker(CommandFactory factory) {
        this.factory = factory;
        registerAllCommands();
    }

    private void registerAllCommands() {
        for (CommandType type : CommandType.values()) {
            commands.put(type.getMenuCode(), factory.createCommand(type));
        }
    }

    public void executeCommand(String code) {
        Command command = commands.get(code);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Неверная команда.");
        }
    }

    public void printMenu() {
        System.out.println("\n--- Меню ---");
        for (CommandType type : CommandType.values()) {
            System.out.println(type.getMenuCode() + ". " + type.getDescription());
        }
    }

}
