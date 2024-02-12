import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminPanel extends JFrame {

    private JButton manageUsersButton;
    private JButton manageCandidatesButton;
    private JButton setVotingTimelineButton;
    private JButton declareWinnerButton;

    public AdminPanel() {
        setTitle("Admin Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        addListeners();

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        manageUsersButton = new JButton("Manage Users");
        panel.add(manageUsersButton);

        manageCandidatesButton = new JButton("Manage Candidates");
        panel.add(manageCandidatesButton);

        setVotingTimelineButton = new JButton("Set Voting Timeline");
        panel.add(setVotingTimelineButton);

        declareWinnerButton = new JButton("Declare Winner");
        panel.add(declareWinnerButton);

        add(panel);
    }

    private void addListeners() {
        manageUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openManageUsersWindow();
            }
        });

        manageCandidatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openManageCandidatesWindow();
            }
        });

        setVotingTimelineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSetVotingTimelineWindow();
            }
        });

        declareWinnerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                declareWinner();
            }
        });
    }

    private void openManageUsersWindow() {
        
        String[] options = {"Add User", "Edit User", "Delete User", "View Users"};
        int choice = JOptionPane.showOptionDialog(this, "Select an operation:", "Manage Users", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                addUser();
                break;
            case 1:
                editUser();
                break;
            case 2:
                deleteUser();
                break;
            case 3:
                viewUsers();
                break;
        }
    }

    private void openManageCandidatesWindow() {
        
        String[] options = {"Add Candidate", "Edit Candidate", "Delete Candidate", "View Candidates"};
        int choice = JOptionPane.showOptionDialog(this, "Select an operation:", "Manage Candidates", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                addCandidate();
                break;
            case 1:
                editCandidate();
                break;
            case 2:
                deleteCandidate();
                break;
            case 3:
                viewCandidates();
                break;
        }
    }

    private void openSetVotingTimelineWindow() {
        
        String input = JOptionPane.showInputDialog(this, "Enter the voting timeline (e.g., YYYY-MM-DD HH:MM:SS):");
        if (input != null) {
            setVotingTimeline(input);
        }
    }

    private void declareWinner() {
        
        String[] candidates = {"Candidate A", "Candidate B", "Candidate C", "Candidate D"};
        String winner = (String) JOptionPane.showInputDialog(this, "Select the winner:", "Declare Winner", JOptionPane.PLAIN_MESSAGE, null, candidates, candidates[0]);

        if (winner != null) {
            JOptionPane.showMessageDialog(this, "Winner declared: " + winner, "Winner Declaration", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    
    private void addUser() {
        JOptionPane.showMessageDialog(this, "Adding a new user functionality coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editUser() {
        JOptionPane.showMessageDialog(this, "Editing a user functionality coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteUser() {
        JOptionPane.showMessageDialog(this, "Deleting a user functionality coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewUsers() {
        JOptionPane.showMessageDialog(this, "Viewing users functionality coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addCandidate() {
        JOptionPane.showMessageDialog(this, "Adding a new candidate functionality coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editCandidate() {
        JOptionPane.showMessageDialog(this, "Editing a candidate functionality coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteCandidate() {
        JOptionPane.showMessageDialog(this, "Deleting a candidate functionality coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewCandidates() {
        JOptionPane.showMessageDialog(this, "Viewing candidates functionality coming soon!", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setVotingTimeline(String timeline) {
        JOptionPane.showMessageDialog(this, "Setting voting timeline functionality coming soon!\nTimeline: " + timeline, "Info", JOptionPane.INFORMATION_MESSAGE);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminPanel());
    }
}
