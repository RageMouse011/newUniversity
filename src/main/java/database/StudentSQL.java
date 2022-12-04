package database;

import database.util.ConnectionPool;
import entities.Payment;
import entities.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static database.util.Resources.*;

public class StudentSQL {
    ConnectionPool pool = new ConnectionPool(dbUrl, dbUser, dbPass, 5);
    Connection conn = null;

    public Student create(Student student) {
        String create = "insert into student (first_name, last_name, faculty) values (?, ?, ?)";
        try {
            conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement(create);
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setString(3, student.getFaculty());

            ps.execute();

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    pool.returnConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(student);
        return student;
    }

    public Student update(int id, String faculty) {
        String update = "update student set faculty= ? where id= ?";
        String getUpdatedStudent = "select first_name, last_name, faculty from student where id = ?";
        Student student = new Student();
        try {
            conn = pool.getConnection();
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
            if (conn != null) {
                try {
                    pool.returnConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
            conn = pool.getConnection();
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
            if (conn != null) {
                try {
                    pool.returnConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(student);
        return student;
    }

    public Student getStudentById(int id) {
        String getStudentById = "select first_name, last_name, faculty from student where id =?";
        Student student = new Student();
        try {
            conn = pool.getConnection();
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
            if (conn != null) {
                try {
                    pool.returnConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(student);
        return student;
    }

    public List<Student> getAllStudents() {
        String getAllStudents = "select first_name, last_name, faculty from student";
        List<Student> result = new ArrayList<>();
        try {
            conn = pool.getConnection();
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
            if (conn != null) {
                try {
                    pool.returnConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(result);
        return result;
    }

    public List<Integer> updateLastPayment(List<Payment> payments) {
        String addLastPayment = "update student set last_payment = ? where id = ?";
        String idOfSelectedStudents = "select student_id from payment where student_id = ? group by student_id";
        List<Integer> SumOfStudentsPayments = new ArrayList<>();
        try {
            conn = pool.getConnection();
            PreparedStatement alp = conn.prepareStatement(addLastPayment);
            PreparedStatement ids = conn.prepareStatement(idOfSelectedStudents);
            conn.setAutoCommit(false);

            for(Payment p : payments) {
                alp.setTimestamp(1, p.getDateOfPayment());
                alp.setInt(2, p.getStudentId());
                alp.execute();

                ids.setInt(1, p.getStudentId());
                ResultSet rs = ids.executeQuery();

                while(rs.next()) {
                    SumOfStudentsPayments.add(rs.getInt("student_id"));
                }
            }
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    pool.returnConnection(conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(SumOfStudentsPayments);
        return SumOfStudentsPayments;
    }
}
