/**
 * Main.java
 * Author: Li Sheng
 */

package application;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Main class for the planner application TabPane: Task, Goal SplistPane:
 * Calendar, TabPane(Task/Goal)
 */
public class Main extends Application {
    public static String db = "testDB.db";
    public static String url = "PlannerDB.db";

    @Override
    public void start(Stage primaryStage) {
	try {
	    CalendarPane dayPane = new CalendarPane();
	    TabPane tabPane = new TabPane();
	    String[] tabNames = { "Daily Task", "Goal", "Yearly Review" };
	    Pane[] panes = { new TaskPane(), new GoalPane(), new YearPane() };
	    for (int i = 0; i < tabNames.length; i++) {
		Tab tab = new Tab(tabNames[i]);
		tab.setContent(panes[i]);
		tabPane.getTabs().add(tab);
	    }
	    SplitPane splitPane = new SplitPane();
	    splitPane.getItems().addAll(dayPane, tabPane);

	    /**
	     * Set the default position of the divider
	     */
	    Scene scene = new Scene(splitPane, 700, 300);
	    primaryStage.setTitle("Planner (V1.0)");
	    primaryStage.setScene(scene);
	    primaryStage.show();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public static void main(String[] args) {
	Path path = Paths.get(db);
	// SQLiteDB.createDatabase("PlannerDB");
	// test the database: will delete and recreate every time
	try {
	    boolean deleted = Files.deleteIfExists(path);
	    if (deleted) {
		System.out.println("Database deleted successfully.");
	    } else {
		System.out.println("Database file not found.");
	    }
	} catch (Exception e) {
	    System.out.println("Error deleting the database file.");
	    e.printStackTrace();
	}
	SQLiteDB.testDB(db); // test the database

	launch(args);
    }
}
