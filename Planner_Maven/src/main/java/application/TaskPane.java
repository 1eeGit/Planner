/**
 * TaskPane.java
 * Author: Li Sheng
 */
package application;

import java.time.LocalDate;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * TaskPane class for goal view in the right side Tab of the UI
 */
public class TaskPane extends GridPane {
    static EventManagement eventManager = new EventManagement();
    static GridPane taskListPane = new GridPane();
    static Label header = new Label(LocalDate.now().toString());
    static HBox taskHeader = new HBox(20);

    /**
     * Default constructor
     */
    public TaskPane() {
	header.setStyle("-fx-font-size: 15px; -fx-font-family: Arial; -fx-text-fill: BLACK;");
	taskHeader.setPrefWidth(180);
	taskHeader.setAlignment(Pos.CENTER);
	// set default size of the taskListPane, or addContextMenu will not work in
	// empty area
	taskListPane.setPrefSize(300, 200);
	this.getChildren().clear();
	this.add(taskHeader, 1, 0);
	this.add(taskListPane, 1, 1);
	ContextMenu contextMenu = EventDialog.addContextMenu(a -> addTask());
	taskListPane.setOnContextMenuRequested(e -> {
	    // only show addContextMenu when right click on empty area
	    // which means the target is not HBox - taskView
	    if (!(e.getTarget() instanceof HBox)) {
		contextMenu.show(taskListPane, e.getScreenX(), e.getScreenY());
	    }
	});
	updateTask();
    }

    /**
     * create single goal view use HBox and default width 15 CheckBox shows the
     * status of the task
     * 
     * @param task
     */
    public static HBox taskView(Event task) {
	CheckBox taskStatus = new CheckBox();
	taskStatus.setSelected(task.isStatus());
	// set listener to update the status of the task
	taskStatus.setOnAction(e -> {
	    int newStatus = taskStatus.isSelected() ? 1 : 0;
	    eventManager.updateEstatus(task, newStatus);
	});
	HBox cboxHbox = new HBox(15);
	cboxHbox.getChildren().add(taskStatus);
	cboxHbox.setAlignment(Pos.CENTER);
	cboxHbox.setPrefWidth(50);
	Label taskName = new Label(task.getName());
	HBox nameHbox = new HBox(15);
	nameHbox.getChildren().add(taskName);
	nameHbox.setAlignment(Pos.CENTER);
	nameHbox.setPrefWidth(100);
	HBox taskView = new HBox(20);
	taskView.setPrefWidth(180);
	taskView.getChildren().addAll(cboxHbox, nameHbox);

	/**
	 * Set the userData of the goalView to the goal object, so that the goal object
	 * can be retrieved when the goalView is clicked
	 */
	ContextMenu contextMenu = EventDialog.createContextMenu(a -> addTask(), a -> deleteTask(task),
		a -> editTask(task));
	taskView.setOnContextMenuRequested(e -> contextMenu.show(taskView, e.getScreenX(), e.getScreenY()));

	return taskView;
    }

    /**
     * Edit the task
     * 
     * @param event
     */
    private static void editTask(Event event) {
	EventDialog dialog = new EventDialog();
	EventData data = dialog.showAndGetData();
	if (data != null) {
	    eventManager.modifyEvent(event, data.getName(), data.getDate());
	    updateTask();
	}

    }

    private static void deleteTask(Event event) {
	eventManager.deleteEvent(event);
	updateTask();
    }

    private static void addTask() {
	EventDialog dialog = new EventDialog();
	EventData data = dialog.showAndGetData();
	EventManagement eventManager = new EventManagement();
	if (data != null) {
	    eventManager.createEvent(data.getName(), 2, data.getDate());
	    updateTask();
	}
    }

    /**
     * Show all the tasks for a single day in the taskPane
     */
    public static void showTask() {
	taskListPane.getChildren().clear();
	int taskRow = 0;
	/**
	 * show today's tasks by default, updates pane view as static selectedDate
	 * changes
	 */
	LocalDate date = CalendarPane.selectedDate;
	/** change database name when not testing **/
	EventManagement eventManagement = new EventManagement();
	List<Event> taskList = eventManagement.getEventList(2, "testDB.db", date);
	taskHeader.getChildren().clear();
	header = new Label();
	taskHeader.getChildren().add(header);
	for (Event task : taskList) {
	    HBox taskView = taskView(task);
	    taskListPane.add(taskView, 1, taskRow++);
	}
	// show total amount and completion rate???
	header.setText(CalendarPane.selectedDate.toString() + "   Total: " + taskList.size() + " tasks" + " %");
    }

    /**
     * Update the taskPane
     */
    public static void updateTask() {
	showTask();
    }
}