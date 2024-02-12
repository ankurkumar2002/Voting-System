import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CandidateRegistration extends JFrame {

    private JTextField candidateNameField;
    private JTextField partyAffiliationField;
    private JButton registerCandidateButton;

    public CandidateRegistration() {
        setTitle("Candidate Registration");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        addListeners();

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Candidate Name:"));
        candidateNameField = new JTextField();
        add(candidateNameField);

        add(new JLabel("Party Affiliation:"));
        partyAffiliationField = new JTextField();
        add(partyAffiliationField);

        add(new JLabel()); 
        registerCandidateButton = new JButton("Register Candidate");
        add(registerCandidateButton);
    }

    private void addListeners() {
        registerCandidateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerCandidate();
            }
        });
    }

    private void registerCandidate() {
        String candidateName = candidateNameField.getText();
        String partyAffiliation = partyAffiliationField.getText();

        try {
            Connection connection = DatabaseConnection.connect();

           
            String sql = "INSERT INTO candidates (candidate_name, party_affiliation) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, candidateName);
                preparedStatement.setString(2, partyAffiliation);

                
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Candidate registered successfully!");

                
                candidateNameField.setText("");
                partyAffiliationField.setText("");
            }

            
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CandidateRegistration());
    }
}
