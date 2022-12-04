package application;

import database.util.ConnectionPool;
import entities.Payment;
import entities.Student;
import database.PaymentSQL;
import database.StudentSQL;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.*;


public class Application {

    public static void main(String[] args) {
        StudentSQL studentSQL = new StudentSQL();
        PaymentSQL paymentSQL = new PaymentSQL();

        while (true) {
            System.out.println("""
                    Enter the appropriate number for further action:\s
                     1: Create student.\s
                     2: Update student.\s
                     3. Delete student.
                     4. Create payment.\s
                     5. Update payment.\s
                     6. Retrieve student's payments.
                     7. Retrieve all student's payments.\s
                     8. Retrieve specific student.\s
                     9. Retrieve all students.\s
                     10. Create several payments.
                     11. Choose to quit the program""");

            Scanner input = new Scanner(System.in);
            int decision = input.nextInt();

            switch (decision) {
                case 1:
                    Scanner getCase1 = new Scanner(System.in);
                    Student student = new Student();

                    System.out.println("Enter  firstname: ");
                    String inputCase11 = getCase1.nextLine();
                    student.setFirstName(inputCase11);

                    System.out.println("Enter  lastname: ");
                    String inputCase12 = getCase1.nextLine();
                    student.setLastName(inputCase12);

                    System.out.println("Enter  faculty: ");
                    String inputCase13 = getCase1.nextLine();
                    student.setFaculty(inputCase13);

                    studentSQL.create(student);
                    break;
                case 2:
                    Scanner getCase2 = new Scanner(System.in);

                    System.out.println("Enter the id of student: ");
                    int inputCase21 = getCase2.nextInt();

                    System.out.println("Enter new faculty: ");
                    String inputCase22 = getCase2.next();

                    studentSQL.update(inputCase21, inputCase22);
                    break;
                case 3:
                    Scanner getCase3 = new Scanner(System.in);

                    System.out.println("Enter the id of student which you want to remove: ");
                    int inputCase3 = getCase3.nextInt();

                    studentSQL.delete(inputCase3);
                    break;
                case 4:
                    Scanner getCase4 = new Scanner(System.in);
                    Payment payment = new Payment();

                    System.out.println("Enter the title: ");
                    String inputCase41 = getCase4.nextLine();
                    payment.setTitle(inputCase41);

                    System.out.println("Enter price: ");
                    int inputCase42 = getCase4.nextInt();
                    payment.setPrice(inputCase42);

                    System.out.println("Enter student id: ");
                    int inputCase43 = getCase4.nextInt();
                    payment.setStudentId(inputCase43);

                    payment.setDateOfPayment(new Timestamp(new Date().getTime()));

                    paymentSQL.create(payment);
                    break;
                case 5:
                    Scanner getCase5 = new Scanner(System.in);

                    System.out.println("Enter payment id: ");
                    int inputCase51 = getCase5.nextInt();

                    System.out.println("Enter new title: ");
                    String inputCase52 = getCase5.next();

                    System.out.println("Enter new price: ");
                    int inputCase53 = getCase5.nextInt();

                    paymentSQL.update(inputCase51, inputCase52, inputCase53);
                    break;
                case 6:
                    Scanner getCase6 = new Scanner(System.in);

                    System.out.println("Enter the student id: ");
                    int inputCase6 = getCase6.nextInt();

                    paymentSQL.retrieveStudentsPayments(inputCase6);
                    break;
                case 7:
                    paymentSQL.retrieveAllStudentsPayments();
                    break;
                case 8:
                    Scanner getCase8 = new Scanner(System.in);

                    System.out.println("Enter the student id: ");
                    int inputCase8 = getCase8.nextInt();

                    studentSQL.getStudentById(inputCase8);
                    break;
                case 9:
                    studentSQL.getAllStudents();
                    break;
                case 10:
                    Scanner getCase10 = new Scanner(System.in);
                    List<Payment> payments = new ArrayList<>();

                    System.out.println("Enter count of payments you want to add:  ");
                    int inputCase101 = getCase10.nextInt();

                    for (int i = 0; i < inputCase101; i++) {
                        Payment anotherPayment = new Payment();

                        System.out.println("Enter the title of the payment: ");
                        String inputCase102 = getCase10.next();
                        anotherPayment.setTitle(inputCase102);

                        System.out.println("Enter the price of the the payment: ");
                        int inputCase103 = getCase10.nextInt();
                        anotherPayment.setPrice(inputCase103);

                        System.out.println("Enter student id of the payment: ");
                        int inputCase104 = getCase10.nextInt();
                        anotherPayment.setStudentId(inputCase104);

                        anotherPayment.setDateOfPayment(new Timestamp(new Date().getTime()));
                        payments.add(anotherPayment);
                    }
                    paymentSQL.createPayments(payments);
                    studentSQL.updateLastPayment(payments);
                    break;
                case 11:
                    return;
            }
        }


    }
}
