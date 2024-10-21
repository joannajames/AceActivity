package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import activityace.FacadeActivityAce;

public class MainApp extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        showLoginView();
    }

    public void showLoginView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/login_view.fxml"));
            Parent root = loader.load();

            LoginController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.setTitle("Activity Ace Login");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showMainView(FacadeActivityAce facadeActivityAce) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main_view.fxml"));
            Parent root = loader.load();

            MainViewController controller = loader.getController();
            controller.setFacadeActivityAce(facadeActivityAce);

            primaryStage.setTitle("Activity Ace Main");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
