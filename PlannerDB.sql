-- NOTE: this sql file is written in MySql, in java project the driver is SQLite, 
-- thus maybe some changes are made when implementing database in java project,
-- but the main database design still aligns with this file.

-- create database
-- CREATE DATABASE planner_database;
USE planner_database;
-- drop all tables if needed
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE event;
DROP TABLE task;
DROP TABLE activity;
-- create Event table
-- event id is unique and automatically increased inside event table
CREATE TABLE IF NOT EXISTS event (
	id INT AUTO_INCREMENT PRIMARY KEY, -- SQLite: id INTEGER PRIMARY KEY AUTOINCREMENT -- INT cannot AUTOINCREMENT
	name VARCHAR(255) NOT NULL,
	status INT DEFAULT 0,  -- 0: incomplete 1: complete
	date DATE NOT NULL, -- format: YYYY-MM-DD
	type INT NOT NULL -- 1: goal, 2: for task, 3: for activity
);
-- event example data
-- insert event 
INSERT INTO event(id,name,status,date,type)
	VALUES(id,'Java project',1,'2024-03-21',1);
INSERT INTO event(id,name,status,date,type)
	VALUES(id,'Java project submission',0,'2024-04-7',2);
INSERT INTO event(id,name,status,date,type)
	VALUES(id,'Java project peer review',0,'2024-04-10',3);
INSERT INTO event(id,name,status,date,type)
	VALUES(id,'for delete test',0,'2024-04-10',3);
-- update
UPDATE event SET name='updated Java proj' WHERE id=1;
-- delete 
DELETE FROM event WHERE id= 4;

-- NOTE: when creating new task or activity, the event is inserted first, 
-- and id is generated for inserting to task and activity table.
-- item deleted in event will also be deleted automatically in task table

-- create task table
CREATE TABLE IF NOT EXISTS task (
	id INT,
    actID INT,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES event(id) ON DELETE CASCADE
);


-- create activity table and add FK
-- item deleted in event will also be deleted automatically in activity table
CREATE TABLE IF NOT EXISTS activity (
	id INT,
    actStime TIME,
    actEtime TIME,
    taskID INT,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES event(id) ON DELETE CASCADE,
	FOREIGN KEY (taskID) REFERENCES task(id)
);
-- add FK for task table
-- these constrains will not be needed in SQLite
ALTER TABLE task ADD CONSTRAINT FKtask FOREIGN KEY (actID) REFERENCES activity(ID);
ALTER TABLE task ADD UNIQUE (actID);

-- retrieve all goals
-- first show incomplete goals in ascending order of deadline
-- then show complete goals in descending order of completion time.

-- v1: can only union same number groups
(SELECT * FROM event WHERE type= 1 AND status = 0 ORDER BY date ASC)
UNION
(SELECT * FROM event WHERE type= 1 AND status = 1 ORDER BY date DESC);
-- v2: CASE(IF-ELSE)  WHEN ... THEN ... ELSE ... END
SELECT * FROM event WHERE type =1 ORDER BY status, 
CASE WHEN status =0 THEN date END ASC,
CASE WHEN status =1 THEN date END DESC;

