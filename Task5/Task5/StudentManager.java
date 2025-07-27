package Task5;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private final List<Student> students = new ArrayList<>();

    public StudentManager() {
        // Preloaded university-level student records
        students.add(new Student("Aarav Mehta", "U001", "Computer Science", "B.Tech",
                "aarav@univ.edu", "9876543210", "Hostel A, Room 102"));
        students.add(new Student("Priya Sharma", "U002", "Electronics", "B.Tech",
                "priya@univ.edu", "9123456789", "Hostel B, Room 203"));
        students.add(new Student("Rahul Verma", "U003", "Mechanical", "M.Tech",
                "rahul@univ.edu", "9988776655", "Hostel C, Room 302"));
        students.add(new Student("Sneha Rao", "U004", "Information Tech", "BCA",
                "sneha@univ.edu", "9876543000", "Hostel A, Room 104"));
        students.add(new Student("Yash Jain", "U005", "Civil", "Diploma",
                "yash@univ.edu", "9765432101", "Hostel D, Room 210"));
        students.add(new Student("Ananya Das", "U006", "Computer Science", "MCA",
                "ananya@univ.edu", "9911223344", "Hostel B, Room 308"));
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Student searchStudent(String rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber().equalsIgnoreCase(rollNumber)) {
                return s;
            }
        }
        return null;
    }

    public boolean deleteStudent(String rollNumber) {
        return students.removeIf(s -> s.getRollNumber().equalsIgnoreCase(rollNumber));
    }

    public List<Student> getAllStudents() {
        return students;
    }
}