package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
	private static final String URL = "jdbc:mysql://localhost:3306/ActivityAce";
    private static final String USER = "root";
    private static final String PASSWORD = "password2024";

    public static Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", USER);
        properties.setProperty("password", PASSWORD);
        properties.setProperty("useSSL", "true"); // Use SSL for encryption
        properties.setProperty("requireSSL", "true"); // Require SSL for all connections
        properties.setProperty("verifyServerCertificate", "false"); // Trust server certificate without verification
        properties.setProperty("serverTimezone", "UTC"); // Set server timezone
        properties.setProperty("connectTimeout", "30000"); // Connection timeout in milliseconds
        properties.setProperty("socketTimeout", "60000"); // Socket timeout in milliseconds

        return DriverManager.getConnection(URL, properties);
    }
}
