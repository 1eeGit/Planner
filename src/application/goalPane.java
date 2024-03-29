package application;

import java.time.LocalDate;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class goalPane extends GridPane {

    public goalPane() {
	GridPane goalPane = new GridPane();
	HBox goalHeader = new HBox(10);
	goalHeader.getChildren().addAll(new Label("     "), new Label("        Goal"),
		new Label("              Status"));
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
	goalList.getChildren().addAll(goalView(true, "Test goal 1", LocalDate.of(2023, 11, 30)),
		goalView(false, "Test goal 2", LocalDate.of(2024, 04, 10)));
	this.getChildren().remove(goalList);
	this.add(goalList, 1, 1);
    }

    /**
     * create single goal view
     * 
     * @return
     */
    public HBox goalView(boolean goalStatus, String goalName, LocalDate goalDdl) {
	HBox goalView = new HBox(10);
	Label goalNameLabel = new Label(goalName);
	Circle statusCircle = new Circle(5);
	Label goalStatusLabel = new Label();
	if (goalStatus) {// if the goal is complete
	    statusCircle.setFill(Color.BLACK);
	    goalStatusLabel.setText("Complete");
	} else {// if the goal is incomplete
	    statusCircle.setFill(Color.WHITE);
	    long days = LocalDate.now().until(goalDdl).getDays(); // calculate the days left
	    goalStatusLabel.setText(days + " days left");
	}
	;
	goalView.getChildren().addAll(statusCircle, goalNameLabel, goalStatusLabel);

	return goalView;
    }

    /**
     * update goalPane
     */
    public void updateGoal() {
	showGoal();
    }
}