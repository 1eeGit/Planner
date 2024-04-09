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
     * void deleteEvent(Task task); void deleteEvent(Goal goal); void
     * deleteEvent(Activity activity);
     */

    /**
     * Modify event object
     * 
     * @param task
     */
    void modifyEvent(Task task);

    void modifyEvent(Goal goal);

    void modifyEvent(Activity activity);

    /**
     * Return event list from database
     */
    public List<Event> getEventList(int type, String db, LocalDate date);
}
