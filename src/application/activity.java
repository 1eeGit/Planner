package application;

public class activity {
	private int actID; private String actName; private int actStime;  private int actEtime;  private int actRelatedTask;

	public activity(int actID, String actName, int actStime, int actEtime, int actRelatedTask) {
		super();
		this.actID = actID;
		this.actName = actName;
		this.actStime = actStime;
		this.actEtime = actEtime;
		this.actRelatedTask = actRelatedTask;
	}

	public int getActID() {
		return actID;
	}

	public void setActID(int actID) {
		this.actID = actID;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public int getActStime() {
		return actStime;
	}

	public void setActStime(int actStime) {
		this.actStime = actStime;
	}

	public int getActEtime() {
		return actEtime;
	}

	public void setActEtime(int actEtime) {
		this.actEtime = actEtime;
	}

	public int getActRelatedTask() {
		return actRelatedTask;
	}

	public void setActRelatedTask(int actRelatedTask) {
		this.actRelatedTask = actRelatedTask;
	}
	
}
