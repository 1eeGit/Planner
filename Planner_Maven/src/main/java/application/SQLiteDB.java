/**
 * @author Li Sheng
 */
package application;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * The Class SQLiteDB includes methods related to database management:
 * createDatabase, createTable, insertEvent, updateEvent, deleteEvent,
 * insertTask, updateTask, insertActivity, updateActivity, testDB.
 */
public class SQLiteDB {
    /** SQLite connection string */
    private final static String SQLITE_CONNECTION_PREFIX = "jdbc:sqlite:";
    public static String url = SQLITE_CONNECTION_PREFIX + Main.db;

    /**
     * Creates the database if it does not exist, else connects to the existing
     * database.
     *
     * @param fileName
     */
    public static void createDatabase(String fileName) {
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
     */
    public static void createTable() {
	String createEventTable = "CREATE TABLE IF NOT EXISTS event (" + "	id INTEGER PRIMARY KEY AUTOINCREMENT,"
		+ "	name VARCHAR(255) NOT NULL," + "	status INT DEFAULT 0," + "date DATE NOT NULL, "
		+ "	type INT NOT NULL);";

	String createTaskTable = "CREATE TABLE IF NOT EXISTS task (" + "	id INT," + "    actID INT,"
		+ "    PRIMARY KEY (id)," + "    FOREIGN KEY (id) REFERENCES event(id) ON DELETE CASCADE);";

	String createActivityTable = "CREATE TABLE IF NOT EXISTS activity (" + "	id INT," + "    actStime TIME,"
		+ "    actEtime TIME," + "    taskID INT," + "    PRIMARY KEY (id),"
		+ "    FOREIGN KEY (id) REFERENCES event(id) ON DELETE CASCADE,"
		+ "	FOREIGN KEY (taskID) REFERENCES task(id));";

	String createYearTable = "CREATE TABLE IF NOT EXISTS year (" + "	dayID INT," + "    taskNum INT,"
		+ "    PRIMARY KEY (dayID));";

	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS1 = conn.prepareStatement(createEventTable);
		PreparedStatement PS2 = conn.prepareStatement(createTaskTable);
		PreparedStatement PS3 = conn.prepareStatement(createActivityTable);
		PreparedStatement PS4 = conn.prepareStatement(createYearTable);) {
	    PS1.executeUpdate();
	    PS2.executeUpdate();
	    PS3.executeUpdate();
	    PS4.executeUpdate();
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
     * 
     */
    public static void insertEvent(String name, int status, String date, int type) {
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
     * Update event name and date in the event table of URL database. Type is not
     * allowed to be modified.
     * 
     * @param id
     * @param name
     * @param date
     */
    public static void updateEvent(int id, String name, String date, String type) {
	String updateSQL = "UPDATE event SET name = ?, date = ? WHERE id = ?";
	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS = conn.prepareStatement(updateSQL)) {
	    PS.setString(1, name);
	    PS.setString(2, date);
	    PS.setInt(3, id);
	    PS.executeUpdate();
	    conn.commit();
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * Update ONLY event status in the event table of URL database.
     * 
     * @param id
     * @param status
     */
    public static void updateEstatus(int status, int id) {
	String updateStatus = "UPDATE event SET status = ? WHERE id = ?";
	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS = conn.prepareStatement(updateStatus)) {
	    PS.setInt(1, status);
	    PS.setInt(2, id);
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
     */
    public static void deleteEvent(int id) {
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
     * 
     */
    public static void insertTask(int id, int actID) {
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
     */
    public static void updateTask(int id, int actID) {
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
     */
    public static void insertActivity(int id, String actStime, String actEtime, int taskID) {
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
     */
    public static void updateActivity(int id, String actStime, String actEtime, int taskID) {
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
     * Get the last event ID
     * 
     * @return
     */
    public static int getEventID() {
	String getEventID = "SELECT MAX(id) FROM event";
	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS = conn.prepareStatement(getEventID);
		ResultSet RS = PS.executeQuery()) {
	    if (RS.next()) {
		return RS.getInt(1);
	    }
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
	return 0;
    }

    /**
     * Check if the year table is, or needed to be updated difference = 0: no need
     * to update; difference > 0: update; <0: DROP & RECREATE year table, for task
     * deleted.
     * 
     * @return
     */
    public static int updateYearCheck() {
	int difference = 0;
	// calculate the difference between expectNum and realNum
	String SQL = "SELECT ((SELECT COUNT(DISTINCT e.date)  FROM event e WHERE type=2 AND strftime('%Y', e.date) = '2024' )- (SELECT COUNT(*)  FROM year y))AS difference;";
	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS = conn.prepareStatement(SQL);
		ResultSet RS = PS.executeQuery()) {
	    if (RS.next()) {
		difference = RS.getInt("difference");
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return difference;
    }

    /**
     * Get and update data in year table
     */
    public static int[] updateData(int difference) {
	int[] result = new int[2];
	String SQL = "SELECT COUNT(*) AS num_in_DAY, e.date AS date FROM event e "
		+ "WHERE TYPE = 2 AND strftime('%Y', e.date) = '2024' " + "GROUP BY date " + "ORDER BY date DESC "
		+ "LIMIT ?;";
	try (Connection conn = DriverManager.getConnection(url); PreparedStatement PS = conn.prepareStatement(SQL);) {
	    PS.setInt(1, difference);
	    ResultSet RS = PS.executeQuery();
	    while (RS.next()) {
		// convert date type to LocalDate to match format
		String dateString = RS.getString("date");
		LocalDate date = LocalDate.parse(dateString);
		// System.out.println(date);
		int dayID = date.getDayOfYear();
		int taskNum = RS.getInt("num_in_DAY");
		// System.out.println(taskNum);
		/**
		 * insertYear(dayID, taskNum); cannot insert inside this method: [SQLITE_BUSY]
		 * The database file is locked (database is locked)
		 */
		result[0] = dayID;
		result[1] = taskNum;
	    }
	} catch (SQLException e) {
	    e.printStackTrace();
	}
	return result;
    }

    /**
     * Insert data into year table if dayID not exist; else update the taskNum.
     * 
     * @param dayID
     * @param taskNum
     */
    public static void updateYearData(int dayID, int taskNum) {
	String insertOrUpdateSQL = "INSERT INTO year (dayID, taskNum) VALUES (?, ?) ON CONFLICT(dayID) DO UPDATE SET taskNum = taskNum + excluded.taskNum;";
	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement pstmt = conn.prepareStatement(insertOrUpdateSQL)) {
	    pstmt.setInt(1, dayID);
	    pstmt.setInt(2, taskNum);
	    pstmt.executeUpdate();
	} catch (SQLException e) {
	    System.out.println(e.getMessage());
	}
    }

    /**
     * Get the number of tasks for day[i]
     * 
     * @param dayID
     * @return
     */
    public static int getTaskNum(int dayID) {
	String SQL = "SELECT taskNum FROM year WHERE dayID = ?";
	try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(SQL)) {
	    pstmt.setInt(1, dayID);
	    try (ResultSet rs = pstmt.executeQuery()) {
		if (rs.next()) {
		    return rs.getInt("taskNum");
		}
	    }
	} catch (SQLException e) {
	    System.out.println("Error retrieving task number: " + e.getMessage());
	    e.printStackTrace();
	}
	return 0;
    }

    /**
     * Drop and recreate year table
     */
    public static void dropANDcreateYear() {
	String dropYearTable = "DROP TABLE year";
	String createYearTable = "CREATE TABLE IF NOT EXISTS year (" + "	dayID INT," + "    taskNum INT,"
		+ "    PRIMARY KEY (dayID));";
	try (Connection conn = DriverManager.getConnection(url);
		PreparedStatement PS1 = conn.prepareStatement(dropYearTable);
		PreparedStatement PS2 = conn.prepareStatement(createYearTable);) {
	    PS1.executeUpdate();
	    PS2.executeUpdate();
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
	createTable();
	/** test data: Goal 1-3; Task 4-6; Activity 7-9; task matches with activity */
	String today = String.valueOf(java.time.LocalDate.now());
	insertEvent("Goal 1", 0, "2023-11-30", 1);
	insertEvent("Goal 2", 0, "2024-04-10", 1);
	insertEvent("Goal 3", 1, today, 1);
	// task data for testing:3,5,3+1,1,1,1
	insertEvent("Java project submission", 1, "2024-04-09", 2);
	insertEvent("Java project documentation", 1, "2024-04-09", 2);
	insertEvent("Java ", 1, "2024-04-09", 2);

	insertEvent("Java peer-review submission", 1, "2024-04-17", 2);
	insertEvent("Java peer-review documentation", 1, "2024-04-17", 2);
	insertEvent("homework 1 ", 1, "2024-04-17", 2);
	insertEvent("homework 2 ", 1, "2024-04-17", 2);
	insertEvent("homework 3 ", 1, "2024-04-17", 2);

	insertEvent("other", 1, today, 2);
	insertEvent("Task 1", 0, today, 2);
	insertEvent("Task 2", 0, today, 2);

	insertEvent("exam ", 1, "2024-05-17", 2);
	insertEvent("exam ", 1, "2024-12-31", 2);
	insertEvent("exam ", 1, "2024-08-31", 2);
	insertEvent("exam ", 1, "2024-09-01", 2);

	insertTask(4, 7);
	insertTask(5, 8);
	insertTask(6, 9);
	// note that activities are not showing in UI, only in database for now.
	insertEvent("Activity 1", 0, "2023-11-30", 3);
	insertEvent("Activity 2", 1, "2024-04-10", 3);
	insertEvent("Activity 3", 0, today, 3);
	insertActivity(7, "10:00", "12:00", 4);
	insertActivity(8, "14:00", "16:00", 5);
	insertActivity(9, "18:00", "20:00", 6);
    }

}