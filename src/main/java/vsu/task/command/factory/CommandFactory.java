package vsu.task.command.factory;

import vsu.task.command.Command;

public interface CommandFactory {
    Command createAddCommand();
    Command createViewCommand();
    Command createEditCommand();
    Command createDeleteCommand();
}