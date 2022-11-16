package sql;

import entities.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentSQL {
    Connection conn = null;
    String url = "jdbc:postgresql://localhost:5432/university";
    String user = "postgres";
    String password = "Q12we34r56t";

    public Student create(Student student) {
        String create = "insert into student (first_name, last_name, faculty) values (?, ?, ?)";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(create);
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getFaculty());

            ps.execute();

            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            connClose();
        }
        return student;
    }

    public Student update(int id, String faculty) {
        Student student = new Student();
        String update = "update student set faculty= ? where id= ?";
        String getUpdatedStudent = "select first_name, last_name, faculty from student where id = ?";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            connClose();
        }
        return student;
    }

    public Student delete(int id) {
        Student student = new Student();
        String deleteAllPayments = "delete from payment where student_id =?";
        String deleteStudent = "delete from student where id = ?";
        String getDeletedStudent = "select first_name, last_name, faculty from student where id = ?";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
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
        } catch (SQLException | ClassNotFoundException e) {
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
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            connClose();
        }
        return student;
    }

    public List<Student> getAllStudents() {
        List<Student> result = new ArrayList<>();
        String getAllStudents = "select first_name, last_name, faculty from student";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            connClose();
        }
        return result;
    }

    public void connClose() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
