package vsu.task.command;

public enum CommandType {
    ADDEVENTCOMMAND("1"),
    EDITEVENTCOMMAND("2"),
    VIEWEVENTCOMMAND("3"),
    DELETEEVENTCOMMAND("4");

    private final String menuCode;

    CommandType(String menuCode) {
        this.menuCode = menuCode;
    }
    public String getMenuCode() {
        return menuCode;
    }
}
