package Task5;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.regex.Pattern;

public class StudentManagementSystem extends JFrame {
    private final StudentManager manager = new StudentManager();
    private final DefaultTableModel tableModel;

    public StudentManagementSystem() {
        setTitle("🎓 University Student Management System");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("University Student Dashboard", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(heading, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[]{"Name", "Roll No", "Department", "Course", "Email", "Mobile", "Address"}, 0);
        JTable studentTable = new JTable(tableModel);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(4, 4, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Add New Student"));

        JTextField nameField = new JTextField();
        JTextField rollField = new JTextField();
        JTextField deptField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField mobileField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField searchField = new JTextField();

        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("Roll No:")); form.add(rollField);
        form.add(new JLabel("Department:")); form.add(deptField);
        form.add(new JLabel("Course:")); form.add(courseField);
        form.add(new JLabel("Email:")); form.add(emailField);
        form.add(new JLabel("Mobile:")); form.add(mobileField);
        form.add(new JLabel("Address:")); form.add(addressField);
        add(form, BorderLayout.NORTH);

        JButton addBtn = new JButton("➕ Add Student");
        JButton viewBtn = new JButton("📋 View All");
        JButton searchBtn = new JButton("🔍 Search Roll No");
        JButton deleteBtn = new JButton("🗑️ Delete Roll No");

        JPanel actions = new JPanel(new GridLayout(2, 4, 10, 10));
        actions.setBorder(BorderFactory.createTitledBorder("Actions"));
        actions.add(addBtn);
        actions.add(viewBtn);
        actions.add(new JLabel("Search/Delete by Roll No:"));
        actions.add(searchField);
        actions.add(searchBtn);
        actions.add(deleteBtn);
        add(actions, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String roll = rollField.getText().trim();
            String dept = deptField.getText().trim();
            String course = courseField.getText().trim();
            String email = emailField.getText().trim();
            String mobile = mobileField.getText().trim();
            String address = addressField.getText().trim();

            if (name.isEmpty() || roll.isEmpty() || dept.isEmpty() || course.isEmpty()
                    || email.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠️ All fields are required.");
                return;
            }

            if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
                JOptionPane.showMessageDialog(this, "❌ Invalid email format.");
                return;
            }

            if (manager.searchStudent(roll) != null) {
                JOptionPane.showMessageDialog(this, "❌ Student with this Roll No already exists.");
                return;
            }

            manager.addStudent(new Student(name, roll, dept, course, email, mobile, address));
            JOptionPane.showMessageDialog(this, "✅ Student added successfully.");
            clear(nameField, rollField, deptField, courseField, emailField, mobileField, addressField);
            refreshTable();
        });

        viewBtn.addActionListener(e -> refreshTable());

        searchBtn.addActionListener(e -> {
            String roll = searchField.getText().trim();
            Student s = manager.searchStudent(roll);
            if (s != null) {
                JOptionPane.showMessageDialog(this,
                        "Student Info:\n" +
                                "Name: " + s.getName() +
                                "\nRoll: " + s.getRollNumber() +
                                "\nDepartment: " + s.getDepartment() +
                                "\nCourse: " + s.getCourse() +
                                "\nEmail: " + s.getEmail() +
                                "\nMobile: " + s.getMobile() +
                                "\nAddress: " + s.getAddress());
            } else {
                JOptionPane.showMessageDialog(this, "❌ Student not found.");
            }
        });

        deleteBtn.addActionListener(e -> {
            String roll = searchField.getText().trim();
            Student s = manager.searchStudent(roll);
            if (s == null) {
                JOptionPane.showMessageDialog(this, "❌ Student not found.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure to delete " + s.getName() + "?", "Confirm", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                manager.deleteStudent(roll);
                JOptionPane.showMessageDialog(this, "✅ Deleted successfully.");
                refreshTable();
            }
        });

        refreshTable(); // Load prefilled data on start
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : manager.getAllStudents()) {
            tableModel.addRow(new Object[]{
                    s.getName(), s.getRollNumber(), s.getDepartment(), s.getCourse(),
                    s.getEmail(), s.getMobile(), s.getAddress()
            });
        }
    }

    private void clear(JTextField... fields) {
        for (JTextField f : fields) f.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystem().setVisible(true));
    }
}