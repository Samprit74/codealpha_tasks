package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private SidebarPanel sidebar;
    private HeaderPanel header;

    public MainFrame() {

        setTitle("Student Grade Tracker Pro");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            ImageIcon icon = new ImageIcon("icon.png");
            setIconImage(icon.getImage());
        } catch (Exception ignored) {}

        setLayout(new BorderLayout());

        header = new HeaderPanel();
        add(header, BorderLayout.NORTH);

        sidebar = new SidebarPanel(this);
        sidebar.setPreferredSize(new Dimension(250, 0));
        add(sidebar, BorderLayout.WEST);

        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(new Color(248, 249, 250));

        contentPanel.add(new HomePanel(this), "HOME");
        contentPanel.add(new AddStudentPanel(this), "ADD");
        contentPanel.add(new ViewStudentPanel(this), "VIEW");
        contentPanel.add(new EditStudentPanel(this), "EDIT");
        contentPanel.add(new DeleteStudentPanel(this), "DELETE");
        contentPanel.add(new SummaryPanel(this), "SUMMARY");

        add(contentPanel, BorderLayout.CENTER);

        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(33, 37, 41));
        statusBar.setPreferredSize(new Dimension(0, 30));

        JLabel statusLabel = new JLabel(" Ready");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel versionLabel = new JLabel("v2.0.1  ");
        versionLabel.setForeground(new Color(200, 200, 200));
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        statusBar.add(statusLabel, BorderLayout.WEST);
        statusBar.add(versionLabel, BorderLayout.EAST);

        add(statusBar, BorderLayout.SOUTH);

        showPage("HOME");
        setVisible(true);
    }

    public void showPage(String pageName) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, pageName);
        header.setTitle(getPageTitle(pageName));
    }

    private String getPageTitle(String pageName) {
        switch (pageName) {
            case "HOME": return "Dashboard Overview";
            case "ADD": return "Add New Student";
            case "VIEW": return "Student Details";
            case "EDIT": return "Edit Student Record";
            case "DELETE": return "Delete Student";
            case "SUMMARY": return "Performance Summary";
            default: return "Student Grade Tracker";
        }
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );

            UIManager.put("Button.background", new Color(33, 37, 41));
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.focus", new Color(0, 0, 0, 0));
            UIManager.put("Button.border", BorderFactory.createEmptyBorder());
            UIManager.put("Button.select", new Color(33, 37, 41));

        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(MainFrame::new);
    }
}
