package vsu.task.domain;

public enum EventType {
    BIRTHDAY("День рождения"),
    MEETING("Встреча");

    private final String label;

    EventType(String label) {
        this.label=label;
    }

    public String getLabel() {
        return label;
    }
}
