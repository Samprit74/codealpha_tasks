package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dao.StudentDAO;

public class HomePanel extends JPanel {

    private JLabel totalLabel;
    private JLabel avgLabel;
    private JLabel maxLabel;

    public HomePanel(MainFrame mainFrame) {

        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 250));

        add(createTopSpacing(), BorderLayout.NORTH);
        add(createContent(), BorderLayout.CENTER);

        SwingUtilities.invokeLater(this::loadData);
    }

    private Component createTopSpacing() {
        JPanel spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 20));
        spacer.setOpaque(false);
        return spacer;
    }

    private JPanel createContent() {

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JPanel cardsRow = new JPanel(new GridLayout(1, 3, 30, 0));
        cardsRow.setOpaque(false);
        cardsRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

        totalLabel = new JLabel("0");
        avgLabel = new JLabel("0.00");
        maxLabel = new JLabel("0.00");

        cardsRow.add(createCard("Total Students", totalLabel, new Color(13, 110, 253)));
        cardsRow.add(createCard("Average Score", avgLabel, new Color(25, 135, 84)));
        cardsRow.add(createCard("Highest Score", maxLabel, new Color(220, 53, 69)));

        JPanel infoBox = new JPanel(new BorderLayout());
        infoBox.setBackground(Color.WHITE);
        infoBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        infoBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JLabel info = new JLabel(
                "<html><b>Live Data:</b> Statistics are calculated directly from the database.<br>" +
                "Use the sidebar to manage students and view analytics.</html>"
        );
        info.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        info.setForeground(new Color(80, 80, 80));

        infoBox.add(info, BorderLayout.CENTER);

        wrapper.add(cardsRow);
        wrapper.add(Box.createRigidArea(new Dimension(0, 35)));
        wrapper.add(infoBox);
        wrapper.add(Box.createVerticalGlue());

        return wrapper;
    }

    private JPanel createCard(String title, JLabel value, Color accent) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        value.setFont(new Font("Segoe UI", Font.BOLD, 36));
        value.setForeground(accent);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(120, 120, 120));

        JPanel text = new JPanel();
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.setOpaque(false);

        text.add(value);
        text.add(Box.createRigidArea(new Dimension(0, 10)));
        text.add(titleLabel);

        card.add(text, BorderLayout.CENTER);

        return card;
    }

    private void loadData() {

        StudentDAO dao = new StudentDAO();

        int total = dao.getTotalStudents();
        double[] summary = dao.getSummary();

        totalLabel.setText(String.valueOf(total));
        avgLabel.setText(String.format("%.2f", summary[0]));
        maxLabel.setText(String.format("%.2f", summary[1]));
    }
}
