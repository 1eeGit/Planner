/**
 * YearPane
 * @author  Li Sheng
 */
package application;

import java.time.LocalDate;
import java.time.Year;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * YearPane for daily event visualization
 */
public class YearPane extends GridPane {
    private int year;
    private int dayNumber;
    private GridPane reviewPane = new GridPane();
    public static YearPane yearPane = new YearPane();

    /**
     * Default constructor
     */
    public YearPane() {
	reviewPane.setPrefSize(200, 150);
	year = LocalDate.now().getYear();
	Year y = Year.of(year);
	dayNumber = y.length();
	// System.out.println("YearPane: " + year + " has " + dayNumber + " days.");
	this.add(reviewPane, 1, 1);
	showYear(); // first update database, then draw the cellUI
    }

    /**
     * Draw the all day cells (365 or 366), color based on the amount of tasks
     * within the day
     */
    public void drawCell() {
	reviewPane.getChildren().clear();
	for (int i = 1; i <= dayNumber; i++) {
	    Label label = new Label();
	    label.setMinSize(12, 12);
	    // set light, lime and dark green for day[i]
	    int taskNum = SQLiteDB.getTaskNum(i);
	    if (taskNum > 0) {
		label.setStyle("-fx-border-color: lightgray; -fx-background-color: " + setColor(taskNum) + ";");
		System.out.println(taskNum + " &&& " + setColor(taskNum));
	    } else {
		label.setStyle("-fx-border-color: lightgray; ");
	    }
	    reviewPane.add(label, (i - 1) % 31 + 1, (i - 1) / 31);
	}
    }

    /**
     * 
     */
    public void showYear() {
	int difference = SQLiteDB.updateYearCheck();
	if (difference > 0) {
	    int[] result = SQLiteDB.updateData(difference);
	    SQLiteDB.updateYearData(result[0], result[1]);
	    showYear();
	} else if (difference == 0) {
	    System.out.println("YearPane is already up-to-date.");
	} else { // difference < 0: task is deleted
	    System.out.println("YearPane: RECREATED.");
	    SQLiteDB.dropANDcreateYear();
	    showYear();
	}
	drawCell();
    };

    /**
     * Set the color of the cell based on the number of tasks: >= 1: light green; >=
     * 3: lime; >= 5: dark green.
     * 
     * @param taskNum
     * @return color code
     */
    public String setColor(int taskNum) {
	String colorCode = null;
	if (taskNum >= 5) {
	    colorCode = "#006400";
	} else if (taskNum >= 3) {
	    colorCode = "#90ee90";
	} else if (taskNum >= 1) {
	    colorCode = "#32cd32";
	}
	return colorCode;
    }

}
