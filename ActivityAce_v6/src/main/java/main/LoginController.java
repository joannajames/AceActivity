package main;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import activityace.UserDAO;
import activityace.FacadeActivityAce;

import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private MainApp mainApp;

    /**
     * Sets the main app reference for this controller.
     * 
     * @param mainApp The main application.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Handles the login process.
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        UserDAO userDAO = new UserDAO();

        try {
            if (userDAO.validateUser(username, password)) {
                // Initialize the FacadeActivityAce with the validated user
                FacadeActivityAce facadeActivityAce = FacadeActivityAce.getInstance(username, password);
                
                // Pass control to the main application
                mainApp.showMainView(facadeActivityAce);
            } else {
                showAlert("Invalid Credentials", "Incorrect username or password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An error occurred while accessing the database. Please try again later.");
        }
    }

    /**
     * Handles the creation of a new user.
     */
    @FXML
    private void handleCreateUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        UserDAO userDAO = new UserDAO();

        try {
            try {
                userDAO.getUserId(username);
                showAlert("User Exists", "The username is already taken. Please choose another username.");
            } catch (SQLException e) {
                userDAO.addUser(username, password);
                showAlert("User Created", "Your account has been created. You can now log in.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "An error occurred while accessing the database. Please try again later.");
        }
    }

    /**
     * Displays an alert dialog.
     * 
     * @param title The title of the alert.
     * @param message The message to display.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
