package Task2;

import java.util.*;

class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

class Student {
    private String name;
    private Map<String, Integer> subjectMarks;
    private int total;
    private double average;
    private char grade;

    public Student(String name, Map<String, Integer> subjectMarks) {
        this.name = name;
        this.subjectMarks = subjectMarks;
        calculateResults();
    }

    private void calculateResults() {
        total = subjectMarks.values().stream().mapToInt(Integer::intValue).sum();
        average = (double) total / subjectMarks.size();
        assignGrade();
    }

    private void assignGrade() {
        if (average >= 90) grade = 'A';
        else if (average >= 80) grade = 'B';
        else if (average >= 70) grade = 'C';
        else if (average >= 60) grade = 'D';
        else if (average >= 50) grade = 'E';
        else grade = 'F';
    }

    public void printReport() {
        System.out.println("\n📘 Report Card for " + name);
        System.out.println("----------------------------");
        for (Map.Entry<String, Integer> entry : subjectMarks.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Total Marks: " + total);
        System.out.printf("Average: %.2f%%\n", average);
        System.out.println("Grade: " + grade);
    }

    public char getGrade() {
        return grade;
    }

    public String getName() {
        return name;
    }
}

public class StudentGradeCalculator {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Character, Integer> gradeDistribution = new TreeMap<>();

    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        System.out.println("🎓 Welcome to the Student Grade Management System");

        try {
            System.out.print("Enter number of students: ");
            int numStudents = readPositiveInt();

            for (int i = 1; i <= numStudents; i++) {
                System.out.println("\n--- Enter details for Student " + i + " ---");
                String name = readNonEmptyString("Enter student name: ");
                int subjectCount = readPositiveInt("Enter number of subjects: ");

                Map<String, Integer> subjectMarks = new LinkedHashMap<>();

                for (int j = 1; j <= subjectCount; j++) {
                    String subject = readUniqueSubject(scanner, subjectMarks, j);
                    int mark = readValidatedMark("Enter marks for " + subject + " (0-100): ");
                    subjectMarks.put(subject, mark);
                }

                Student student = new Student(name, subjectMarks);
                student.printReport();
                students.add(student);

                gradeDistribution.put(student.getGrade(),
                        gradeDistribution.getOrDefault(student.getGrade(), 0) + 1);
            }

            printGradeSummary();

        } catch (InvalidInputException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // --- Input Helpers ---

    private static int readPositiveInt() throws InvalidInputException {
        return readPositiveInt("Enter a positive number: ");
    }

    private static int readPositiveInt(String prompt) throws InvalidInputException {
        System.out.print(prompt);
        if (!scanner.hasNextInt()) throw new InvalidInputException("Invalid integer input.");
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        if (value <= 0) throw new InvalidInputException("Number must be greater than zero.");
        return value;
    }

    private static String readNonEmptyString(String prompt) throws InvalidInputException {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) throw new InvalidInputException("Input cannot be empty.");
        return input;
    }

    private static int readValidatedMark(String prompt) throws InvalidInputException {
        System.out.print(prompt);
        if (!scanner.hasNextInt()) throw new InvalidInputException("Marks must be numeric.");
        int mark = scanner.nextInt();
        scanner.nextLine(); // consume newline
        if (mark < 0 || mark > 100) throw new InvalidInputException("Marks must be between 0 and 100.");
        return mark;
    }

    private static String readUniqueSubject(Scanner scanner, Map<String, Integer> existingSubjects, int index) throws InvalidInputException {
        System.out.print("Enter name for Subject " + index + ": ");
        String subject = scanner.nextLine().trim();
        if (subject.isEmpty()) throw new InvalidInputException("Subject name cannot be empty.");
        if (existingSubjects.containsKey(subject))
            throw new InvalidInputException("Duplicate subject: " + subject);
        return subject;
    }

    private static void printGradeSummary() {
        System.out.println("\n📊 Grade Distribution:");
        System.out.println("----------------------------");
        for (Map.Entry<Character, Integer> entry : gradeDistribution.entrySet()) {
            System.out.println("Grade " + entry.getKey() + ": " + entry.getValue() + " student(s)");
        }
    }
}