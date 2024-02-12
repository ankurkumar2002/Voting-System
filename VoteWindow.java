import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VoteWindow extends JFrame {

    private JComboBox<String> candidateComboBox;
    private JButton voteButton;

    private String voterUsername;

    public VoteWindow(String voterUsername) {
        this.voterUsername = voterUsername;

        setTitle("Vote for a Candidate");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        panel.add(new JLabel("Select a Candidate to Vote:"));

        
        candidateComboBox = new JComboBox<>();
        populateCandidateComboBox();
        panel.add(candidateComboBox);

        voteButton = new JButton("Vote");
        panel.add(voteButton);

        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voteForCandidate();
            }
        });

        add(panel);
    }

    private void populateCandidateComboBox() {
        try {
            Connection connection = DatabaseConnection.connect();

            String sql = "SELECT candidate_name FROM candidates";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String candidateName = resultSet.getString("candidate_name");
                        candidateComboBox.addItem(candidateName);
                    }
                }
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void voteForCandidate() {
        String selectedCandidate = (String) candidateComboBox.getSelectedItem();

        try {
            Connection connection = DatabaseConnection.connect();

            String getCandidateIdSql = "SELECT candidate_id FROM candidates WHERE candidate_name = ?";
            try (PreparedStatement getCandidateIdStatement = connection.prepareStatement(getCandidateIdSql)) {
                getCandidateIdStatement.setString(1, selectedCandidate);
                try (ResultSet resultSet = getCandidateIdStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int candidateId = resultSet.getInt("candidate_id");

                        String checkVoteSql = "SELECT * FROM votes WHERE voter_username = ?";
                        try (PreparedStatement checkVoteStatement = connection.prepareStatement(checkVoteSql)) {
                            checkVoteStatement.setString(1, voterUsername);
                            try (ResultSet voteResultSet = checkVoteStatement.executeQuery()) {
                                if (voteResultSet.next()) {
                                    JOptionPane.showMessageDialog(this, "You have already voted. Each user can vote only once.", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    String insertVoteSql = "INSERT INTO votes (candidate_id, voter_username) VALUES (?, ?)";
                                    try (PreparedStatement insertVoteStatement = connection.prepareStatement(insertVoteSql)) {
                                        insertVoteStatement.setInt(1, candidateId);
                                        insertVoteStatement.setString(2, voterUsername);

                                        insertVoteStatement.executeUpdate();

                                        JOptionPane.showMessageDialog(this, "Vote recorded successfully!");
                                        dispose(); 
                                        checkForWinner(); 
                                    }
                                }
                            }
                        }
                    }
                }
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkForWinner() {
        try {
            Connection connection = DatabaseConnection.connect();

            String countVotesSql = "SELECT candidates.candidate_name, COUNT(votes.vote_id) AS vote_count " +
                    "FROM candidates " +
                    "LEFT JOIN votes ON candidates.candidate_id = votes.candidate_id " +
                    "GROUP BY candidates.candidate_id " +
                    "ORDER BY vote_count DESC " +
                    "LIMIT 1";
            try (PreparedStatement countVotesStatement = connection.prepareStatement(countVotesSql)) {
                try (ResultSet resultSet = countVotesStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int voteCount = resultSet.getInt("vote_count");
                        if (voteCount >= 5) { 
                            new WinnerDeclaration();
                        }
                    }
                }
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VoteWindow("SampleUser"));
    }
}
