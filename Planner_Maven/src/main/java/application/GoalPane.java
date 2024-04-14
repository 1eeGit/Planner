/**
 * GoalPane.java
 * Author: Li Sheng
 */
package application;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
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
    static VBox goalList = new VBox(15);

    // Create eventManager object, or the method cannot be invoked
    // EventManagement eventManager = new EventManagement();
    /**
     * Default width and style for the goalPane: 60 for the status circle, 100 for
     * goal name, 80 for status Circle color: BLACK for complete, GREY for
     * incomplete and passed DDL, WHITE for incomplete and not passed DDL HBox
     * default height:15
     */
    Integer[] hboxWidth = { 60, 100, 150, 60 };
    /** The string style. */
    String stringStyle = "-fx-font-size: 15px; -fx-font-family: Arial; -fx-text-fill: BLACK;";

    /**
     * Default constructor
     */
    public GoalPane() {
	goalList.setPrefSize(300, 200);
	ContextMenu contextMenu = EventDialog.addContextMenu(a -> addGoal());
	goalList.setOnContextMenuRequested(e -> {
	    // only show addContextMenu when right click on empty area
	    // which means the target is not HBox - goalView
	    if (!(e.getTarget() instanceof HBox)) {
		contextMenu.show(goalList, e.getScreenX(), e.getScreenY());
	    }
	});
	HBox goalHeader = new HBox(20);
	String[] header = { "", "           Goal", "             Status", "" };
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

	this.add(goalList, 1, 1);
	updateGoal();
    }

    /**
     * Show all the goals in the goalPane: first show incomplete goals in ascending
     * order of deadline then show complete goals in descending order of completion
     * time. The order is sorted by the database query.
     */
    public void showGoal() {
	goalList.getChildren().clear();
	/** change database name when not testing **/
	EventManagement eventManagement = new EventManagement();
	List<Event> goalDBList = eventManagement.getEventList(1, "testDB.db", null);
	for (Event goal : goalDBList) {
	    goalList.getChildren().add(goalView(goal));
	}
	goalList.getChildren().add(new Label("Total: " + goalDBList.size() + " goals"));
    }

    /**
     * Create single goal view use HBox and default width
     * 
     * @param event
     * @return
     */
    public HBox goalView(Event event) {
	boolean goalStatus = event.isStatus();
	String goalName = event.getName();
	LocalDate goalDdl = event.getDate();
	Circle statusCircle = new Circle(8);
	HBox goalView = new HBox(20);
	String goalStatusCell;
	/**
	 * Set the userData of the goalView to the goal object, so that the goal object
	 * can be retrieved when the goalView is clicked
	 */
	ContextMenu contextMenu = EventDialog.createContextMenu(a -> addGoal(), a -> deleteGoal(event),
		a -> editGoal(event));
	goalView.setOnContextMenuRequested(e -> contextMenu.show(goalView, e.getScreenX(), e.getScreenY()));

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
	    long days = ChronoUnit.DAYS.between(goalDdl, LocalDate.now());
	    goalStatusCell = days + " days passed";

	} else {// if the goal is incomplete and the ddl is not passed
	    statusCircle.setFill(Color.WHITE);
	    // long days = LocalDate.now().until(goalDdl).getDays();
	    // getDays will not calculate the days correctly between different months
	    long days = ChronoUnit.DAYS.between(LocalDate.now(), goalDdl);
	    goalStatusCell = days + " days left";
	}
	/**
	 * add CheckBox for change goal status, set listener to update the status in
	 * database
	 */
	CheckBox goalStatusBox = new CheckBox();
	goalStatusBox.setSelected(event.isStatus());
	goalStatusBox.setOnAction(e -> {
	    int newStatus = goalStatusBox.isSelected() ? 1 : 0;
	    TaskPane.eventManager.updateEstatus(event, newStatus);
	    updateGoal();
	});

	StackPane circleStack = new StackPane(statusCircle);
	circleStack.setPrefWidth(hboxWidth[0]);
	circleStack.setAlignment(Pos.CENTER);
	goalView.getChildren().add(circleStack);
	String[] goalInfo = { goalName, goalStatusCell };

	/**
	 * Add goal name and status text to the goalView
	 */
	for (int i = 0; i < 2; i++) {
	    HBox goalHbox = new HBox(15, new Label(goalInfo[i]));
	    goalHbox.getChildren().add(goalStatusBox);
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
     * Add new goal via EventDialog
     */
    private void addGoal() {
	EventDialog dialog = new EventDialog();
	EventData data = dialog.showAndGetData();
	// Create eventManager object, or the method cannot be invoked
	EventManagement eventManager = new EventManagement();
	if (data != null) {
	    eventManager.createEvent(data.getName(), data.getDate(), 1);
	    updateGoal();
	}
    }

    /**
     * Delete goal
     * 
     * @param event
     */
    private void deleteGoal(Event event) {
	TaskPane.eventManager.deleteEvent(event);
	updateGoal();
    }

    /**
     * Edit goal
     * 
     * @param event
     */
    private void editGoal(Event event) {
	EventDialog dialog = new EventDialog();
	EventData data = dialog.showAndGetData();
	if (data != null) {
	    TaskPane.eventManager.modifyEvent(event, data.getName(), data.getDate());
	    updateGoal();
	}
    }

}