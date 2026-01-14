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

public class DeleteStudentPanel extends JPanel {

    private MainFrame mainFrame;
    private JTextField idField;
    private JPanel detailsPanel;
    private Student studentToDelete;

    public DeleteStudentPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titleLabel = new JLabel("Delete Student");
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

        JButton loadButton = new JButton("Find Student");
        loadButton.setPreferredSize(new Dimension(140, 40));
        loadButton.setBackground(new Color(52, 152, 219));
        loadButton.setForeground(Color.WHITE);
        loadButton.setOpaque(true);
        loadButton.setContentAreaFilled(true);
        loadButton.setBorderPainted(false);
        loadButton.setFocusPainted(false);

        JButton clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(100, 40));
        clearButton.setBackground(new Color(108, 117, 125));
        clearButton.setForeground(Color.WHITE);
        clearButton.setOpaque(true);
        clearButton.setContentAreaFilled(true);
        clearButton.setBorderPainted(false);
        clearButton.setFocusPainted(false);

        searchRow.add(searchLabel);
        searchRow.add(idField);
        searchRow.add(loadButton);
        searchRow.add(clearButton);

        searchPanel.add(searchRow, BorderLayout.CENTER);

        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(new Color(248, 249, 252));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        detailsPanel.setVisible(false);

        JPanel warningPanel = new JPanel(new BorderLayout(15, 0));
        warningPanel.setBackground(new Color(255, 243, 205));
        warningPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 193, 7), 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel warningIcon = new JLabel("⚠");
        warningIcon.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel warningText = new JLabel(
                "<html><b>Warning:</b> This action is permanent and cannot be undone.</html>"
        );
        warningText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        warningText.setForeground(new Color(133, 100, 4));

        warningPanel.add(warningIcon, BorderLayout.WEST);
        warningPanel.add(warningText, BorderLayout.CENTER);

        JPanel detailsCard = new JPanel(new GridLayout(0, 2, 10, 8));
        detailsCard.setBackground(Color.WHITE);
        detailsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        actionPanel.setBackground(new Color(248, 249, 252));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(120, 45));
        cancelButton.setBackground(new Color(108, 117, 125));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setOpaque(true);
        cancelButton.setContentAreaFilled(true);
        cancelButton.setBorderPainted(false);
        cancelButton.setFocusPainted(false);

        JButton deleteButton = new JButton("Delete Permanently");
        deleteButton.setPreferredSize(new Dimension(180, 45));
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setOpaque(true);
        deleteButton.setContentAreaFilled(true);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);

        actionPanel.add(cancelButton);
        actionPanel.add(deleteButton);

        detailsPanel.add(warningPanel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        detailsPanel.add(detailsCard);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        detailsPanel.add(actionPanel);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(new Color(245, 247, 250));

        body.add(searchPanel);
        body.add(detailsPanel);

        add(headerPanel, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);

        loadButton.addActionListener(e -> findStudent(detailsCard));
        clearButton.addActionListener(e -> reset(detailsCard));
        cancelButton.addActionListener(e -> reset(detailsCard));
        deleteButton.addActionListener(e -> deleteStudent());
        idField.addActionListener(e -> findStudent(detailsCard));
    }

    private void findStudent(JPanel grid) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            studentToDelete = new StudentDAO().getStudentById(id);

            if (studentToDelete == null) {
                JOptionPane.showMessageDialog(this, "Student not found");
                return;
            }

            grid.removeAll();

            addRow(grid, "ID", String.valueOf(studentToDelete.getId()));
            addRow(grid, "Name", studentToDelete.getName());
            addRow(grid, "Department", studentToDelete.getDepartment());
            addRow(grid, "Average Score",
                    String.format("%.2f", studentToDelete.getScore()));

            detailsPanel.setVisible(true);
            grid.revalidate();
            grid.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Student ID");
        }
    }

    private void addRow(JPanel panel, String label, String value) {
        JLabel l1 = new JLabel(label);
        l1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel l2 = new JLabel(value);
        l2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(l1);
        panel.add(l2);
    }

    private void deleteStudent() {
        if (studentToDelete == null) return;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to permanently delete this student?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            new StudentDAO().deleteStudent(studentToDelete.getId());
            JOptionPane.showMessageDialog(this, "Student deleted successfully");
            reset(null);
        }
    }

    private void reset(JPanel grid) {
        idField.setText("");
        studentToDelete = null;
        detailsPanel.setVisible(false);
        if (grid != null) {
            grid.removeAll();
            grid.revalidate();
            grid.repaint();
        }
    }
}
