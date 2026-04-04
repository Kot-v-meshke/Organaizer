package vsu.task.command;

import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {

    private final Map<String, Command> commands = new HashMap<>();

    public CommandInvoker() {
    }

    public void registerCommand(String key, Command command) {
        commands.put(key, command);
    }

    public void executeCommand(String key) {
        Command command = commands.get(key);
        if (command != null) {
            command.execute();
        } else {
            System.out.println("Неверная команда.");
        }
    }

    public void printMenu() {
        System.out.println("\n--- Меню ---");
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            if (!"0".equals(entry.getKey())) {
                System.out.println(entry.getKey() + ". " + entry.getValue().getDescription());
            }
        }
        System.out.println("0. Выход");
    }

}
