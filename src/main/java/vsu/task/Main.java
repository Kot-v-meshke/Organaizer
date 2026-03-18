package vsu.task;

import vsu.task.console.ConsoleUI;
import vsu.task.storage.InMemoryEventStorage;

public class Main {
    public static void main(String[] args) {

        InMemoryEventStorage storage = new InMemoryEventStorage();
        ConsoleUI ui = new ConsoleUI(storage);
        ui.start();

    }
}