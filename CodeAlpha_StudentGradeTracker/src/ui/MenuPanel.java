package ui;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private MainFrame mainFrame;

    public MenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 250));

        JLabel header = new JLabel("Dashboard");
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel grid = new JPanel(new GridLayout(2, 3, 20, 20));
        grid.setBackground(new Color(248, 249, 250));
        grid.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JButton addBtn = createButton("Add Student", new Color(25, 135, 84));
        JButton viewBtn = createButton("View Student", new Color(13, 110, 253));
        JButton editBtn = createButton("Edit Student", new Color(255, 193, 7));
        JButton deleteBtn = createButton("Delete Student", new Color(220, 53, 69));
        JButton summaryBtn = createButton("Summary", new Color(108, 117, 125));

        grid.add(addBtn);
        grid.add(viewBtn);
        grid.add(editBtn);
        grid.add(deleteBtn);
        grid.add(summaryBtn);

        add(header, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);

        addBtn.addActionListener(e -> mainFrame.showPage("ADD"));
        viewBtn.addActionListener(e -> mainFrame.showPage("VIEW"));
        editBtn.addActionListener(e -> mainFrame.showPage("EDIT"));
        deleteBtn.addActionListener(e -> mainFrame.showPage("DELETE"));
        summaryBtn.addActionListener(e -> mainFrame.showPage("SUMMARY"));
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(200, 60));
        return btn;
    }
}
