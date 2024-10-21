package main;

import activityace.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import Utilities.ValidationUtil;

public class MainViewController {
    @FXML
    private ComboBox<String> activityTypeComboBox;
    @FXML
    private TextField durationField;
    @FXML
    private ComboBox<String> locationComboBox;
    @FXML
    private TextField distanceField;
    @FXML
    private TextField exerciseNameField;
    @FXML
    private TextField setsField;
    @FXML
    private TextField repsField;
    @FXML
    private TextField weightField;
    @FXML
    private TextField elevationField;

    @FXML
    private ComboBox<String> progressTypeComboBox;
    
    @FXML
    private ComboBox<String> viewActivityTypeComboBox;
    
    @FXML
    private ListView<String> activitiesListView; // Add a ListView to display activities

    private FacadeActivityAce facadeActivityAce;

    public void setFacadeActivityAce(FacadeActivityAce facadeActivityAce) {
        this.facadeActivityAce = facadeActivityAce;
    }

    @FXML
    public void initialize() {
    	
        activityTypeComboBox.setItems(FXCollections.observableArrayList("Running", "Weightlifting", "Cycling"));
        locationComboBox.setItems(FXCollections.observableArrayList("Indoors", "Outdoors"));
        activityTypeComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> handleActivityTypeChange(newValue));
        
        viewActivityTypeComboBox.setItems(FXCollections.observableArrayList("All", "Running", "Weightlifting", "Cycling"));
        viewActivityTypeComboBox.setValue("All");
        
        progressTypeComboBox.setItems(FXCollections.observableArrayList("Running Pace", "Cycling Distance"));
        progressTypeComboBox.setValue("Running Pace");
    }

    @FXML
    private void handleAddActivity() {
        String activityType = activityTypeComboBox.getValue();
        int duration = Integer.parseInt(durationField.getText());
        LocalDateTime dateTime = LocalDateTime.now();
        Activity activity = null;

        try {
            switch (activityType.toLowerCase()) {
                case "running":
                    String location = locationComboBox.getValue().substring(0, 1);
                    float distance = Float.parseFloat(distanceField.getText());
                    ValidationUtil.checkPositive(distance, "Distance");
                    activity = new Running(duration, dateTime, distance, location);
                    break;
                case "weightlifting":
                    String exerciseName = exerciseNameField.getText();
                    int sets = Integer.parseInt(setsField.getText());
                    int reps = Integer.parseInt(repsField.getText());
                    int weight = Integer.parseInt(weightField.getText());
                    ValidationUtil.checkPositive(sets, "Number of sets");
                    ValidationUtil.checkPositive(reps, "Number of repetitions");
                    ValidationUtil.checkPositive(weight, "Weight lifted");
                    activity = new Lifting(duration, dateTime, exerciseName, sets, reps, weight);
                    break;
                case "cycling":
                    float cyclingDistance = Float.parseFloat(distanceField.getText());
                    int elevation = Integer.parseInt(elevationField.getText());
                    ValidationUtil.checkPositive(cyclingDistance, "Distance");
                    ValidationUtil.checkElevation(elevation, "Elevation");
                    activity = new Cycling(duration, dateTime, cyclingDistance, elevation);
                    break;
                default:
                    throw new Exception("Unknown activity type.");
            }
            facadeActivityAce.addActivity(activity);
            showAlert(Alert.AlertType.INFORMATION, "Activity Added", "Your activity has been added successfully.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void handleViewActivities() {
        String selectedType = viewActivityTypeComboBox.getValue();
        try {
            List<Activity> activities = facadeActivityAce.getActivities();
            ObservableList<String> activitiesList = FXCollections.observableArrayList();

            List<Activity> filteredActivities;
            switch (selectedType.toLowerCase()) {
                case "running":
                    filteredActivities = activities.stream()
                            .filter(activity -> activity instanceof Running)
                            .collect(Collectors.toList());
                    break;
                case "weightlifting":
                    filteredActivities = activities.stream()
                            .filter(activity -> activity instanceof Lifting)
                            .collect(Collectors.toList());
                    break;
                case "cycling":
                    filteredActivities = activities.stream()
                            .filter(activity -> activity instanceof Cycling)
                            .collect(Collectors.toList());
                    break;
                default:
                    filteredActivities = activities;
                    break;
            }

            for (Activity activity : filteredActivities) {
                activitiesList.add(activity.toString());
            }
            activitiesListView.setItems(activitiesList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load activities.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewProgress() {
    	String selectedType = progressTypeComboBox.getValue();
        try {
            List<Activity> activities = facadeActivityAce.getActivities();
            LineChart<Number, Number> lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
            lineChart.setTitle("Progress Over Time");

            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(selectedType);

            XYChart.Series<Number, Number> averageSeries = new XYChart.Series<>();
            averageSeries.setName("Average " + selectedType);

            int index = 1;
            double average = 0;
            switch (selectedType.toLowerCase()) {
                case "running pace":
                    average = facadeActivityAce.getAverageRunningPace();
                    for (Activity activity : activities) {
                        if (activity instanceof Running) {
                            Running running = (Running) activity;
                            double pace = running.getDuration() / running.getDistance(); // pace in minutes per mile
                            series.getData().add(new XYChart.Data<>(index++, pace));
                        }
                    }
                    break;
                case "cycling distance":
                    average = facadeActivityAce.getAverageCyclingDistance();
                    for (Activity activity : activities) {
                        if (activity instanceof Cycling) {
                            Cycling cycling = (Cycling) activity;
                            double distance = cycling.getDistance();
                            series.getData().add(new XYChart.Data<>(index++, distance));
                        }
                    }
                    break;
            }

            for (int i = 1; i <= index - 1; i++) {
                averageSeries.getData().add(new XYChart.Data<>(i, average));
            }

            lineChart.getData().addAll(series, averageSeries);

            Stage stage = new Stage();
            stage.setTitle("Activity Progress");
            Scene scene = new Scene(lineChart, 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load progress data.");
            e.printStackTrace();
        }
    }

    private void handleActivityTypeChange(String activityType) {
        // Reset all fields
        locationComboBox.setDisable(true);
        distanceField.setDisable(true);
        exerciseNameField.setDisable(true);
        setsField.setDisable(true);
        repsField.setDisable(true);
        weightField.setDisable(true);
        elevationField.setDisable(true);

        switch (activityType.toLowerCase()) {
            case "running":
                locationComboBox.setDisable(false);
                distanceField.setDisable(false);
                break;
            case "weightlifting":
                exerciseNameField.setDisable(false);
                setsField.setDisable(false);
                repsField.setDisable(false);
                weightField.setDisable(false);
                break;
            case "cycling":
                distanceField.setDisable(false);
                elevationField.setDisable(false);
                break;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
