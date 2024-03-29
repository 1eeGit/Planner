/**
 * taskPane.java
 * Author: Li Sheng
 */
package application;

public class taskPane {
    private int taskID;
    private String taskName;
    private String taskDsc;
    private boolean taskStatus;
    private int taskStime;
    private int taskEtime;
    private double taskRate;
    private int taskRelatedAct;

    public taskPane(int taskID, String taskName, String taskDsc, boolean taskStatus, int taskStime, int taskEtime,
	    double taskRate, int taskRelatedAct) {
	super();
	this.taskID = taskID;
	this.taskName = taskName;
	this.taskDsc = taskDsc;
	this.taskStatus = taskStatus;
	this.taskStime = taskStime;
	this.taskEtime = taskEtime;
	this.taskRate = taskRate;
	this.taskRelatedAct = taskRelatedAct;
    }

    public taskPane() {

    }

    public int getTaskRelatedAct() {
	return taskRelatedAct;
    }

    public void setTaskRelatedAct(int taskRelatedAct) {
	this.taskRelatedAct = taskRelatedAct;
    }

    public int getTaskID() {
	return taskID;
    }

    public void setTaskID(int taskID) {
	this.taskID = taskID;
    }

    public String getTaskName() {
	return taskName;
    }

    public void setTaskName(String taskName) {
	this.taskName = taskName;
    }

    public String getTaskDsc() {
	return taskDsc;
    }

    public void setTaskDsc(String taskDsc) {
	this.taskDsc = taskDsc;
    }

    public boolean isTaskStatus() {
	return taskStatus;
    }

    public void setTaskStatus(boolean taskStatus) {
	this.taskStatus = taskStatus;
    }

    public int getTaskStime() {
	return taskStime;
    }

    public void setTaskStime(int taskStime) {
	this.taskStime = taskStime;
    }

    public int getTaskEtime() {
	return taskEtime;
    }

    public void setTaskEtime(int taskEtime) {
	this.taskEtime = taskEtime;
    }

    public double getTaskRate() {
	return taskRate;
    }

    public void setTaskRate(double taskRate) {
	this.taskRate = taskRate;
    }

}
