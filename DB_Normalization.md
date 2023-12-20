# NORMALIZATION

Normalization is done mainly to reduce data redundancy and improve efficiency of the database.
1NF- A table is in 1NF if it contains only atomic values
2NF- A table is in 2NF if it already in 1NF and all non key values are completely dependent on primary key
3NF- A table is in 3NF if it already in 2NF and there are no transitive dependencies
4NF- A table is in 4NF if it already in 3NF and multivalue dependecies are eliminated
5NF- A table is in 5NF if it already in 4NF and join dependencies are eliminated

Example for Normalization:

-- Create the initial table
CREATE TABLE StudentCourses (
Student_ID INT,
Course_ID INT,
Instructor VARCHAR(50),
Office_Location VARCHAR(50),
PRIMARY KEY (Student_ID, Course_ID)
);

-- Insert sample data
INSERT INTO StudentCourses VALUES
(101, 1, 'Dr. Smith', 'Room 101'),
(101, 2, 'Dr. Johnson', 'Room 102'),
(102, 1, 'Dr. Brown', 'Room 103'),
(102, 2, 'Dr. Johnson', 'Room 102'),
(103, 3, 'Dr. White', 'Room 104');

-- 1NF Table: StudentCourses_1NF
CREATE TABLE StudentCourses_1NF (
Student_ID INT,
Course VARCHAR(50),
Instructor VARCHAR(50),
Office_Location VARCHAR(50),
PRIMARY KEY (Student_ID, Course)
);

-- Copy data to 1NF table
INSERT INTO StudentCourses_1NF (Student_ID, Course, Instructor, Office_Location)
SELECT Student_ID, CASE Course_ID WHEN 1 THEN 'Math' WHEN 2 THEN 'Physics' WHEN 3 THEN 'Chemistry' END, Instructor, Office_Location
FROM StudentCourses;

-- 2NF Table: Students
CREATE TABLE Students (
Student_ID INT PRIMARY KEY
);

-- 2NF Table: Courses
CREATE TABLE Courses (
Course_ID INT PRIMARY KEY,
Course VARCHAR(50)
);

-- 2NF Table: StudentCourses_2NF
CREATE TABLE StudentCourses_2NF (
Student_ID INT,
Course_ID INT,
Instructor VARCHAR(50),
Office_Location VARCHAR(50),
PRIMARY KEY (Student_ID, Course_ID),
FOREIGN KEY (Student_ID) REFERENCES Students(Student_ID),
FOREIGN KEY (Course_ID) REFERENCES Courses(Course_ID)
);

-- Copy data to 2NF tables
INSERT INTO Students (Student_ID)
SELECT DISTINCT Student_ID FROM StudentCourses_1NF;

INSERT INTO Courses (Course_ID, Course)
SELECT DISTINCT CASE WHEN Course = 'Math' THEN 1 WHEN Course = 'Physics' THEN 2 WHEN Course = 'Chemistry' THEN 3 END, Course
FROM StudentCourses_1NF;

INSERT INTO StudentCourses_2NF (Student_ID, Course_ID, Instructor, Office_Location)
SELECT s.Student_ID, c.Course_ID, sc1NF.Instructor, sc1NF.Office_Location
FROM StudentCourses_1NF sc1NF
JOIN Students s ON sc1NF.Student_ID = s.Student_ID
JOIN Courses c ON sc1NF.Course = c.Course;

-- 3NF Table: Instructors
CREATE TABLE Instructors (
Instructor_ID INT PRIMARY KEY,
Instructor_Name VARCHAR(50),
Office_Location VARCHAR(50)
);

-- 3NF Table: StudentCourses_3NF
CREATE TABLE StudentCourses_3NF (
Student_ID INT,
Course_ID INT,
Instructor_ID INT,
PRIMARY KEY (Student_ID, Course_ID),
FOREIGN KEY (Student_ID) REFERENCES Students(Student_ID),
FOREIGN KEY (Course_ID) REFERENCES Courses(Course_ID),
FOREIGN KEY (Instructor_ID) REFERENCES Instructors(Instructor_ID)
);

-- Copy data to 3NF tables
INSERT INTO Instructors (Instructor_ID, Instructor_Name, Office_Location)
SELECT DISTINCT sc2NF.Instructor, sc2NF.Instructor, sc2NF.Office_Location
FROM StudentCourses_2NF sc2NF;

INSERT INTO StudentCourses_3NF (Student_ID, Course_ID, Instructor_ID)
SELECT sc2NF.Student_ID, sc2NF.Course_ID, i.Instructor_ID
FROM StudentCourses_2NF sc2NF
JOIN Instructors i ON sc2NF.Instructor = i.Instructor_Name;

-- 4NF Table: OfficeHours
CREATE TABLE OfficeHours (
Instructor_ID INT,
Day VARCHAR(15),
PRIMARY KEY (Instructor_ID, Day),
FOREIGN KEY (Instructor_ID) REFERENCES Instructors(Instructor_ID)
);

-- Copy data to 4NF table
INSERT INTO OfficeHours (Instructor_ID, Day)
VALUES
(1, 'Monday'), (1, 'Wednesday'),
(2, 'Tuesday'), (2, 'Thursday');

-- 5NF Table: OfficePreferences
CREATE TABLE OfficePreferences (
Instructor_ID INT,
Office_Location VARCHAR(50),
PRIMARY KEY (Instructor_ID),
FOREIGN KEY (Instructor_ID) REFERENCES Instructors(Instructor_ID)
);

-- Copy data to 5NF table
INSERT INTO OfficePreferences (Instructor_ID, Office_Location)
VALUES
(1, 'Room 101'),
(2, 'Room 102');
