/**
 * EventManager.java
 * Author: Li Sheng
 */
package application;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
     * @param ID
     * @param name
     * @param status
     * @param date
     * @param type
     * @param specific data field
     * @return
     */
    Task createEvent(int taskID, String taskName, boolean taskStatus, LocalDate taskDate, int taskRelatedAct);

    Activity createEvent(int actID, String actName, LocalDateTime actStime, LocalDateTime actEtime, int actRelatedTask,
	    LocalDate date);

    Goal createEvent(String goalName, LocalDate goalDdl, boolean goalStatus, int goalID);

    /**
     * Delete event object
     * 
     * @param ID
     * @param name
     * @param status
     * @param date
     * @param type
     * @param specific event data field
     * @return
     */
    void deleteEvent(Task task);

    void deleteEvent(Goal goal);

    void deleteEvent(Activity activity);

    /**
     * Modify event object
     * 
     * @param ID
     * @param name
     * @param status
     * @param date
     * @param type
     * @param specific event data field
     * @return
     */
    void modifyEvent(Task task);

    void modifyEvent(Goal goal);

    void modifyEvent(Activity activity);
}
