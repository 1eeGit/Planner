/**
 * @author Li Sheng
 */
package application;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The Class SQLiteDB includes methods related to database management:
 * createDatabase, createTable, insertEvent, updateEvent, deleteEvent,
 * insertTask, updateTask, insertActivity, updateActivity, testDB.
 */
public class SQLiteDB {
    /** SQLite connection string */
    private static final String SQLITE_CONNECTION_PREFIX = "jdbc:sqlite:";
    private String url = "jdbc:sqlite:PlannerDB.db";

    /**
     * Creates the database if it does not exist, else connects to the existing
     * database.
     *
     * @param fileName
     */
    public static void createDatabase(String fileName) {
	String url = SQLITE_CONNECTION_PREFIX + fileName;

	try (Connection conn = DriverManager.getConnection(url)) {
	    if (conn != null) {
		DatabaseMetaData meta = conn.getMetaData();
		System.out.println("The driver name is " + meta.getDriverName());
		System.out.println("Database " + fileName + " connected.");
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * Create event, task and activity tables in the URL database. ID is
     * auto-incremented in event table.
     * 
     * @param url
     */
    public static void createTable(String url) {
	String createEventTable = "CREATE TABLE IF NOT EXISTS event (" + "	id INTEGER PRIMARY KEY AUTOINCREMENT,"
		+ "	name VARCHAR(255) NOT NULL," + "	status INT DEFAULT 0," + "date DATE NOT NULL, "
		+ "	type INT NOT NULL);";

	String createTaskTable = "CREATE TABLE IF NOT EXISTS task (" + "	id INT," + "    actID INT,"
		+ "    PRIMARY KEY (id)," + "    FOREIGN KEY (id) REFERENCES event(id) ON DELETE CASCADE);";

	String createActivityTable = "CREATE TABLE IF NOT EXISTS activity (" + "	id INT," + "    actStime TIME,"
		+ "    actEtime TIME," + "    taskID INT," + "    PRIMARY KEY (id),"
		+ "    FOREIGN KEY (id) REFERENCES event(id) ON DELETE CASCADE,"
		+ "	FOREIGN KEY (taskID) REFERENCES task(id));";

	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS1 = conn.prepareStatement(createEventTable);
		PreparedStatement PS2 = conn.prepareStatement(createTaskTable);
		PreparedStatement PS3 = conn.prepareStatement(createActivityTable);) {
	    PS1.executeUpdate();
	    PS2.executeUpdate();
	    PS3.executeUpdate();
	    conn.commit();
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * Insert event into the event table of URL database. id is auto-incremented.
     * 
     * @param name
     * @param status
     * @param date
     * @param type
     * @param url
     */
    public static void insertEvent(String name, int status, String date, int type, String url) {
	String insertSQL = "INSERT INTO event (name, status, date, type) VALUES (?,?,?,?)";
	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS = conn.prepareStatement(insertSQL)) {
	    PS.setString(1, name);
	    PS.setInt(2, status);
	    PS.setString(3, date);
	    PS.setInt(4, type);
	    PS.executeUpdate();
	    conn.commit();
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * Update event in the event table of URL database.
     * 
     * @param id
     * @param name
     * @param status
     * @param date
     * @param type
     * @param url
     */
    public static void updateEvent(int id, String name, int status, String date, String type, String url) {
	String updateSQL = "UPDATE event SET name = ?, status = ?, date = ?, type = ? WHERE id = ?";
	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS = conn.prepareStatement(updateSQL)) {
	    PS.setString(1, name);
	    PS.setInt(2, status);
	    PS.setString(3, date);
	    PS.setString(4, type);
	    PS.setInt(5, id);
	    PS.executeUpdate();
	    conn.commit();
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * Delete event from the URL database: item deleted in event will also be
     * deleted automatically in activity and task table.
     * 
     * @param id
     * @param url
     */
    public static void deleteEvent(int id, String url) {
	String deleteSQL = "DELETE FROM event WHERE id = ?";
	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS = conn.prepareStatement(deleteSQL)) {
	    PS.setInt(1, id);
	    PS.executeUpdate();
	    conn.commit();
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * Insert task into the task table of URL database.
     * 
     * @param id
     * @param actID
     * @param url
     */
    public static void insertTask(int id, int actID, String url) {
	String insertSQL = "INSERT INTO task (id, actID) VALUES (?,?)";
	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS = conn.prepareStatement(insertSQL)) {
	    PS.setInt(1, id);
	    PS.setInt(2, actID);
	    PS.executeUpdate();
	    conn.commit();
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * Update task in the task table of URL database.
     * 
     * @param id
     * @param actID
     * @param url
     */
    public static void updateTask(int id, int actID, String url) {
	String updateSQL = "UPDATE task SET actID = ? WHERE id = ?";
	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS = conn.prepareStatement(updateSQL)) {
	    PS.setInt(1, actID);
	    PS.setInt(2, id);
	    PS.executeUpdate();
	    conn.commit();
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * Insert activity into the activity table of URL database.
     * 
     * @param id
     * @param actStime
     * @param actEtime
     * @param taskID
     * @param url
     */
    public static void insertActivity(int id, String actStime, String actEtime, int taskID, String url) {
	String insertSQL = "INSERT INTO activity (id, actStime, actEtime, taskID) VALUES (?,?,?,?)";
	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS = conn.prepareStatement(insertSQL)) {
	    PS.setInt(1, id);
	    PS.setString(2, actStime);
	    PS.setString(3, actEtime);
	    PS.setInt(4, taskID);
	    PS.executeUpdate();
	    conn.commit();
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * Update activity in the activity table of URL database.
     * 
     * @param id
     * @param actStime
     * @param actEtime
     * @param taskID
     * @param url
     */
    public static void updateActivity(int id, String actStime, String actEtime, int taskID, String url) {
	String updateSQL = "UPDATE activity SET actStime = ?, actEtime = ?, taskID = ? WHERE id = ?";
	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS = conn.prepareStatement(updateSQL)) {
	    PS.setString(1, actStime);
	    PS.setString(2, actEtime);
	    PS.setInt(3, taskID);
	    PS.setInt(4, id);
	    PS.executeUpdate();
	    conn.commit();
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * Test database
     * 
     * @param db "database name.db"
     */
    public static void testDB(String db) {
	createDatabase(db);
	String url = SQLITE_CONNECTION_PREFIX + db;
	createTable(url);
	/** test data: Goal 1-3; Task 4-6; Activity 7-9; task matches with activity */
	String today = String.valueOf(java.time.LocalDate.now());
	insertEvent("Goal 1", 0, "2023-11-30", 1, url);
	insertEvent("Goal 2", 0, "2024-04-10", 1, url);
	insertEvent("Goal 3", 1, today, 1, url);
	insertEvent("Task 1", 0, "2023-11-30", 2, url);
	insertEvent("Task 2", 1, "2024-04-10", 2, url);
	insertEvent("Task 3", 0, today, 2, url);
	insertEvent("Activity 1", 0, "2023-11-30", 3, url);
	insertEvent("Activity 2", 1, "2024-04-10", 3, url);
	insertEvent("Activity 3", 0, today, 3, url);
	insertTask(4, 7, url);
	insertTask(5, 8, url);
	insertTask(6, 9, url);
	insertActivity(7, "10:00", "12:00", 4, url);
	insertActivity(8, "14:00", "16:00", 5, url);
	insertActivity(9, "18:00", "20:00", 6, url);
    }
}