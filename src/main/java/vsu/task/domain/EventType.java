package vsu.task.domain;

public enum EventType {
    BIRTHDAY("День рождения", "1"),
    MEETING("Важная встреча", "2");

    private final String label;
    private final String menuCode;

    EventType(String label, String menuCode) {
        this.label = label;
        this.menuCode = menuCode;
    }

    public String getLabel() {
        return label;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public static EventType fromMenuCode(String code) {
        for (EventType type : values()) {
            if (type.getMenuCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
