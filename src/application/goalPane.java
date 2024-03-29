package application;

public class goalPane {
	      private int goalID; private String goalName; private String goalDsc; private int goalDdl; private boolean goalStatus;

	public goalPane(int goalID, String goalName, String goalDsc, int goalDdl, boolean goalStatus) {
		super();
		this.goalID = goalID;
		this.goalName = goalName;
		this.goalDsc = goalDsc;
		this.goalDdl = goalDdl;
		this.goalStatus = goalStatus;
	}
	
	public goalPane() {
		
	}
	
	public int getGoalID() {
		return goalID;
	}

	public void setGoalID(int goalID) {
		this.goalID = goalID;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	public String getGoalDsc() {
		return goalDsc;
	}

	public void setGoalDsc(String goalDsc) {
		this.goalDsc = goalDsc;
	}

	public int getGoalDdl() {
		return goalDdl;
	}

	public void setGoalDdl(int goalDdl) {
		this.goalDdl = goalDdl;
	}

	public boolean isGoalStatus() {
		return goalStatus;
	}

	public void setGoalStatus(boolean goalStatus) {
		this.goalStatus = goalStatus;
	}
	
}
