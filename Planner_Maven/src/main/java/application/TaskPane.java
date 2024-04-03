/**
 * TaskPane.java
 * Author: Li Sheng
 */
package application;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * TaskPane class for goal view in the right side Tab of the UI
 */
public class TaskPane extends GridPane {

    ObservableList<Task> taskList = FXCollections.observableArrayList();

    public TaskPane() {
	GridPane taskPane = new GridPane();
	HBox taskHeader = new HBox(20);
	Label header = new Label(LocalDate.now().toString());
	header.setStyle("-fx-font-size: 15px; -fx-font-family: Arial; -fx-text-fill: BLACK;");
	taskHeader.getChildren().add(header);
	taskHeader.setPrefWidth(180);
	taskHeader.setAlignment(Pos.CENTER);
	this.add(taskHeader, 1, 0);
	updateTaskPane();
    }

    /**
     * create single goal view use HBox and default width 15 CheckBox shows the
     * status of the task
     * 
     * @param task
     */
    public HBox taskView(Task task) {
	CheckBox taskStatus = new CheckBox();
	taskStatus.setSelected(task.isStatus());
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
	return taskView;
    }

    /**
     * Show all the tasks for a single day in the taskPane
     */
    public void showTask() {
	GridPane taskListPane = new GridPane();
	this.getChildren().remove(taskListPane);
	int taskRow = 0;
	for (Task task : taskList) {
	    HBox taskView = taskView(task);
	    taskListPane.add(taskView, 1, taskRow++);
	}
	this.add(taskListPane, 1, 1);
    }

    /**
     * Update the taskPane
     */
    public void updateTaskPane() {
	/**
	 * Test data for task
	 */
	taskList.addAll(new Task(1, "Task1", false, LocalDate.now(), 1),
		new Task(2, "Task2", false, LocalDate.of(2024, 3, 31), 0),
		new Task(3, "Task3", true, LocalDate.of(2024, 3, 31), 0),
		new Task(4, "Task4", true, LocalDate.now(), 2));
	showTask();
    }
}