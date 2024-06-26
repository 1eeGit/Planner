/**
 * calendarPane.java
 * Author: Li Sheng
 */
package application;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * calendarPane class for calendar view, allow user to navigate between months
 * show required month calendar in the left side of UI
 */
public class CalendarPane extends GridPane {
    private int year;
    private int month;
    private int day;
    private Calendar calendar;
    private LocalDate currentDate = LocalDate.now();
    private GridPane dayPane = new GridPane();
    private Button LastMbutton = new Button("<");
    private Button NextMbutton = new Button(">");
    private DatePicker datePicker = new DatePicker();

    /**
     * Default constructor
     */
    public CalendarPane() {
	calendar = new GregorianCalendar();
	month = currentDate.getMonthValue();
	year = currentDate.getYear();
	updateCalendar();
    }

    /**
     * Clear and set the calendar view
     */
    public void showCalendar() {
	/**
	 * Clear the existing view
	 */
	this.getChildren().remove(dayPane);
	dayPane.getChildren().clear();

	/**
	 * Set calendar head line as Labels: Monday - Sunday
	 */
	int currentDay = currentDate.getDayOfMonth();
	int dayMax = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
	calendarTest(this);
	String dayHeader[] = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
	for (int i = 0; i <= 6; i++) {
	    Label headerLabel = new Label(dayHeader[i]);
	    GridPane.setHalignment(headerLabel, HPos.CENTER);
	    GridPane.setValignment(headerLabel, VPos.CENTER);
	    dayPane.add(headerLabel, i, 1);
	}

	/**
	 * Set last month days as Labels: current month - 1, Set different color as gray
	 * clone the current calendar to create another object LastMCalendar
	 */
	Calendar LastMCalendar = (Calendar) calendar.clone();
	LastMCalendar.set(Calendar.MONTH, month - 2);// 1-based
	int LastMdayMax = LastMCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	System.out.println("Calendar test: Last month dayMax: " + LastMdayMax);
	/**
	 * Set last month days as Labels: current month - 1, Set different color as gray
	 */
	for (int i = 0; i < firstDay - 1; i++) {
	    Label dayLabel = new Label(String.valueOf(LastMdayMax - firstDay + i + 2));
	    dayLabel.setMinSize(40, 40);
	    dayLabel.setAlignment(Pos.CENTER);
	    dayLabel.setStyle("-fx-text-fill: gray;");
	    dayPane.add(dayLabel, i % 7, 2);
	}

	/**
	 * Set current month as Labels, Set style refer to setDayStyle function
	 */
	for (int i = 1; i <= dayMax; i++) {
	    Label dayLabel = new Label(String.valueOf(i));
	    dayLabel.setMinSize(40, 40);
	    dayLabel.setAlignment(Pos.CENTER);
	    setDayStyle(i, currentDay, dayLabel);
	    mouseEvent(this, dayLabel, i);
	    dayPane.add(dayLabel, (i + firstDay - 2) % 7, (i + firstDay - 2) / 7 + 2);
	}

	/**
	 * Set next month days as Labels: current month + 1, Set different color as gray
	 */
	for (int i = 1; i <= 43 - dayMax - firstDay; i++) {
	    Label dayLabel = new Label(String.valueOf(i));
	    dayLabel.setMinSize(40, 40);
	    dayLabel.setAlignment(Pos.CENTER);
	    dayLabel.setStyle("-fx-text-fill: gray;");
	    // mouseEvent(this, dayLabel);
	    dayPane.add(dayLabel, (i + firstDay + dayMax - 2) % 7, (i + firstDay + dayMax - 2) / 7 + 2);
	}
	this.add(dayPane, 1, 1);
	this.add(LastMbutton, 0, 0);
	this.add(NextMbutton, 2, 0);

	/**
	 * Set event actions for last month and next month buttons
	 */
	LastMbutton.setOnAction(e -> {
	    int currentMonth = getMonth();
	    if (currentMonth > 1) {
		// setMonth(currentMonth - 1);
		datePicker.setValue(LocalDate.of(year, month - 1, 1));
	    } else {
		// setYear(getYear() - 1);
		// setMonth(12);
		datePicker.setValue(LocalDate.of(year - 1, 12, 1));
		;
	    }
	});
	NextMbutton.setOnAction(e -> {
	    int currentMonth = getMonth();
	    if (currentMonth < 12) {
		// setMonth(currentMonth + 1);
		datePicker.setValue(LocalDate.of(year, month + 1, 1));
	    } else {
		// setYear(getYear() + 1);
		// setMonth(1);
		datePicker.setValue(LocalDate.of(year + 1, 1, 1));
		;
	    }
	});

	/**
	 * Set the date picker to select the date in the top middle of calendar the
	 * setting of the date picker will trigger the action event to update the
	 * calendar ???? some how it is not working as expected:???? Children: duplicate
	 * children added: parent = Grid hgap=0.0, vgap=0.0, alignment=TOP_LEFT
	 */
	datePicker.setValue(LocalDate.of(year, month, LocalDate.now().getDayOfMonth()));
	datePicker.setOnAction(e -> {
	    LocalDate selectedDate = datePicker.getValue();
	    if (selectedDate.getYear() != year || selectedDate.getMonthValue() != month) {
		setYear(selectedDate.getYear());
		setMonth(selectedDate.getMonthValue());
		updateCalendar();
	    }
	});
	GridPane.setHalignment(datePicker, HPos.CENTER);
	GridPane.setValignment(datePicker, VPos.CENTER);
	this.add(datePicker, 1, 0);
    }

    /**
     * Calendar color setting: current moth: today in red, others in black other
     * month: in gray
     * 
     * @param i
     * @param currentDay
     * @param dayLabel
     */
    public void setDayStyle(int i, int currentDay, Label dayLabel) {
	if (i == currentDay && month == currentDate.getMonthValue() && year == currentDate.getYear()) {
	    dayLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
	} else {
	    dayLabel.setStyle("-fx-border-color: lightgray;  -fx-font-weight: bold;-fx-padding: 5px;");
	}
	;
    }

    /**
     * Mouse event actions for day labels Show different border color when mouse
     * entered, exited and clicked
     * 
     * @param calendarPane
     * @param dayLabel
     * @param date
     */
    public void mouseEvent(CalendarPane calendarPane, Label dayLabel, int date) {
	int currentDay = calendarPane.currentDate.getDayOfMonth();
	dayLabel.setOnMouseEntered(e -> dayLabel.setStyle("-fx-border-color: blue; -fx-padding: 5px;"));
	dayLabel.setOnMouseExited(e -> {
	    dayLabel.setStyle("-fx-border-color: lightgray; -fx-padding: 5px;");
	    setDayStyle(date, currentDay, dayLabel);
	});
	dayLabel.setOnMouseClicked(e -> {
	    dayLabel.setStyle("-fx-border-color: red; -fx-padding: 5px;");
	    System.out.println("Calendar test: Year " + year + " Month " + month + " Day " + date + " clicked!");
	});
    }

    /**
     * Update the calendar and refresh the view
     */
    public void updateCalendar() {
	calendar.set(Calendar.YEAR, year);
	calendar.set(Calendar.MONTH, month - 1); // 1-based
	showCalendar();
    }

    /**
     * Print the calendar test
     */
    public void calendarTest(CalendarPane cp) {
	int year = cp.getYear();
	int month = cp.getMonth();
	int dayMax = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	int firstDay = calendar.get(Calendar.DAY_OF_WEEK);
	System.out.println("Calendar test: " + " Year is:" + year + " " + ",Month is:" + month + ",dayMax is:" + dayMax
		+ ",1st weekday is: " + firstDay);
    }

    public CalendarPane(int year, int month, int day, Calendar calendar) {
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
	updateCalendar();
    }

    public int getMonth() {
	return month;
    }

    public void setMonth(int month) {
	this.month = month;
	updateCalendar();
    }

    public int getDay() {
	return day;
    }

    public void setDay(int day) {
	this.day = day;
	updateCalendar();
    }

    public Calendar getCalendar() {
	return calendar;
    }

    public void setCalendar(Calendar calendar) {
	this.calendar = calendar;
    }

}
