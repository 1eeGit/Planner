/**
 * EventManagement.java
 * Author: Li Sheng
 */
package application;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class implements interface EventManager for Event objects management
 * functions: overloads and overrides createEvent, deleteEvent and modifyEvent
 * methods from interface.
 */
public class EventManagement implements EventManager {
    /**
     * Create new event object
     */
    @Override
    public Task createEvent(int taskID, String taskName, boolean taskStatus, LocalDate taskDate, int taskRelatedAct) {
	return new Task(taskID, taskName, taskStatus, taskDate, taskRelatedAct);
    }

    @Override
    public Activity createEvent(int actID, String actName, LocalDateTime actStime, LocalDateTime actEtime,
	    int actRelatedTask, LocalDate date) {
	return new Activity(actID, actName, actStime, actEtime, actRelatedTask, date);
    }

    @Override
    public Goal createEvent(String goalName, LocalDate goalDdl, boolean goalStatus, int goalID) {
	return new Goal(goalName, goalDdl, goalStatus, goalID);
    }

    /**
     * Delete event object
     */
    @Override
    public void deleteEvent(Task task) {

    }

    @Override
    public void deleteEvent(Goal goal) {
    }

    @Override
    public void deleteEvent(Activity activity) {

    }

    /**
     * Modify event object
     */
    @Override
    public void modifyEvent(Task task) {

    }

    @Override
    public void modifyEvent(Goal goal) {
    }

    @Override
    public void modifyEvent(Activity activity) {
    }
}
