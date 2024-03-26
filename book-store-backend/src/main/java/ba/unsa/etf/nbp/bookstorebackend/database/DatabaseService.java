package ba.unsa.etf.nbp.bookstorebackend.database;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class DatabaseService {
    private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static String url = System.getenv("DATABASE_URL");
    private static String username = System.getenv("DATABASE_USERNAME");
    private static String password  = System.getenv("DATABASE_PASSWORD");

    private Connection connection;

    public void startDBConnection() {
        connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        if (connection == null) {
            startDBConnection();
        }
        return connection;
    }

    @PreDestroy
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection = null;
            }
        }
    }
}
