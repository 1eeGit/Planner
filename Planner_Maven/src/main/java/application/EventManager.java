/**
 * EventManager.java
 * Author: Li Sheng
 */
package application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for Event objects: task, goal and activity; including: createEvent
 * methods overloads for creating new objects event specific parameters;
 * deleteEvent methods overloads for deleting new objects event specific
 * parameters; modifyEvent methods overloads for modifying new objects event
 * specific parameters.
 */
public interface EventManager {
    /**
     * Create new event object
     * 
     * @param name
     * @param status
     * @param date
     * @param type
     * @param specific data field
     * @return
     */
    void createEvent(String taskName, int taskStatus, LocalDate taskDate);

    void createEvent(String actName, LocalDateTime actStime, LocalDateTime actEtime, int actRelatedTask,
	    LocalDate date);

    void createEvent(String goalName, LocalDate goalDdl, int goalStatus); // goalStatus is 0 for uncompleted, 1 for
									  // completed

    /**
     * Delete event object
     * 
     * @param event
     */
    void deleteEvent(Event event);

    /**
     * Return event list from database
     */
    public List<Event> getEventList(int type, String db, LocalDate date);

    /**
     * Update event status, first INT is status, second is event ID
     * 
     * @param event
     * @param status
     */
    void updateEstatus(Event event, int status);

    /**
     * Modify event object
     */
    void modifyEvent(Event event, String newName, LocalDate newDate);

}
