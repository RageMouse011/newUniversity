package sql;

import java.sql.*;

public class StudentSQL {
    Connection conn = null;
    String url = "jdbc:postgresql://localhost:5432/university";
    String user = "postgres";
    String password = "Q12we34r56t";

    public boolean create() {
        boolean result = false;
        String create = "insert into student (first_name, last_name, faculty) values (?, ?, ?)";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(create);
            ps.setString(1, "Max");
            ps.setString(2, "Cornie");
            ps.setString(3, "historical");

            ps.execute();
            result = true;

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            connClose();
            return result;
        }
    }

    public boolean update() {
        boolean result = false;
        String update = "update student set faculty= ? where id= ?";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(update);
            ps.setString(1, "mathematical");
            ps.setInt(2, 21);

            ps.execute();
            result = true;


            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            result = false;
        } finally {
            connClose();
            return result;
        }
    }

    public boolean delete() {
        boolean result = false;
        String deleteAllPayments = "delete from payment where student_id =?";
        String delete = "delete from student where id = ?";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = conn.prepareStatement(deleteAllPayments);
            ps.setInt(1, 23);
            ps.execute();
            ps = conn.prepareStatement(delete);
            ps.setInt(1, 23);

            ps.execute();
            result = true;


            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            result = false;
        } finally {
            connClose();
            return result;
        }
    }

    public void connClose() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}