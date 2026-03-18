package vsu.task.domain;

import java.time.LocalDate;
import java.time.Period;

public class Birthday extends Event {

    private String name;
    private int birthYear;

    public Birthday(LocalDate date, String description, String name, int birthYear) {
        super(date, description);
        this.name = name;
        this.birthYear = birthYear;
    }

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

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
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
