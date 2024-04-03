/**
 * @author Li Sheng
 */

package application;

import java.time.LocalDate;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

// TODO: Auto-generated Javadoc
/**
 * The Class EventDialog.
 */
public class EventDialog extends Dialog {
    /**
     *  
     */
    public EventDialog() {
	setTitle("Event Dialog");
	setHeaderText("Enter details for the event:");

	GridPane grid = new GridPane();
	grid.setHgap(10);
	grid.setVgap(10);

	TextField nameField = new TextField();
	nameField.setPromptText("Name");

	DatePicker datePicker = new DatePicker();
	grid.add(nameField, 1, 0);
	grid.add(datePicker, 1, 1);

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
     * Show and get data.
     *
     * @return the event data
     */
// Method to show the dialog and get the input data
    public EventData showAndGetData() {
	return (EventData) showAndWait().orElse(null);
    }
}

class EventData {
    private String name;
    private LocalDate date;

    public EventData(String name, LocalDate date) {
	this.name = name;
	this.date = date;
    }

    public String getName() {
	return name;
    }

    public LocalDate getDate() {
	return date;
    }
}
