import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection connect() throws SQLException, ClassNotFoundException {
        
        Class.forName("com.mysql.cj.jdbc.Driver");

        String url = "jdbc:mysql://localhost:3306/voting_system";
        String user = "root";
        String passwordDB = "Admin";
        return DriverManager.getConnection(url, user, passwordDB);
    }
}
