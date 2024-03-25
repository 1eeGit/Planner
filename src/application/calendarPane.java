package application;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class calendarPane extends GridPane {
    private int year;
    private int month;
    private int day;
    private Calendar calendar;

    public calendarPane() {
	GridPane dayPane = new GridPane();
	calendar = new GregorianCalendar();
	month = calendar.get(Calendar.MONTH);
	year = calendar.get(Calendar.YEAR);
	int dayMax = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	updateCalendar();
	/*
	 * Set calendar head line as Labels: Monday - Sunday
	 */
	String dayHeader[] = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
	for (int i = 0; i <= 6; i++) {
	    dayPane.add(new Label(dayHeader[i]), i, 1);
	}
	/*
	 * Set current month days as Buttons use console print check the mouse click
	 * action
	 */
	for (int i = 1; i <= dayMax; i++) {

	    Button dayButton = new Button(String.valueOf(i));
	    int day = i;
	    dayButton.setOnAction(e -> {
		System.out.println("Calendar test: Day " + day + " clicked!");
	    });
	    dayPane.add(dayButton, i % 7, i / 7 + 2);

	}
	this.add(dayPane, 0, 1);
    }

    public void updateCalendar() {
	calendar.set(Calendar.YEAR, year);
	calendar.set(Calendar.MONTH, month);
	calendar.set(Calendar.DATE, 1);
    }

    public calendarPane(int year, int month, int day, Calendar calendar) {
	super();
	this.year = year;
	this.month = month;
	this.day = day;
	this.calendar = calendar;
    }

    public int getYear() {
	return year;
    }

    public void setYear(int year) {
	this.year = year;
    }

    public int getMonth() {
	return month;
    }

    public void setMonth(int month) {
	this.month = month;
    }

    public int getDay() {
	return day;
    }

    public void setDay(int day) {
	this.day = day;
    }

    public Calendar getCalendar() {
	return calendar;
    }

    public void setCalendar(Calendar calendar) {
	this.calendar = calendar;
    }

}
