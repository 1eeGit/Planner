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
    /** Common data fields between events **/
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

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
	return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * Checks if is status.
     *
     * @return true, if is status
     */
    public boolean isStatus() {
	return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(boolean status) {
	this.status = status;
    }

    /**
     * Gets the date.
     *
     * @return the date
     */
    public LocalDate getDate() {
	return date;
    }

    /**
     * Sets the date.
     *
     * @param date the new date
     */
    public void setDate(LocalDate date) {
	this.date = date;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getID() {
	return ID;
    }

    /**
     * Sets the id.
     *
     * @param ID the new id
     */
    public void setID(int ID) {
	this.ID = ID;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
	return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(String type) {
	this.type = type;
    }
}
