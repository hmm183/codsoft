# Course Management System

The **Course Management System** is a Java application that allows users to manage courses, register students, show registered students, and drop courses using a MySQL database.

## Features

- Display available courses.
- Register a student for a course.
- Show registered students.
- Drop a course.

## Dependencies

To run this application, you'll need the MySQL Connector/J library. You can download it from the [official MySQL website](https://dev.mysql.com/downloads/connector/j/).

### Adding Dependencies

1. **For IntelliJ IDEA:**
   - Right-click on your project in the Project Explorer.
   - Select **Open Module Settings**.
   - Go to the **Libraries** tab.
   - Click the **+** button and choose **Java**.
   - Navigate to the location of the downloaded MySQL Connector/J JAR file and click **OK**.

2. **For Eclipse:**
   - Right-click on your project in the Project Explorer.
   - Select **Build Path** -> **Configure Build Path**.
   - Go to the **Libraries** tab and click **Add External JARs**.
   - Navigate to the location of the downloaded MySQL Connector/J JAR file and click **Open**.

3. **For NetBeans:**
   - Right-click on your project in the Projects window.
   - Select **Properties**.
   - Click on **Libraries** and then **Add JAR/Folder**.
   - Navigate to the location of the downloaded MySQL Connector/J JAR file and click **Open**.

## How to Run

### Prerequisites

- **Java JDK 8** or later installed.
- **MySQL Database** running with a database named `codsoft`.

### Compiling and Running

1. Open your terminal or command prompt.
2. Navigate to the directory containing the `CourseManagementSystem.java` file.
3. Compile the Java file using the following command:
   javac CourseManagementSystem.java
   java CourseManagementSystem

### SQL CODES:
1. Create and configure Database Codsoft:
    create database codsoft;
    use codsoft
2. Create the courses table:
    CREATE TABLE courses (
        code VARCHAR(10) PRIMARY KEY,
        title VARCHAR(100),
        description TEXT,
        capacity INT,
        enrolled INT DEFAULT 0
    );
3. Create the students table:
    CREATE TABLE students (
        id VARCHAR(10) PRIMARY KEY,
        name VARCHAR(100),
        registered_courses TEXT
    );
