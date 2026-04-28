package vsu.task.command.factory;

import vsu.task.command.Command;
import vsu.task.command.CommandType;

public interface CommandFactory {
    Command createCommand(CommandType type);
}