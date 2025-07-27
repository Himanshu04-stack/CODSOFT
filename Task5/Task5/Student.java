package Task5;

public class Student {
    private final String name;
    private final String rollNumber;
    private final String department;
    private final String course;
    private final String email;
    private final String mobile;
    private final String address;

    public Student(String name, String rollNumber, String department, String course,
                   String email, String mobile, String address) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.department = department;
        this.course = course;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }

    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getDepartment() { return department; }
    public String getCourse() { return course; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public String getAddress() { return address; }
}