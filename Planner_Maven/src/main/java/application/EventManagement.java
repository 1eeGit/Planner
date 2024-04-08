/**
 * EventManagement.java
 * Author: Li Sheng
 */
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Retrieve event list from database name db. 1st query: retrieve goal list from
     * database 2nd query: retrieve activity and task list TOGETHER from database
     */
    @Override
    public List<Event> getEventList(int type, String db, LocalDate date) {
	String url = "jdbc:sqlite:" + db;
	String query = null;
	if (type == 1) { // retrieve goal list
	    /**
	     * improved query: now the incomplete previous goal is showing first
	     */
	    query = "SELECT * FROM event WHERE type =1 ORDER BY status, " + "CASE WHEN status =0 THEN date END ASC,"
		    + "CASE WHEN status =1 THEN date END DESC;";
	} else if (type == 2) { // retrieve task list for a specific day
	    // query = "SELECT * FROM event AS e JOIN task AS t ON e.ID= t.ID WHERE e.date =
	    // ? ORDER BY e.id;";
	    query = "SELECT * FROM event AS e JOIN task AS t ON e.ID = t.ID WHERE  e.date = '" + date.toString()
		    + "' ORDER BY e.id;";
	} else { // retrieve activity list for a specific day
	    query = "SELECT * FROM event AS e  JOIN activity AS a ON e.ID= a.ID WHERE e.date = '" + date.toString()
		    + "'ORDER BY id;";
	}

	List<Event> events = new ArrayList<>();
	try (Connection conn = DriverManager.getConnection(url); PreparedStatement PS = conn.prepareStatement(query)) {
	    if (type != 1) {
		// set date parameter for task and activity, as the only placeholder '?' is
		// date, but not working properly
		// PS.setDate(1, java.sql.Date.valueOf(date));
		// System.out.println(query);
		// System.out.println("Query with date set: " + PS.toString());
	    }
	    try (ResultSet RS = PS.executeQuery()) {
		while (RS.next()) {
		    Boolean status = RS.getInt("status") == 1;
		    /**
		     * Not using parameter date directly, because when getting Goal objects, we will
		     * set date to NULL since we are showing all the goals not a specific day. Thus
		     * here will get the real date again for goal object's correct creating in
		     * GoalPane class.
		     */
		    LocalDate eventDate = LocalDate.parse(RS.getString("date"));
		    Event event = null;
		    switch (type) {
		    case 1:
			event = new Goal(RS.getString("name"), eventDate, status, RS.getInt("id"));
			break;
		    case 2:
			event = new Task(RS.getInt("id"), RS.getString("name"), status, eventDate, RS.getInt("actID"));
			break;
		    case 3:
			event = new Activity(RS.getInt("id"), RS.getString("name"),
				LocalDateTime.parse(RS.getString("actStime")),
				LocalDateTime.parse(RS.getString("actETime")), RS.getInt("taskID"), eventDate);
			break;
		    }
		    if (event != null) {
			events.add(event);

		    }
		}
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
	return events;
    }

}
