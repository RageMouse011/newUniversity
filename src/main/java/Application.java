import sql.PaymentSQL;
import sql.StudentSQL;

import java.util.Scanner;


public class Application {
    public static void main(String[] args) {
        StudentSQL studentSQL = new StudentSQL();
        PaymentSQL paymentSQL = new PaymentSQL();

        System.out.println("Enter the appropriate number for further action: " +
                "\n 1: Create student. \n 2: Update student. \n 3. Delete student." +
                "\n 4. Create payment. \n 5. Update payment. \n 6. Retrieve student's payments." +
                "\n 7. Retrieve all student's payments.");

        Scanner input = new Scanner(System.in);
        int decision = input.nextInt();

        switch (decision) {
            case 1: studentSQL.create();
            break;
            case 2: studentSQL.update();
            break;
            case 3: studentSQL.delete();
            break;
            case 4: paymentSQL.create();
            break;
            case 5: paymentSQL.update();
            break;
            case 6: paymentSQL.retrieveStudentsPayments();
            break;
            case 7: paymentSQL.retrieveAllStudentsPayments();
        }


    }
}
