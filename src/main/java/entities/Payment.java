package entities;

import java.sql.Timestamp;

public class Payment {
    private String title;
    private Timestamp dateOfPayment;
    private int price;
    private int studentId;

    public Payment() {
    }

    public Payment(String title,
                   Timestamp dateOfPayment,
                   int price,
                   int studentId) {
        this.title = title;
        this.dateOfPayment = dateOfPayment;
        this.price = price;
        this.studentId = studentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Timestamp dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "title='" + title + '\'' +
                ", dateOfPayment=" + dateOfPayment +
                ", price=" + price +
                ", studentId=" + studentId +
                '}';
    }
}
