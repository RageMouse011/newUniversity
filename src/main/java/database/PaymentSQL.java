package database;

import application.Application;
import database.util.ConnectionPool;
import entities.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.util.Resources.*;

public class PaymentSQL {
    ConnectionPool pool = new ConnectionPool(dbUrl, dbUser, dbPass, 5);
    Connection conn = null;


    public Payment create(Payment payment) {
        String create = "insert into payment (title, date_of_payment, price, student_id) values (?, ?, ?, ?)";
        try {
            conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement(create);
            ps.setString(1, payment.getTitle());
            ps.setTimestamp(2, payment.getDateOfPayment());
            ps.setInt(3, payment.getPrice());
            ps.setInt(4, payment.getStudentId());

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
        System.out.println(payment);
        return payment;
    }

    public Payment update(int id, String title, int price) {
        String update = "update payment set title =?, price =? where id= ?";
        String getUpdatedPayment = "select title, date_of_payment, price, student_id from payment where id =?";
        Payment payment = new Payment();
        try {
            conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement(update);
            ps.setString(1, title);
            ps.setInt(2, price);
            ps.setInt(3, id);

            ps.execute();

            ps = conn.prepareStatement(getUpdatedPayment);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                payment.setTitle(rs.getString(1));
                payment.setDateOfPayment(rs.getTimestamp(2));
                payment.setPrice(rs.getInt(3));
                payment.setStudentId(rs.getInt(4));
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
        System.out.println(payment);
        return payment;
    }

    public List<String> retrieveAllStudentsPayments() {
        String retrieve = "select s.id, s.first_name, s.last_name, sum(p.price) from student s join payment p " +
                "on s.id = p.student_id group by s.id, s.first_name, s.last_name";
        List<String> result = new ArrayList<>();
        try {
            conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement(retrieve);

            ResultSet rs = ps.executeQuery();
            int sum;
            while(rs.next()) {
                result.add(rs.getString("first_name"));
                result.add(rs.getString("last_name"));
                sum = rs.getInt(4);
                result.add(String.valueOf(sum));
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

    public List<String> retrieveStudentsPayments(int Id) {
        String retrieve = "select s.first_name, s.last_name, sum(p.price) from student s join payment p " +
                "on s.id = ? and p.student_id = ? group by s.first_name, s.last_name";
        List<String> result = new ArrayList<>();
        try {
            conn = pool.getConnection();
            PreparedStatement ps = conn.prepareStatement(retrieve);
            ps.setInt(1, Id);
            ps.setInt(2, Id);

            ResultSet rs = ps.executeQuery();
            int sum;
            while(rs.next()) {
                result.add(rs.getString("first_name"));
                result.add(rs.getString("last_name"));
                sum = rs.getInt(3);
                result.add(String.valueOf(sum));
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

    public List<Payment> createPayments(List<Payment> payments) {
        String createPayment = "insert into payment (title, date_of_payment, price, student_id) values (?, ?, ?, ?)";
        List<Payment> creatingResult = new ArrayList<>();

        try {
            conn = pool.getConnection();
            PreparedStatement cp = conn.prepareStatement(createPayment);

            for (Payment p : payments) {
                cp.setString(1, p.getTitle());
                cp.setTimestamp(2 ,p.getDateOfPayment());
                cp.setInt(3, p.getPrice());
                cp.setInt(4, p.getStudentId());
                cp.execute();
                creatingResult.add(p);
            }

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
        System.out.println(creatingResult);
        return creatingResult;
    }
}
