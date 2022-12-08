package entities;

public class Teacher extends UniversityStaff{

    private String subject;


    public Teacher() {
        super();
    }

    public Teacher(String faculty,
                   String firstName,
                   String lastName,
                   int experience,
                   int salary,
                   String subject) {
        super(faculty, firstName, lastName, experience, salary);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "subject='" + subject + '\'' +
                '}';
    }
}
