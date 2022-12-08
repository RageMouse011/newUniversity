package entities;

public class UniversityStaff extends Person{

    private static int countOfStaff;
    private int experience;
    private int salary;

    public UniversityStaff() {
        super();
        countOfStaff++;
    }

    public UniversityStaff(String faculty,
                           String firstName,
                           String lastName,
                           int experience,
                           int salary) {
        super(faculty, firstName, lastName);
        this.experience = experience;
        this.salary = salary;
        countOfStaff++;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "UniversityStaff{" +
                "experience=" + experience +
                ", salary=" + salary +
                '}';
    }
}
