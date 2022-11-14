package entities;

import java.sql.Timestamp;

public class Payment {
    private String title;
    private Timestamp date_of_payment;
    private int price;
    private int student_id;

    public Payment() {
    }

    public Payment(String title,
                   Timestamp date_of_payment,
                   int price,
                   int student_id) {
        this.title = title;
        this.date_of_payment = date_of_payment;
        this.price = price;
        this.student_id = student_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getDate_of_payment() {
        return date_of_payment;
    }

    public void setDate_of_payment(Timestamp date_of_payment) {
        this.date_of_payment = date_of_payment;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "title='" + title + '\'' +
                ", date_of_payment=" + date_of_payment +
                ", price=" + price +
                ", student_id=" + student_id +
                '}';
    }
}
