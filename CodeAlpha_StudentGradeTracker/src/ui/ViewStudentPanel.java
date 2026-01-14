package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.StudentDAO;
import model.Student;

public class ViewStudentPanel extends JPanel {

    private JTextField idField;
    private JPanel detailsCard;

    public ViewStudentPanel(MainFrame mainFrame) {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel title = new JLabel("View Student");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(33, 37, 41));

        header.add(title, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JLabel idLabel = new JLabel("Student ID");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        idField = new JTextField(10);
        idField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        idField.setPreferredSize(new Dimension(120, 36));

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchButton.setPreferredSize(new Dimension(110, 36));
        searchButton.setBackground(new Color(13, 110, 253));
        searchButton.setForeground(Color.WHITE);
        searchButton.setOpaque(true);
        searchButton.setContentAreaFilled(true);
        searchButton.setBorderPainted(false);
        searchButton.setFocusPainted(false);

        searchPanel.add(idLabel);
        searchPanel.add(idField);
        searchPanel.add(searchButton);

        detailsCard = new JPanel(new GridLayout(0, 2, 15, 12));
        detailsCard.setBackground(Color.WHITE);
        detailsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        detailsCard.setVisible(false);

        JPanel centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.setBackground(new Color(245, 247, 250));
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(30, 40, 40, 40));

        centerWrapper.add(searchPanel);
        centerWrapper.add(Box.createRigidArea(new Dimension(0, 25)));
        centerWrapper.add(detailsCard);

        add(header, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);

        searchButton.addActionListener(e -> loadStudent());
        idField.addActionListener(e -> loadStudent());
    }

    private void loadStudent() {

        detailsCard.removeAll();

        try {
            int id = Integer.parseInt(idField.getText().trim());
            Student student = new StudentDAO().getStudentById(id);

            if (student == null) {
                JOptionPane.showMessageDialog(this, "Student not found");
                detailsCard.setVisible(false);
                return;
            }

            addRow("ID", String.valueOf(student.getId()));
            addRow("Name", student.getName());
            addRow("Department", student.getDepartment());
            addRow("Year", String.valueOf(student.getYear()));
            addRow("Semester", String.valueOf(student.getSemester()));
            addRow("Subject 1", String.valueOf(student.getSubject1()));
            addRow("Subject 2", String.valueOf(student.getSubject2()));
            addRow("Subject 3", String.valueOf(student.getSubject3()));
            addRow("Average Score", String.format("%.2f", student.getScore()));
            addRow("Grade", calculateGrade(student.getScore()));

            detailsCard.setVisible(true);
            detailsCard.revalidate();
            detailsCard.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Enter a valid Student ID");
        }
    }

    private void addRow(String label, String value) {

        JLabel l1 = new JLabel(label);
        l1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l1.setForeground(new Color(90, 90, 90));

        JLabel l2 = new JLabel(value);
        l2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        l2.setForeground(new Color(33, 37, 41));

        detailsCard.add(l1);
        detailsCard.add(l2);
    }

    private String calculateGrade(double avg) {
        if (avg >= 90) return "A+";
        if (avg >= 80) return "A";
        if (avg >= 70) return "B+";
        if (avg >= 60) return "B";
        if (avg >= 50) return "C";
        return "F";
    }
}
