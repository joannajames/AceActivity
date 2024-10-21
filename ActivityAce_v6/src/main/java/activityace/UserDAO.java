package activityace;

import Utilities.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

	//Validates the user's credentials by checking the username and password, returns boolean
    public boolean validateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    //Retrieves the user ID for a given username (user id is auto incremented in database)
    public int getUserId(String username) throws SQLException {
        String query = "SELECT user_id FROM users WHERE username = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("user_id");
                }
            }
        }
        throw new SQLException("User not found.");
    }

    //Adds a new user to the database with the provided username and password.
    public void addUser(String username, String password) throws SQLException {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        }
    }
}
