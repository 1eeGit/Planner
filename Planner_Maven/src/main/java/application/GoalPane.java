/**
 * GoalPane.java
 * Author: Li Sheng
 */
package application;

import java.time.LocalDate;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// TODO: Auto-generated Javadoc
/**
 * GoalPane class for goal view in the right side Tab of the UI
 */
public class GoalPane extends GridPane {

    /** The event manager. */
    private EventManagement eventManager;
    /**
     * Default width and style for the goalPane: 60 for the status circle, 100 for
     * goal name, 80 for status Circle color: BLACK for complete, GREY for
     * incomplete and passed DDL, WHITE for incomplete and not passed DDL HBox
     * default height:15
     */
    Integer[] hboxWidth = { 60, 100, 80 };

    /** The string style. */
    String stringStyle = "-fx-font-size: 15px; -fx-font-family: Arial; -fx-text-fill: BLACK;";

    /**
     * Default constructor
     */
    public GoalPane() {
	// this.eventManager = eventManager;
	GridPane goalPane = new GridPane();
	HBox goalHeader = new HBox(20);
	String[] header = { "", "Goal", "Status" };
	for (int i = 0; i < header.length; i++) {
	    HBox headHbox = new HBox(15);
	    Label headLabel = new Label(header[i]);
	    headLabel.setStyle(stringStyle);
	    headHbox.getChildren().add(headLabel);
	    headHbox.setPrefWidth(hboxWidth[i]);
	    headHbox.setAlignment(Pos.CENTER);
	    goalHeader.getChildren().add(headHbox);
	}
	this.add(goalHeader, 1, 0);
	Button goalButton = new Button("Add Goal");
	goalButton.setStyle(stringStyle);
	goalButton.setOnAction(e -> addGoal());
	goalButton.setAlignment(Pos.BOTTOM_CENTER);
	this.add(goalButton, 1, 3);
	updateGoal();
    }

    /**
     * Show all the goals in the goalPane: first show incomplete goals in ascending
     * order of deadline then show complete goals in descending order of completion
     * time. The order is sorted by the database query.
     */
    public void showGoal() {
	VBox goalList = new VBox(15);
	/** change database name when not testing **/
	List<Goal> goalDBList = Goal.getGoals("testDB.db");
	for (Goal goal : goalDBList) {
	    goalList.getChildren().add(goalView(goal));
	}
	goalList.getChildren().add(new Label("Total: " + goalDBList.size() + " goals"));
	this.getChildren().remove(goalList); // remove the old goalList
	this.add(goalList, 1, 1);
    }

    /**
     * Create single goal view use HBox and default width
     * 
     * @param goal
     * @return
     */
    public HBox goalView(Goal goal) {
	boolean goalStatus = goal.isStatus();
	String goalName = goal.getName();
	LocalDate goalDdl = goal.getDate();
	Circle statusCircle = new Circle(8);
	HBox goalView = new HBox(15);
	String goalStatusCell;
	/**
	 * Set the color of the status circle and text of the status cell according to
	 * the status of the goal
	 */
	if (goalStatus) {// if the goal is complete
	    statusCircle.setFill(Color.BLACK);
	    goalStatusCell = "Complete";
	} else if (!goalStatus && goalDdl.isBefore(LocalDate.now())) {
	    // if the goal is incomplete and the ddl is passed
	    // compare time use .isAfter() , .isBefore() , .isEqual() instead of > , < ,==
	    statusCircle.setFill(Color.GREY);
	    goalStatusCell = "Incomplete";
	} else {// if the goal is incomplete and the ddl is not passed
	    statusCircle.setFill(Color.WHITE);
	    long days = LocalDate.now().until(goalDdl).getDays(); // calculate the days left
	    goalStatusCell = days + " days left";
	}
	;
	StackPane circleStack = new StackPane(statusCircle);
	circleStack.setPrefWidth(hboxWidth[0]);
	circleStack.setAlignment(Pos.CENTER);
	goalView.getChildren().add(circleStack);
	String[] goalInfo = { goalName, goalStatusCell };

	for (int i = 0; i < 2; i++) {
	    HBox goalHbox = new HBox(15, new Label(goalInfo[i]));
	    goalHbox.setPrefWidth(hboxWidth[i + 1]);
	    goalHbox.setAlignment(Pos.CENTER);
	    goalView.getChildren().add(goalHbox);
	}
	return goalView;
    }

    /**
     * update goalPane
     */
    public void updateGoal() {
	showGoal();
    }

    /**
     * Adds the goal.
     */
    private void addGoal() {
	EventDialog dialog = new EventDialog();
	EventData data = dialog.showAndGetData();
	if (data != null) {
	    Goal goal = eventManager.createEvent(data.getName(), data.getDate(), true, 1);

	    updateGoal();
	}

    }
}