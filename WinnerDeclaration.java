import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WinnerDeclaration extends JFrame {

    public WinnerDeclaration() {
        setTitle("Winner Declaration");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        panel.add(new JLabel("Winner:"));

        JTextArea winnerTextArea = new JTextArea();
        winnerTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(winnerTextArea);
        panel.add(scrollPane);

        JButton declareWinnerButton = new JButton("Declare Winner");
        panel.add(declareWinnerButton);

        declareWinnerButton.addActionListener(e -> declareWinner(winnerTextArea));

        add(panel);

        displayWinner(winnerTextArea);
    }

    private void displayWinner(JTextArea winnerTextArea) {
        try {
            Connection connection = DatabaseConnection.connect();

            String sql = "SELECT candidates.candidate_name, COUNT(votes.vote_id) AS vote_count " +
                    "FROM candidates " +
                    "LEFT JOIN votes ON candidates.candidate_id = votes.candidate_id " +
                    "GROUP BY candidates.candidate_id " +
                    "ORDER BY vote_count DESC " +
                    "LIMIT 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String winnerName = resultSet.getString("candidate_name");
                        int voteCount = resultSet.getInt("vote_count");
                        winnerTextArea.append("Candidate: " + winnerName + "\nVote Count: " + voteCount);
                    } else {
                        winnerTextArea.setText("No votes recorded yet.");
                    }
                }
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void declareWinner(JTextArea winnerTextArea) {
        JOptionPane.showMessageDialog(this, "Winner declared successfully!");
        dispose(); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WinnerDeclaration());
    }
}
