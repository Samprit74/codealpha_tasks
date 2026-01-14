package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.StudentDAO;
import model.Student;

public class EditStudentPanel extends JPanel {

    private MainFrame mainFrame;
    private JTextField idField;
    private JTextField nameField, departmentField, yearField, semesterField;
    private JTextField subject1Field, subject2Field, subject3Field;
    private Student currentStudent;
    private JPanel formContainer;

    public EditStudentPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titleLabel = new JLabel("Edit Student Record");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 37, 41));

        headerPanel.add(titleLabel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchRow.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Student ID");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        idField = new JTextField(10);
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JButton loadButton = new JButton("Load Student");
        loadButton.setPreferredSize(new Dimension(140, 40));
        loadButton.setBackground(new Color(52, 152, 219));
        loadButton.setForeground(Color.WHITE);
        loadButton.setOpaque(true);
        loadButton.setContentAreaFilled(true);
        loadButton.setBorderPainted(false);
        loadButton.setFocusPainted(false);

        searchRow.add(searchLabel);
        searchRow.add(idField);
        searchRow.add(loadButton);

        searchPanel.add(searchRow, BorderLayout.CENTER);

        formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBackground(new Color(248, 249, 252));
        formContainer.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        formContainer.setVisible(false);

        JLabel studentIdLabel = new JLabel("Student ID: --");
        studentIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        studentIdLabel.setForeground(new Color(108, 117, 125));

        JPanel formHeader = new JPanel(new BorderLayout());
        formHeader.setOpaque(false);
        formHeader.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JLabel formTitle = new JLabel("Edit Student Details");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        formTitle.setForeground(new Color(52, 152, 219));

        formHeader.add(formTitle, BorderLayout.WEST);
        formHeader.add(studentIdLabel, BorderLayout.EAST);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = createField();
        departmentField = createField();
        yearField = createField();
        semesterField = createField();
        subject1Field = createField();
        subject2Field = createField();
        subject3Field = createField();

        addRow(formPanel, gbc, 0, "Full Name", nameField, 4);
        addRow(formPanel, gbc, 1, "Department", departmentField, 4);
        addRow(formPanel, gbc, 2, "Year", yearField, 1);
        addRow(formPanel, gbc, 2, "Semester", semesterField, 1, 2);
        addRow(formPanel, gbc, 3, "Subject 1", subject1Field, 1);
        addRow(formPanel, gbc, 3, "Subject 2", subject2Field, 1, 2);
        addRow(formPanel, gbc, 4, "Subject 3", subject3Field, 1);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        buttonPanel.setBackground(new Color(248, 249, 252));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.setBackground(new Color(108, 117, 125));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setOpaque(true);
        cancelButton.setContentAreaFilled(true);
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);

        JButton updateButton = new JButton("Update Student");
        updateButton.setPreferredSize(new Dimension(150, 45));
        updateButton.setBackground(new Color(241, 196, 15));
        updateButton.setForeground(Color.WHITE);
        updateButton.setOpaque(true);
        updateButton.setContentAreaFilled(true);
        updateButton.setBorderPainted(false);
        updateButton.setFocusPainted(false);

        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton);

        formContainer.add(formHeader);
        formContainer.add(formPanel);
        formContainer.add(buttonPanel);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(new Color(245, 247, 250));

        body.add(searchPanel);
        body.add(formContainer);

        add(headerPanel, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);

        loadButton.addActionListener(e -> loadStudent(studentIdLabel));
        updateButton.addActionListener(e -> updateStudent());
        cancelButton.addActionListener(e -> resetForm());
        idField.addActionListener(e -> loadStudent(studentIdLabel));
    }

    private JTextField createField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setEnabled(false);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row,
                        String label, JTextField field, int width) {
        addRow(panel, gbc, row, label, field, width, 0);
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row,
                        String label, JTextField field, int width, int col) {

        gbc.gridy = row;
        gbc.gridx = col;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = col + 1;
        gbc.gridwidth = width;
        panel.add(field, gbc);
    }

    private void loadStudent(JLabel label) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            currentStudent = new StudentDAO().getStudentById(id);

            if (currentStudent == null) {
                JOptionPane.showMessageDialog(this, "Student not found");
                return;
            }

            label.setText("Student ID: " + currentStudent.getId());
            enableFields(true);

            nameField.setText(currentStudent.getName());
            departmentField.setText(currentStudent.getDepartment());
            yearField.setText(String.valueOf(currentStudent.getYear()));
            semesterField.setText(String.valueOf(currentStudent.getSemester()));
            subject1Field.setText(String.valueOf(currentStudent.getSubject1()));
            subject2Field.setText(String.valueOf(currentStudent.getSubject2()));
            subject3Field.setText(String.valueOf(currentStudent.getSubject3()));

            formContainer.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Student ID");
        }
    }

    private void enableFields(boolean enable) {
        nameField.setEnabled(enable);
        departmentField.setEnabled(enable);
        yearField.setEnabled(enable);
        semesterField.setEnabled(enable);
        subject1Field.setEnabled(enable);
        subject2Field.setEnabled(enable);
        subject3Field.setEnabled(enable);
    }

    private void resetForm() {
        idField.setText("");
        enableFields(false);
        formContainer.setVisible(false);
        currentStudent = null;
    }

    private void updateStudent() {
        try {
            int s1 = Integer.parseInt(subject1Field.getText());
            int s2 = Integer.parseInt(subject2Field.getText());
            int s3 = Integer.parseInt(subject3Field.getText());

            currentStudent.setName(nameField.getText());
            currentStudent.setDepartment(departmentField.getText());
            currentStudent.setYear(Integer.parseInt(yearField.getText()));
            currentStudent.setSemester(Integer.parseInt(semesterField.getText()));
            currentStudent.setSubject1(s1);
            currentStudent.setSubject2(s2);
            currentStudent.setSubject3(s3);
            currentStudent.setScore((s1 + s2 + s3) / 3.0);

            new StudentDAO().updateStudent(currentStudent);

            JOptionPane.showMessageDialog(this, "Student updated successfully");
            resetForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid input data");
        }
    }
}
