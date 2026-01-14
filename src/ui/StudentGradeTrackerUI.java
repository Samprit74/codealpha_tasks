package ui;

import javax.swing.*;
import java.awt.GridLayout;

import dao.StudentDAO;
import model.Student;

public class StudentGradeTrackerUI extends JFrame {

    private JTextField nameField;
    private JTextField departmentField;
    private JTextField yearField;
    private JTextField semesterField;
    private JTextField subject1Field;
    private JTextField subject2Field;
    private JTextField subject3Field;

    public StudentGradeTrackerUI() {

        setTitle("Student Grade Tracker");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));

        panel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Department:"));
        departmentField = new JTextField();
        panel.add(departmentField);

        panel.add(new JLabel("Year:"));
        yearField = new JTextField();
        panel.add(yearField);

        panel.add(new JLabel("Semester:"));
        semesterField = new JTextField();
        panel.add(semesterField);

        panel.add(new JLabel("Subject 1 Marks:"));
        subject1Field = new JTextField();
        panel.add(subject1Field);

        panel.add(new JLabel("Subject 2 Marks:"));
        subject2Field = new JTextField();
        panel.add(subject2Field);

        panel.add(new JLabel("Subject 3 Marks:"));
        subject3Field = new JTextField();
        panel.add(subject3Field);

        JButton saveButton = new JButton("Save Student");
        panel.add(saveButton);
        panel.add(new JLabel("")); // empty cell for layout

        add(panel);

        // Button action
        saveButton.addActionListener(e -> saveStudent());

        setVisible(true);
    }

    private void saveStudent() {
        try {
            String name = nameField.getText();
            String department = departmentField.getText();
            int year = Integer.parseInt(yearField.getText());
            int semester = Integer.parseInt(semesterField.getText());

            int sub1 = Integer.parseInt(subject1Field.getText());
            int sub2 = Integer.parseInt(subject2Field.getText());
            int sub3 = Integer.parseInt(subject3Field.getText());

            double score = (sub1 + sub2 + sub3) / 3.0;

            Student student = new Student(
                    name,
                    department,
                    year,
                    semester,
                    sub1,
                    sub2,
                    sub3,
                    score
            );

            StudentDAO dao = new StudentDAO();
            dao.insertStudent(student);

            JOptionPane.showMessageDialog(this,
                    "Student saved successfully!\nAverage Score: " + score);

            clearFields();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid data",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        departmentField.setText("");
        yearField.setText("");
        semesterField.setText("");
        subject1Field.setText("");
        subject2Field.setText("");
        subject3Field.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradeTrackerUI());
    }
}
