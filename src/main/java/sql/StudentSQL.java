package sql;

import entities.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StudentSQL {
    Connection conn = null;

    public Student create(Student student) {
        String create = "insert into student (first_name, last_name, faculty) values (?, ?, ?)";
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(create);
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getFaculty());

            ps.execute();

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connClose();
        }
        System.out.println(student);
        return student;
    }

    public Student update(int id, String faculty) {
        String update = "update student set faculty= ? where id= ?";
        String getUpdatedStudent = "select first_name, last_name, faculty from student where id = ?";
        Student student = new Student();
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(update);
            ps.setString(1, faculty);
            ps.setInt(2, id);

            ps.execute();

            ps = conn.prepareStatement(getUpdatedStudent);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                student.setFirstName(rs.getString(1));
                student.setLastName(rs.getString(2));
                student.setFaculty(rs.getString(3));
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connClose();
        }
        System.out.println(student);
        return student;
    }

    public Student delete(int id) {
        String deleteAllPayments = "delete from payment where student_id =?";
        String deleteStudent = "delete from student where id = ?";
        String getDeletedStudent = "select first_name, last_name, faculty from student where id = ?";
        Student student = new Student();
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(deleteAllPayments);
            ps.setInt(1, id);

            ps.execute();

            ps = conn.prepareStatement(deleteStudent);
            ps.setInt(1, id);

            ps.execute();

            try {
                ps = conn.prepareStatement(getDeletedStudent);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    student.setFirstName(rs.getString(1));
                    student.setLastName(rs.getString(2));
                    student.setFaculty(rs.getString(3));
                }
            } catch (Exception e) {
                System.out.println("Student was successfully removed or he didn't even exist.");
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connClose();
        }
        System.out.println(student);
        return student;
    }

    public Student getStudentById(int id) {
        String getStudentById = "select first_name, last_name, faculty from student where id =?";
        Student student = new Student();
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(getStudentById);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setFaculty(rs.getString("faculty"));
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connClose();
        }
        System.out.println(student);
        return student;
    }

    public List<Student> getAllStudents() {
        String getAllStudents = "select first_name, last_name, faculty from student";
        List<Student> result = new ArrayList<>();
        try {
            conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(getAllStudents);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Student student = new Student();
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setFaculty(rs.getString("faculty"));
                result.add(student);
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connClose();
        }
        System.out.println(result);
        return result;
    }

    public Connection getConnection() {
        String url = null;
        String username = null;
        String password = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        FileInputStream fis;
        Properties properties = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/application.properties");
            properties.load(fis);

            url = properties.getProperty("db.host");
            username = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public void connClose() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
