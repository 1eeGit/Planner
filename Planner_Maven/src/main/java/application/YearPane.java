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

    /**
     * Default constructor
     */
    public YearPane() {
	reviewPane.setPrefSize(200, 150);
	year = LocalDate.now().getYear();
	Year y = Year.of(year);
	dayNumber = y.length();
	this.add(reviewPane, 1, 1);
	drawCell();
    }

    /**
     * Draw the all day cells (365 or 366)
     */
    public void drawCell() {
	reviewPane.getChildren().clear();
	for (int i = 1; i <= dayNumber; i++) {
	    Label label = new Label();
	    label.setMinSize(10, 10);
	    label.setStyle("-fx-border-color: lightgray;");
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
	    SQLiteDB.insertYear(result[0], result[1]);
	} else if (difference == 0) {
	    System.out.println("YearPane is already up-to-date.");
	} else if (difference == -1) {
	    System.out.println("Error when update YearPane: expectNum < realNum");
	}
    };

    /**
     * Set the color for day cell
     * 
     * @return
     */
    public int getColor() {

	return 0;

    }

}
