package pooja;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

 class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql:///student";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}

public class Bluescopetask2 {

    public static void addStudent(String name, String className, int mark) throws ClassNotFoundException, SQLException {
        try (Connection con = ConnectionUtil.getConnection()) {
            String query = "insert into studentdata values(?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(2, className);
            st.setInt(3, mark);
            st.setString(1, name);
            st.executeUpdate();
        }
    }

    public static List<Map<String, Object>> ViewAllStudent() throws ClassNotFoundException, SQLException {
        List<Map<String, Object>> students = new ArrayList<>();

        try (Connection con = ConnectionUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet r = st.executeQuery("SELECT * FROM studentdata")) {

            ResultSetMetaData metaData = r.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (r.next()) {
                Map<String, Object> studentMap = new HashMap<>();

                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = r.getObject(i);
                    studentMap.put(columnName, columnValue);
                }

                students.add(studentMap);
            }
        }

        return students;
    }

    public static void updateStudent(String name, String className, int mark) throws ClassNotFoundException, SQLException {
        try (Connection con = ConnectionUtil.getConnection()) {
            String sql = "update studentdata set className=?,mark=? where name=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(2, className);
            st.setInt(3, mark);
            st.setString(1, name);
            int n = st.executeUpdate();
            System.out.println("Record Updated");
        }
    }

    public static void viewStudent(String name) throws ClassNotFoundException, SQLException {
        try (Connection con = ConnectionUtil.getConnection()) {
            String query2 = "Select * from studentdata where name=?";
            PreparedStatement st = con.prepareStatement(query2);
            st.setString(1, name);
            ResultSet r = st.executeQuery();
            while (r.next())
                System.out.println(r.getString(1) + " " + r.getString(2) + " " + r.getInt(3));
        }
    }

    public static void deleteStudent(String name) throws ClassNotFoundException, SQLException {
        try (Connection con = ConnectionUtil.getConnection()) {
            String ashwini = "delete from studentdata  where name=?";
            PreparedStatement st = con.prepareStatement(ashwini);
            st.setString(1, name);
            st.executeUpdate();
            System.out.println("Record Deleted");
        }
    }

    public static void menu() {
        System.out.println("Enter the choice:");
        System.out.println("1: Add Student");
        System.out.println("2: Display Student's details");
        System.out.println("3: View particular Student");
        System.out.println("4: Update Student");
        System.out.println("5: Delete Student");
        System.out.println("6: To Exit");
    }

    public static void operation(int choice) throws ClassNotFoundException, SQLException {
        Scanner c = new Scanner(System.in);
        switch (choice) {
            case 1:
                System.out.println("Enter the student name");
                String s = c.nextLine();
                System.out.println("Enter the student Classname");
                String a = c.nextLine();
                System.out.println("Enter the student Mark");
                int i = c.nextInt();
                addStudent(s, a, i);
                System.out.println("Added successfully....");
                break;

            case 2:
                List<Map<String, Object>> students = ViewAllStudent();
                System.out.println("View all students:");
                System.out.println(students);
                break;

            case 3:
                System.out.println("Enter the name of the student");
                String ss = c.next();
                viewStudent(ss);
                break;

            case 4:
                System.out.println("Enter the Student name");
                String s1 = c.next();
                System.out.println("Enter the student Classname");
                String a1 = c.next();
                System.out.println("Enter the student Mark");
                int i1 = c.nextInt();
                updateStudent(s1, a1, i1);
                System.out.println("Record Updated");
                break;

            case 5:
                System.out.println("Enter the Student name to delete");
                String name = c.next();
                deleteStudent(name);
                System.out.println("Record Deleted");
                break;

            case 6:
                System.out.println("Thank You!...");
                break;

            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner sc = new Scanner(System.in);
        String userId = "root";
        int password = 12345;
        System.out.println("Enter User Id");
        String uId = sc.nextLine();

        if (uId.equals(userId)) {
            System.out.println("Enter The Password");
            int pass = sc.nextInt();

            if (pass == password) {
                System.out.println("\nLogin Successfully.....");
                do {
                    menu();
                    System.out.println("Enter Your Choice");
                    int ch = sc.nextInt();

                    if (ch == 6) {
                        System.out.println("Thank You!...");
                        break;
                    }
                    operation(ch);

                } while (true);
            } else
                System.out.println("Invalid Password! Try Again");
        } else
            System.out.println("Invalid user Id! Try Again");
    }
}