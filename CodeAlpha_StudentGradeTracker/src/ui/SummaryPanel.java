package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import dao.StudentDAO;

public class SummaryPanel extends JPanel {

    private JLabel totalLabel;
    private JLabel avgLabel;
    private JLabel maxLabel;
    private JLabel minLabel;

    public SummaryPanel(MainFrame mainFrame) {

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JLabel titleLabel = new JLabel("Performance Summary");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(33, 37, 41));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(110, 35));
        refreshButton.setBackground(new Color(52, 152, 219));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setOpaque(true);
        refreshButton.setContentAreaFilled(true);
        refreshButton.setBorderPainted(false);
        refreshButton.setFocusPainted(false);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(refreshButton, BorderLayout.EAST);

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        statsPanel.setBackground(new Color(245, 247, 250));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

        totalLabel = new JLabel("0");
        avgLabel = new JLabel("0.00");
        maxLabel = new JLabel("0.00");
        minLabel = new JLabel("0.00");

        statsPanel.add(createStatCard("Total Students", totalLabel, new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Average Score", avgLabel, new Color(46, 204, 113)));
        statsPanel.add(createStatCard("Highest Score", maxLabel, new Color(155, 89, 182)));
        statsPanel.add(createStatCard("Lowest Score", minLabel, new Color(231, 76, 60)));

        JTextArea analysisArea = new JTextArea();
        analysisArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        analysisArea.setLineWrap(true);
        analysisArea.setWrapStyleWord(true);
        analysisArea.setEditable(false);
        analysisArea.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(analysisArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JPanel analysisPanel = new JPanel(new BorderLayout());
        analysisPanel.setBackground(Color.WHITE);
        analysisPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        JLabel analysisTitle = new JLabel("Performance Analysis");
        analysisTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        analysisTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        analysisPanel.add(analysisTitle, BorderLayout.NORTH);
        analysisPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 247, 250));

        contentPanel.add(statsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(analysisPanel);

        add(headerPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        refreshButton.addActionListener(e -> loadSummaryData(analysisArea));

        SwingUtilities.invokeLater(() -> loadSummaryData(analysisArea));
    }

    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(108, 117, 125));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        textPanel.add(valueLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        textPanel.add(titleLabel);

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    private void loadSummaryData(JTextArea analysisArea) {

        try {
            StudentDAO dao = new StudentDAO();

            int total = dao.getTotalStudents();
            double[] summary = dao.getSummary();

            totalLabel.setText(String.valueOf(total));
            avgLabel.setText(String.format("%.2f", summary[0]));
            maxLabel.setText(String.format("%.2f", summary[1]));
            minLabel.setText(String.format("%.2f", summary[2]));

            analysisArea.setText(generateAnalysisText(total, summary[0], summary[1], summary[2]));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Unable to load summary data",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String generateAnalysisText(int total, double avg, double max, double min) {

        if (total == 0) {
            return "No student records available.\n\nAdd students to view performance analysis.";
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Overall Performance Summary\n");
        sb.append("----------------------------\n\n");
        sb.append("Total Students: ").append(total).append("\n");
        sb.append(String.format("Average Score: %.2f\n", avg));
        sb.append(String.format("Highest Score: %.2f\n", max));
        sb.append(String.format("Lowest Score: %.2f\n\n", min));

        if (avg >= 80) {
            sb.append("Overall performance is excellent.\n");
        } else if (avg >= 65) {
            sb.append("Overall performance is good.\n");
        } else {
            sb.append("Overall performance needs improvement.\n");
        }

        sb.append(String.format("\nScore Range: %.2f\n", max - min));
        return sb.toString();
    }
}
