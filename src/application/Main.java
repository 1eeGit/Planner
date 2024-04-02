/**
 * Main.java
 * Author: Li Sheng
 */
package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * Main class for the planner application TabPane: Task, Goal SplistPane:
 * Calendar, TabPane(Task/Goal)
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
	try {
	    CalendarPane dayPane = new CalendarPane();
	    TabPane tabPane = new TabPane();
	    Tab tab_task = new Tab("Task");
	    TaskPane taskPane = new TaskPane();
	    tab_task.setContent(taskPane);
	    Tab tab_goal = new Tab("Goal");
	    GoalPane goalPane = new GoalPane();
	    tab_goal.setContent(goalPane);
	    tabPane.getTabs().addAll(tab_task, tab_goal);
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
	launch(args);

    }
}
