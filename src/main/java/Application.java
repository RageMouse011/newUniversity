import entities.Student;
import sql.PaymentSQL;
import sql.StudentSQL;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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
                     10. Choose to quit the program""");

            Scanner input = new Scanner(System.in);
            int decision = input.nextInt();

            switch (decision) {
                case 1:
                    System.out.println("Enter the firstname, lastname and faculty (after each field click enter): ");
                    List<String> case1 = new ArrayList<>();
                    Scanner getCase1 = new Scanner(System.in);
                    boolean quitCase1 = false;

                    Student student = new Student();
                    while (!quitCase1) {
                        System.out.println("Enter q to quit.");
                        String inputCase1 = getCase1.nextLine();
                        if (inputCase1.equals("q")) {
                            quitCase1 = true;
                        } else {
                            case1.add(inputCase1);
                        }
                    }
                    student.setFirstName(case1.get(0));
                    student.setLastName(case1.get(1));
                    student.setFaculty(case1.get(2));

                    studentSQL.create(student);
                    break;
                case 2:
                    System.out.println("Enter new faculty: ");
                    Scanner getCase2 = new Scanner(System.in);
                    String inputCase2 = getCase2.next();
                    studentSQL.update(inputCase2);
                    break;
                case 3:
                    System.out.println("Enter the id of student which you want to remove: ");
                    Scanner getCase3 = new Scanner(System.in);
                    int inputCase3 = getCase3.nextInt();
                    studentSQL.delete(inputCase3);
                    break;
                case 4:
                    System.out.println("Enter the title, price and student id: ");
                    Scanner getCase4 = new Scanner(System.in);
                    String inputCase41 = getCase4.nextLine();
                    int inputCase42 = getCase4.nextInt();
                    int inputCase43 = getCase4.nextInt();
                    paymentSQL.create(inputCase41, inputCase42, inputCase43);
                    break;
                case 5:
                    System.out.println("Enter the price and id of the payment: ");
                    List<Integer> case5 = new ArrayList<>();
                    Scanner getCase5 = new Scanner(System.in);
                    boolean quitCase2 = false;
                    while (!quitCase2) {
                        System.out.println("Enter 0 to quit.");
                        Integer inputCase5 = getCase5.nextInt();
                        if (inputCase5.equals(0)) {
                            quitCase2 = true;
                        } else {
                            case5.add(inputCase5);
                        }
                    }
                    paymentSQL.update(case5.get(0), case5.get(1));
                    break;
                case 6:
                    System.out.println("Enter the student id: ");
                    Scanner getCase6 = new Scanner(System.in);
                    int inputCase6 = getCase6.nextInt();
                    paymentSQL.retrieveStudentsPayments(inputCase6);
                    break;
                case 7:
                    paymentSQL.retrieveAllStudentsPayments();
                    break;
                case 8:
                    System.out.println("Enter the student id: ");
                    Scanner getCase8 = new Scanner(System.in);
                    int inputCase8 = getCase8.nextInt();
                    studentSQL.getStudentById(inputCase8);
                    break;
                case 9:
                    studentSQL.getAllStudents();
                case 10:
                    return;
            }
        }


    }
}
