package application;

import java.time.LocalDate;

public class EventData {
    private String name;
    private LocalDate date;

    public EventData(String name, LocalDate date) {
	this.name = name;
	this.date = date;
    }

    public String getName() {
	return name;
    }

    public LocalDate getDate() {
	return date;
    }
}
