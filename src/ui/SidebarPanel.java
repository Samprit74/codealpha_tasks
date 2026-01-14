package ui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SidebarPanel extends JPanel {

    private final MainFrame mainFrame;
    private final JPanel navPanel;
    private final Map<String, JButton> navButtons = new HashMap<>();

    private static final Color BG = new Color(33, 37, 41);
    private static final Color BG_DARK = new Color(25, 29, 35);
    private static final Color HOVER = new Color(44, 48, 53);
    private static final Color ACTIVE = new Color(13, 110, 253);

    public SidebarPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(new BorderLayout());
        setBackground(BG);
        setPreferredSize(new Dimension(230, 0));

        add(createHeader(), BorderLayout.NORTH);
        navPanel = createNavigation();
        add(navPanel, BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        setActive("HOME");
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(BG_DARK);
        header.setPreferredSize(new Dimension(230, 110));
        header.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        JLabel title = new JLabel("GRADE TRACKER");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Admin Dashboard");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(180, 180, 180));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(Box.createVerticalGlue());
        header.add(title);
        header.add(Box.createRigidArea(new Dimension(0, 6)));
        header.add(subtitle);
        header.add(Box.createVerticalGlue());

        return header;
    }

    private JPanel createNavigation() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        addNav(panel, "Dashboard", "HOME");
        addNav(panel, "Add Student", "ADD");
        addNav(panel, "View Student", "VIEW");
        addNav(panel, "Edit Student", "EDIT");
        addNav(panel, "Delete Student", "DELETE");
        addNav(panel, "Summary", "SUMMARY");

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private void addNav(JPanel panel, String text, String page) {
        JButton btn = createNavButton(text, page);
        navButtons.put(page, btn);
        panel.add(btn);
    }

    private JButton createNavButton(String text, String page) {

        JButton button = new JButton(text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(BG);
        button.setBorder(BorderFactory.createEmptyBorder(12, 22, 12, 22));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        button.setOpaque(true);
        button.setBorderPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (button.getBackground() != ACTIVE) {
                    button.setBackground(HOVER);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (button.getBackground() != ACTIVE) {
                    button.setBackground(BG);
                }
            }
        });

        button.addActionListener(e -> {
            setActive(page);
            mainFrame.showPage(page);
        });

        return button;
    }

    public void setActive(String page) {
        for (JButton btn : navButtons.values()) {
            btn.setBackground(BG);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
        JButton active = navButtons.get(page);
        if (active != null) {
            active.setBackground(ACTIVE);
            active.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }
    }

    private JPanel createFooter() {

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(BG_DARK);
        footer.setPreferredSize(new Dimension(230, 50));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel user = new JLabel("Logged in as: Admin");
        user.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        user.setForeground(new Color(200, 200, 200));

        footer.add(user, BorderLayout.WEST);
        return footer;
    }
}
