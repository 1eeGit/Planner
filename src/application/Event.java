/**
 * Main.java
 * Author: Li Sheng
 */
package application;

import java.time.LocalDate;

/**
 * Abstract class for Event object: task, goal and activity; each event has a
 * name, status, date, ID and type.
 */
public abstract class Event {
    private String name;
    private boolean status;
    private LocalDate date;
    private int ID;
    protected String type;

    /**
     * Event constructor with parameters
     * 
     * @param name
     * @param status
     * @param date
     * @param ID
     * @param type
     */
    public Event(String name, boolean status, LocalDate date, int ID, String type) {
	super();
	this.name = name;
	this.status = status;
	this.date = date;
	this.ID = ID;
	this.type = type;
    }

    /**
     * Abstract method for eventTest
     */
    public abstract void eventTest();

    /**
     * Default constructor
     */
    public Event() {
	super();
	this.name = "";
	this.status = false;
	this.date = LocalDate.now();
	this.ID = 0;
	this.type = null;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public boolean isStatus() {
	return status;
    }

    public void setStatus(boolean status) {
	this.status = status;
    }

    public LocalDate getDate() {
	return date;
    }

    public void setDate(LocalDate date) {
	this.date = date;
    }

    public int getID() {
	return ID;
    }

    public void setID(int ID) {
	this.ID = ID;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }
}
