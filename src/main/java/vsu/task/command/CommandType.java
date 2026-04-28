package vsu.task.command;

public enum CommandType {

    EXIT_COMMAND("0", "Выход"),
    ADD_EVENT_COMMAND("1", "Добавить событие"),
    EDIT_EVENT_COMMAND("2", "Изменить событие"),
    VIEW_EVENT_COMMAND("3", "Посмотреть событие"),
    DELETE_EVENT_COMMAND("4", "Удалить событие");

    private final String menuCode;
    private final String description;

    CommandType(String menuCode, String description) {
        this.menuCode = menuCode;
        this.description = description;

    }

    public String getMenuCode() {
        return menuCode;
    }

    public String getDescription() {
        return description;
    }

    public static CommandType fromCode(String code) {
        for (CommandType type : CommandType.values()) {
            if (type.getMenuCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
