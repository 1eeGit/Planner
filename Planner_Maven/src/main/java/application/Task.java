/**
 * Task.java
 * Author: Li Sheng
 */
package application;

import java.time.LocalDate;

// TODO: Auto-generated Javadoc
/**
 * <subclass of Event> Task class for task object: Each task is created at a
 * specific date, and can only be modified at that date, can only be related to
 * one activity recorded at the same date.
 */
public class Task extends Event {

    /** The task related act. */
    private int taskRelatedAct; // related activity ID

    /**
     * Task constructor with parameters
     * 
     * @param taskID
     * @param taskName
     * @param taskStatus
     * @param taskDate
     * @param taskRelatedAct
     */
    public Task(int taskID, String taskName, boolean taskStatus, LocalDate taskDate, int taskRelatedAct) {
	super(taskName, taskStatus, taskDate, taskID, "Task");
	this.taskRelatedAct = taskRelatedAct;
    }

    /**
     * Override the abstract method eventTest from superclass Event
     */
    @Override
    public void eventTest() {
	System.out.println(this.type + " test:" + "\n" + this.getName() + " " + this.getDate() + " " + this.getID()
		+ this.isStatus());
	System.out.println(" " + this.getTaskRelatedAct() + "\n");
    }

    /**
     * Default constructor
     */
    public Task() {
	super();
	this.setType("Task");
	this.taskRelatedAct = 0;
    }

    /**
     * Additional task getters and setters for taskID and taskRelatedAct
     * 
     * @return
     */
    public int getTaskRelatedAct() {
	return taskRelatedAct;
    }

    /**
     * Sets the task related act.
     *
     * @param taskRelatedAct the new task related act
     */
    public void setTaskRelatedAct(int taskRelatedAct) {
	this.taskRelatedAct = taskRelatedAct;
    }

}