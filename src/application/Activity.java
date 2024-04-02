/**
 * Activity.java
 * Author: Li Sheng
 */
package application;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <subclass of Event> Task class for activity object: Each activity is created
 * at a specific date, and can only be modified at that date, can only be
 * related to one task recorded at the same date.
 */
public class Activity extends Event {
    private LocalDateTime actStime;
    private LocalDateTime actEtime;
    private int actRelatedTask;

    /**
     * Activity constructor with parameters: default status is false, status is not
     * used in this class.
     * 
     * @param actID
     * @param actName
     * @param actStime
     * @param actEtime
     * @param actRelatedTask
     * @param date
     */
    public Activity(int actID, String actName, LocalDateTime actStime, LocalDateTime actEtime, int actRelatedTask,
	    LocalDate date) {
	super(actName, false, date, actID, "Activity");
	this.actStime = actStime;
	this.actEtime = actEtime;
	this.actRelatedTask = actRelatedTask;
    }

    /**
     * Default constructor default actStime = 00:00, actEtime = 23:59 within the
     * same date
     */
    public Activity() {
	super();
	this.actStime = LocalDateTime.of(LocalDate.now(), LocalTime.of(00, 00));
	this.actEtime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 59));
	this.actRelatedTask = 0;
	this.setType("Activity");
    }

    /**
     * calculate activity the duration in double hours
     * 
     * @param activity
     * @return
     */
    public double hour(Activity activity) {
	Duration duration = Duration.between(activity.getActStime(), activity.getActEtime());
	return (double) duration.toHours();
    }

    /**
     * Override the abstract method eventTest from superclass Event
     */
    @Override
    public void eventTest() {
	System.out.println(this.type + " test:" + "\n" + this.getName() + " " + this.getDate() + " " + this.getID()
		+ this.isStatus());
	System.out.println(" " + this.getActRelatedTask() + " " + this.getActStime() + " " + this.getActEtime() + " "
		+ this.getType() + "\n");
    }

    /**
     * Additional activity getters and setters for actStime, actEtime and
     * actRelatedTask
     * 
     * @return
     */

    public LocalDateTime getActStime() {
	return actStime;
    }

    public void setActStime(LocalDateTime actStime) {
	this.actStime = actStime;
    }

    public LocalDateTime getActEtime() {
	return actEtime;
    }

    public void setActEtime(LocalDateTime actEtime) {
	this.actEtime = actEtime;
    }

    public int getActRelatedTask() {
	return actRelatedTask;
    }

    public void setActRelatedTask(int actRelatedTask) {
	this.actRelatedTask = actRelatedTask;
    }

}
