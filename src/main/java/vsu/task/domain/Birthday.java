package vsu.task.domain;

import java.time.LocalDate;
import java.time.Period;

public class Birthday extends Event {

    private String name;

    public Birthday(LocalDate date, String description, String name) {
        super(date, description);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return Period.between(getDate(), LocalDate.now()).getYears();
    }

    public String getDetails() {
        return String.format("Имя %s. Сейчас %d лет.", name, getAge());
    }

    @Override
    public EventType getType() {
        return EventType.BIRTHDAY;
    }
}
