package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
	try {
	    calendarPane dayPane = new calendarPane();
	    TabPane tabPane = new TabPane();
	    Tab tab_task = new Tab("Task");
	    StackPane pane_task = new StackPane();
	    tab_task.setContent(pane_task);
	    // Tab tab_calendar = new Tab("Calendar");
	    // tab_calendar.setContent(dayPane);
	    Tab tab_goal = new Tab("Goal");
	    tabPane.getTabs().addAll(tab_task, tab_goal);
	    SplitPane splitPane = new SplitPane();
	    splitPane.getItems().addAll(dayPane, tabPane);

	    Scene scene = new Scene(splitPane, 500, 300);
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
