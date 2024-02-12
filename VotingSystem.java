import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VotingSystem extends JFrame {

    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;
    private JButton logoutButton;

    private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;
    private JButton registerButton;

    private JButton candidateRegistrationButton;
    private JButton viewCandidatesButton;
    private JButton voteButton; 

    private boolean isLoggedIn = false;

    public VotingSystem() {
        setTitle("Voting System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
        addListeners();

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2, 10, 10));

        loginPanel.add(new JLabel("Username:"));
        loginUsernameField = new JTextField();
        loginPanel.add(loginUsernameField);

        loginPanel.add(new JLabel("Password:"));
        loginPasswordField = new JPasswordField();
        loginPanel.add(loginPasswordField);

        loginPanel.add(new JLabel()); 
        loginButton = new JButton("Login");
        loginPanel.add(loginButton);

        tabbedPane.addTab("Login", loginPanel);

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridLayout(3, 2, 10, 10));

        registerPanel.add(new JLabel("Username:"));
        registerUsernameField = new JTextField();
        registerPanel.add(registerUsernameField);

        registerPanel.add(new JLabel("Password:"));
        registerPasswordField = new JPasswordField();
        registerPanel.add(registerPasswordField);

        registerPanel.add(new JLabel()); 
        registerButton = new JButton("Register");
        registerPanel.add(registerButton);

        tabbedPane.addTab("Register", registerPanel);

        candidateRegistrationButton = new JButton("Candidate Registration");
        tabbedPane.addTab("Candidate Registration", candidateRegistrationButton);

        viewCandidatesButton = new JButton("View Candidates");
        tabbedPane.addTab("View Candidates", viewCandidatesButton);

        voteButton = new JButton("Vote");
        tabbedPane.addTab("Vote", voteButton);
        voteButton.setEnabled(false); 

        logoutButton = new JButton("Logout");
        tabbedPane.addTab("Logout", logoutButton);
        logoutButton.setEnabled(false); 

        add(tabbedPane);
    }

    private void addListeners() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginUsernameField.getText();
                char[] passwordChars = loginPasswordField.getPassword();
                String password = new String(passwordChars);
                loginUser(username, password);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = registerUsernameField.getText();
                char[] passwordChars = registerPasswordField.getPassword();
                String password = new String(passwordChars);
                UserRegistration.registerUser(username, password);
            }
        });

        candidateRegistrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCandidateRegistrationWindow();
            }
        });

        viewCandidatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCandidates();
            }
        });

        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openVoteWindow();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
    }

    private void loginUser(String username, String password) {
        
        isLoggedIn = true;
        loginButton.setEnabled(false);
        registerButton.setEnabled(false);
        candidateRegistrationButton.setEnabled(false);
        viewCandidatesButton.setEnabled(true);
        voteButton.setEnabled(true);
        logoutButton.setEnabled(true);
    }

    private void openCandidateRegistrationWindow() {
        new CandidateRegistration();
    }

    private void viewCandidates() {
        if (isLoggedIn) {
            new CandidatesDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "Please log in to view candidates.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openVoteWindow() {
        if (isLoggedIn) {
            new VoteWindow(loginUsernameField.getText());
        } else {
            JOptionPane.showMessageDialog(this, "Please log in to vote.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logout() {
        isLoggedIn = false;
        loginButton.setEnabled(true);
        registerButton.setEnabled(true);
        candidateRegistrationButton.setEnabled(true);
        viewCandidatesButton.setEnabled(false);
        voteButton.setEnabled(false);
        logoutButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VotingSystem());
    }
}
