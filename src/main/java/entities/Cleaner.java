package entities;

public class Cleaner extends UniversityStaff {

    public Cleaner() {
        super();
    }

    public Cleaner(String faculty,
                   String firstName,
                   String lastName,
                   int experience,
                   double salary) {
        super(faculty, firstName, lastName, experience, salary);
    }
}
