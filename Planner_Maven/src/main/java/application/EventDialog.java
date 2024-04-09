/**
 * @author Li Sheng
 */

package application;

import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * EventDialog class for dialog window to input event data
 */
public class EventDialog extends Dialog {
    /**
     * Constructor for EventDialog
     */
    public EventDialog() {
	setTitle("Event Dialog");
	setHeaderText("Enter details for the event:");

	GridPane grid = new GridPane();
	grid.setHgap(10);
	grid.setVgap(10);

	TextField nameField = new TextField();
	Label name = new Label("Event Name:");
	grid.add(name, 1, 0);
	Label date = new Label("Date:");
	grid.add(date, 1, 1);

	DatePicker datePicker = new DatePicker();
	grid.add(nameField, 2, 0);
	grid.add(datePicker, 2, 1);

	getDialogPane().setContent(grid);

	// Add button types
	getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

	// Request focus on the name field by default.
	Platform.runLater(() -> nameField.requestFocus());

	// Convert the result when the OK button is clicked.
	setResultConverter(dialogButton -> {
	    if (dialogButton == ButtonType.OK) {
		return new EventData(nameField.getText(), datePicker.getValue());
	    }
	    return null;
	});
    }

    /**
     * Method to show the dialog and get the input data
     *
     * @return the event data
     */
    public EventData showAndGetData() {
	return (EventData) showAndWait().orElse(null);
    }

    /**
     * Method to create a context menu for add, delete and edit an event
     * 
     * @param onAdd
     * @param onDelete
     * @param onEdit
     * @return
     */
    public static ContextMenu createContextMenu(Consumer<ActionEvent> onAdd, Consumer<ActionEvent> onDelete,
	    Consumer<ActionEvent> onEdit) {
	ContextMenu contextMenu = new ContextMenu();
	MenuItem addItem = new MenuItem("Add");
	addItem.setOnAction(onAdd::accept);
	MenuItem deleteItem = new MenuItem("Delete");
	deleteItem.setOnAction(onDelete::accept);
	MenuItem editItem = new MenuItem("Edit");
	editItem.setOnAction(onEdit::accept);
	contextMenu.getItems().addAll(addItem, deleteItem, editItem);
	return contextMenu;
    }

    /**
     * context menu ONLY for add, this method is used in empty pane area, when there
     * is no event listed
     * 
     * @return
     */
    public static ContextMenu addContextMenu(Consumer<ActionEvent> onAdd) {
	ContextMenu contextMenu = new ContextMenu();
	MenuItem addItem = new MenuItem("Add");
	contextMenu.getItems().add(addItem);
	addItem.setOnAction(onAdd::accept);
	return contextMenu;
    }

}