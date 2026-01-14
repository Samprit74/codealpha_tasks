package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.StudentDAO;
import model.Student;

public class AddStudentPanel extends JPanel {

    private MainFrame mainFrame;
    private JTextField nameField, departmentField, yearField, semesterField;
    private JTextField subject1Field, subject2Field, subject3Field;

    public AddStudentPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titleLabel = new JLabel("Add New Student");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 37, 41));

        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBackground(Color.WHITE);
        formContainer.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JPanel personalPanel = createSectionPanel("Personal Details");
        personalPanel.add(createFormRow("Full Name", nameField = createTextField()));
        personalPanel.add(createFormRow("Department", departmentField = createTextField()));
        personalPanel.add(createFormRow("Year", yearField = createTextField()));
        personalPanel.add(createFormRow("Semester", semesterField = createTextField()));

        JPanel academicPanel = createSectionPanel("Academic Details");
        academicPanel.add(createFormRow("Subject 1 Marks", subject1Field = createTextField()));
        academicPanel.add(createFormRow("Subject 2 Marks", subject2Field = createTextField()));
        academicPanel.add(createFormRow("Subject 3 Marks", subject3Field = createTextField()));

        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setBackground(new Color(248, 249, 252));
        previewPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel previewTitle = new JLabel("Score Preview");
        previewTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JTextArea previewArea = new JTextArea("Marks will be calculated here...");
        previewArea.setEditable(false);
        previewArea.setOpaque(false);
        previewArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        previewPanel.add(previewTitle, BorderLayout.NORTH);
        previewPanel.add(previewArea, BorderLayout.CENTER);

        javax.swing.event.DocumentListener listener = new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { updatePreview(previewArea); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { updatePreview(previewArea); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { updatePreview(previewArea); }
        };

        subject1Field.getDocument().addDocumentListener(listener);
        subject2Field.getDocument().addDocumentListener(listener);
        subject3Field.getDocument().addDocumentListener(listener);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        buttonPanel.setBackground(Color.WHITE);

        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40));
        clearButton.setBackground(new Color(108, 117, 125));
        clearButton.setForeground(Color.WHITE);
        clearButton.setOpaque(true);
        clearButton.setContentAreaFilled(true);
        clearButton.setBorderPainted(false);
        clearButton.setFocusPainted(false);

        JButton saveButton = new JButton("Save Student");
        saveButton.setPreferredSize(new Dimension(150, 45));
        saveButton.setBackground(new Color(46, 204, 113));
        saveButton.setForeground(Color.WHITE);
        saveButton.setOpaque(true);
        saveButton.setContentAreaFilled(true);
        saveButton.setBorderPainted(false);
        saveButton.setFocusPainted(false);

        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);

        formContainer.add(personalPanel);
        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        formContainer.add(academicPanel);
        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        formContainer.add(previewPanel);
        formContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        formContainer.add(buttonPanel);

        JScrollPane scrollPane = new JScrollPane(formContainer);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        clearButton.addActionListener(e -> clearForm());
        saveButton.addActionListener(e -> saveStudent());

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(new Color(52, 152, 219));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        panel.add(label);
        return panel;
    }

    private JPanel createFormRow(String text, JTextField field) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);

        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(150, 30));

        row.add(label, BorderLayout.WEST);
        row.add(field, BorderLayout.CENTER);

        return row;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private void updatePreview(JTextArea previewArea) {
        try {
            int s1 = Integer.parseInt(subject1Field.getText());
            int s2 = Integer.parseInt(subject2Field.getText());
            int s3 = Integer.parseInt(subject3Field.getText());
            double avg = (s1 + s2 + s3) / 3.0;

            previewArea.setText(
                    "Subject 1: " + s1 + "\n" +
                    "Subject 2: " + s2 + "\n" +
                    "Subject 3: " + s3 + "\n\n" +
                    "Average: " + String.format("%.2f", avg)
            );
        } catch (Exception e) {
            previewArea.setText("Enter valid marks to see preview...");
        }
    }

    private void clearForm() {
        nameField.setText("");
        departmentField.setText("");
        yearField.setText("");
        semesterField.setText("");
        subject1Field.setText("");
        subject2Field.setText("");
        subject3Field.setText("");
    }

    private void saveStudent() {
        try {
            Student student = new Student(
                    nameField.getText(),
                    departmentField.getText(),
                    Integer.parseInt(yearField.getText()),
                    Integer.parseInt(semesterField.getText()),
                    Integer.parseInt(subject1Field.getText()),
                    Integer.parseInt(subject2Field.getText()),
                    Integer.parseInt(subject3Field.getText()),
                    (Integer.parseInt(subject1Field.getText())
                            + Integer.parseInt(subject2Field.getText())
                            + Integer.parseInt(subject3Field.getText())) / 3.0
            );

            new StudentDAO().insertStudent(student);
            JOptionPane.showMessageDialog(this, "Student added successfully");
            clearForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input data");
        }
    }
}
