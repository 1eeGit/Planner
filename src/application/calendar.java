package application;

import java.util.Calendar;

public class calendar {
	private int year; private int month; private int day; private Calendar calendar;
	
	public calendar() {
		
	}
	
	public calendar(int year, int month, int day, Calendar calendar) {
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
