import sql.PaymentSQL;
import sql.StudentSQL;

public class Application {
    public static void main(String[] args) {
        StudentSQL studentSQL = new StudentSQL();
        PaymentSQL paymentSQL = new PaymentSQL();


        paymentSQL.retrieveAllStudentsPayments();

    }
}
