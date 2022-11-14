package entities;

public class Student {
    private String first_name;
    private String last_name;
    private String faculty;

    public Student() {
    }

    public Student(String first_name,
                   String last_name,
                   String faculty) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.faculty = faculty;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Student{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", faculty='" + faculty + '\'' +
                '}';
    }
}
