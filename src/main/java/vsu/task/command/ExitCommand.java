package vsu.task.command;


public class ExitCommand implements Command{


    @Override
    public void execute() {
        System.out.println("Выход из программы...");
    }

    @Override
    public String getDescription() {
        return "";
    }
}
