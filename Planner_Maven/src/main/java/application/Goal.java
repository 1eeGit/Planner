/**
 * Goal.java
 * Author: Li Sheng
 */
package application;

import java.time.LocalDate;
import java.util.List;

/**
 * <subclass of Event> Goal class for goal object: Each goal has a deadline, and
 * can be marked as completed before the deadline. If the goal is not completed
 * before the deadline, the goal is considered as failed.
 */
public class Goal extends Event {
    /**
     * Default constructor
     */
    public Goal() {
	super();
	this.setType("Goal");
    }

    /**
     * Goal constructor with parameters
     * 
     * @param goalName
     * @param goalDdl
     * @param goalStatus
     */
    public Goal(String goalName, LocalDate goalDdl, boolean goalStatus, int goalID) {
	super(goalName, goalStatus, goalDdl, goalID, "Goal");
    }

    /**
     * Override the abstract method eventTest from superclass Event
     */
    @Override
    public void eventTest() {
	System.out.println(this.type + " test:" + "\n" + this.getName() + " " + this.getDate() + " " + this.getID()
		+ this.isStatus());
    }

    /**
     * Retrieve goal list from database name db
     * 
     * @param db
     * @return
     */
    public static List<Goal> getGoals(String db) {
	return SQLiteDB.getGoals(db);
    }
}
