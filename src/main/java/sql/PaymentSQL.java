package sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentSQL {
    Connection conn = null;
    String url = "jdbc:postgresql://localhost:5432/university";
    String user = "postgres";
    String password = "Q12we34r56t";

    public boolean create(String title, int price, int studentId) {
        boolean result = false;
        String create = "insert into payment (title, date_of_payment, price, student_id) values (?, ?, ?, ?)";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(create);
            ps.setString(1, title);
            ps.setTimestamp(2, new Timestamp(new Date().getTime()));
            ps.setInt(3, price);
            ps.setInt(4, studentId);

            result = ps.execute();

            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            connClose();
        }
        return result;
    }

    public boolean update(Integer price, Integer id) {
        boolean result = false;
        String update = "update payment set price= ? where id= ?";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(update);
            ps.setInt(1, price);
            ps.setInt(2, id);

            ps.execute();
            result = true;

            ps.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            result = false;
        } finally {
            connClose();
        }
        return result;
    }

    public List<String> retrieveAllStudentsPayments() {
        List<String> result = new ArrayList<>();
        String retrieve = "select s.id, s.first_name, s.last_name, sum(p.price) from student s join payment p " +
                "on s.id = p.student_id group by s.id, s.first_name, s.last_name";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            connClose();
        }
        return result;
    }

    public List<String> retrieveStudentsPayments(int Id) {
        List<String> result = new ArrayList<>();
        String retrieve = "select s.first_name, s.last_name, sum(p.price) from student s join payment p " +
                "on s.id = ? and p.student_id = ? group by s.first_name, s.last_name";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
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
