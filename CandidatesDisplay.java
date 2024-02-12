import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CandidatesDisplay extends JFrame {

    public CandidatesDisplay() {
        setTitle("Candidates Display");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea candidatesTextArea = new JTextArea();
        candidatesTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(candidatesTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        try {
            Connection connection = DatabaseConnection.connect();

            String sql = "SELECT * FROM candidates";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String candidateName = resultSet.getString("candidate_name");
                        String partyAffiliation = resultSet.getString("party_affiliation");
                        candidatesTextArea.append("Candidate: " + candidateName + ", Party: " + partyAffiliation + "\n");
                    }
                }
            }

            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        add(panel);
    }
}
