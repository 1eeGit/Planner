/**
 * GoalPane.java
 * Author: Li Sheng
 */
package application;

import java.time.LocalDate;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * GoalPane class for goal view in the right side Tab of the UI
 */
public class GoalPane extends GridPane {
    /**
     * Default width and style for the goalPane: 60 for the status circle, 100 for
     * goal name, 80 for status Circle color: BLACK for complete, GREY for
     * incomplete and passed DDL, WHITE for incomplete and not passed DDL HBox
     * default height:15
     */
    Integer[] hboxWidth = { 60, 100, 80 };
    String stringStyle = "-fx-font-size: 15px; -fx-font-family: Arial; -fx-text-fill: BLACK;";
    /**
     * Test data for goal
     */
    Goal goalTest1 = new Goal("Test goal 1", LocalDate.of(2023, 11, 30), true, 1);
    Goal goalTest2 = new Goal("Test goal 2", LocalDate.of(2024, 4, 10), false, 2);
    Goal goalTest3 = new Goal("Test goal 3", LocalDate.of(2024, 3, 29), false, 3);

    /**
     * Default constructor
     */
    public GoalPane() {
	GridPane goalPane = new GridPane();
	HBox goalHeader = new HBox(20);
	String[] headr = { "", "Goal", "Status" };
	for (int i = 0; i < headr.length; i++) {
	    HBox headHbox = new HBox(15);
	    Label headLabel = new Label(headr[i]);
	    headLabel.setStyle(stringStyle);
	    headHbox.getChildren().add(headLabel);
	    headHbox.setPrefWidth(hboxWidth[i]);
	    headHbox.setAlignment(Pos.CENTER);
	    goalHeader.getChildren().add(headHbox);
	}
	this.add(goalHeader, 1, 0);
	updateGoal();
    }

    /**
     * Show all the goals in the goalPane first show incomplete goals in ascending
     * order of deadline then show complete goals in descending order of completion
     * time ?? should the order be set in SQL query??
     */
    public void showGoal() {
	VBox goalList = new VBox(10);
	goalList.getChildren().addAll(goalView(goalTest1), goalView(goalTest2), goalView(goalTest3));
	this.getChildren().remove(goalList);
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
}