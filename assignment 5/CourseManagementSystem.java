import java.sql.*;
import java.util.Scanner;

public class CourseManagementSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/codsoft";
    private static final String USER = "root";
    private static final String PASSWORD = "vrishank";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Display Available Courses");
            System.out.println("2. Register Student");
            System.out.println("3. Show Registered Students");
            System.out.println("4. Drop Course");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayAvailableCourses();
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Course Code: ");
                    String courseCode = scanner.nextLine();
                    registerStudent(studentId, name, courseCode);
                    break;
                case 3:
                    showRegisteredStudents();
                    break;
                case 4:
                    System.out.print("Enter Student ID: ");
                    String dropStudentId = scanner.nextLine();
                    System.out.print("Enter Course Code: ");
                    String dropCourseCode = scanner.nextLine();
                    dropCourse(dropStudentId, dropCourseCode);
                    break;
                case 5:
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    static void displayAvailableCourses() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT code, title, description, capacity, enrolled FROM courses WHERE enrolled < capacity")) {
             
            System.out.println("Available Courses:");
            while (rs.next()) {
                String code = rs.getString("code");
                String title = rs.getString("title");
                String description = rs.getString("description");
                int capacity = rs.getInt("capacity");
                int enrolled = rs.getInt("enrolled");
                System.out.printf("Code: %s, Title: %s, Description: %s, Capacity: %d, Enrolled: %d%n", 
                                  code, title, description, capacity, enrolled);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void registerStudent(String studentId, String name, String courseCode) {
        try (Connection conn = connect()) {
            conn.setAutoCommit(false);
            String checkCourseQuery = "SELECT enrolled, capacity FROM courses WHERE code = ?";
            String studentQuery = "INSERT INTO students (id, name, registered_courses) VALUES (?, ?, '') " +
                                  "ON DUPLICATE KEY UPDATE name = VALUES(name)";
            String courseQuery = "UPDATE courses SET enrolled = enrolled + 1 WHERE code = ? AND enrolled < capacity";
            String updateRegisteredCourses = "UPDATE students SET registered_courses = CONCAT_WS(',', registered_courses, ?) WHERE id = ?";

            try (PreparedStatement checkCourseStmt = conn.prepareStatement(checkCourseQuery);
                 PreparedStatement studentStmt = conn.prepareStatement(studentQuery);
                 PreparedStatement courseStmt = conn.prepareStatement(courseQuery);
                 PreparedStatement updateStmt = conn.prepareStatement(updateRegisteredCourses)) {

                checkCourseStmt.setString(1, courseCode);
                ResultSet rs = checkCourseStmt.executeQuery();
                
                if (rs.next()) {
                    int enrolled = rs.getInt("enrolled");
                    int capacity = rs.getInt("capacity");
                    
                    if (enrolled < capacity) {
                        studentStmt.setString(1, studentId);
                        studentStmt.setString(2, name);
                        studentStmt.executeUpdate();

                        courseStmt.setString(1, courseCode);
                        courseStmt.executeUpdate();

                        updateStmt.setString(1, courseCode);
                        updateStmt.setString(2, studentId);
                        updateStmt.executeUpdate();

                        System.out.println("Registered " + name + " for " + courseCode);
                    } else {
                        System.out.println(courseCode + " is full.");
                    }
                } else {
                    System.out.println(courseCode + " does not exist.");
                }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void showRegisteredStudents() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, name, registered_courses FROM students")) {
             
            System.out.println("Registered Students:");
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String registeredCourses = rs.getString("registered_courses");
                System.out.printf("ID: %s, Name: %s, Registered Courses: %s%n", id, name, registeredCourses);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void dropCourse(String studentId, String courseCode) {
        try (Connection conn = connect()) {
            conn.setAutoCommit(false);

            String checkRegistrationQuery = "SELECT registered_courses FROM students WHERE id = ?";
            String updateRegisteredCourses = "UPDATE students SET registered_courses = REGEXP_REPLACE(registered_courses, ?, '') WHERE id = ?";
            String decrementEnrollment = "UPDATE courses SET enrolled = enrolled - 1 WHERE code = ?";

            try (PreparedStatement checkStmt = conn.prepareStatement(checkRegistrationQuery);
                 PreparedStatement updateStmt = conn.prepareStatement(updateRegisteredCourses);
                 PreparedStatement decrementStmt = conn.prepareStatement(decrementEnrollment)) {

                checkStmt.setString(1, studentId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    String registeredCourses = rs.getString("registered_courses");

                    if (registeredCourses.contains(courseCode)) {
                        String updatedCourses = registeredCourses.replaceAll(",?\\b" + courseCode + "\\b,?", "").replaceAll("^,|,$", "").trim();

                        updateStmt.setString(1, courseCode);
                        updateStmt.setString(2, studentId);
                        updateStmt.executeUpdate();

                        decrementStmt.setString(1, courseCode);
                        decrementStmt.executeUpdate();

                        System.out.println("Dropped " + courseCode + " for student " + studentId);
                    } else {
                        System.out.println("Student " + studentId + " is not registered for " + courseCode);
                    }
                } else {
                    System.out.println("Student ID " + studentId + " does not exist.");
                }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
