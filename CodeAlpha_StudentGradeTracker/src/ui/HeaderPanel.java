package ui;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeaderPanel extends JPanel {

    private final JLabel titleLabel;

    public HeaderPanel() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        setPreferredSize(new Dimension(0, 90));

        titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(33, 37, 41));

        JLabel dateLabel = new JLabel(
                new SimpleDateFormat("EEEE, MMMM dd, yyyy").format(new Date())
        );
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateLabel.setForeground(new Color(120, 120, 120));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(6));
        textPanel.add(dateLabel);

        add(textPanel, BorderLayout.WEST);
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }
}
