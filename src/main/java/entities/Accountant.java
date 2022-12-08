package entities;

public class Accountant extends UniversityStaff {

    private String department;

    public Accountant() {
        super();
    }

    public Accountant(String faculty,
                      String firstName,
                      String lastName,
                      int experience,
                      int salary,
                      String department) {
        super(faculty, firstName, lastName, experience, salary);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Accountant{" +
                "department='" + department + '\'' +
                '}';
    }
}
