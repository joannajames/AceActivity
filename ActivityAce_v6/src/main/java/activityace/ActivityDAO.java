package activityace;
import Utilities.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActivityDAO {
	
	/**
	 * Adds a new activity for a given user ID to the database.
	 * Postconditions:
	 * - The activity is inserted into the 'activities' table in the database with all relevant fields set correctly.
	 * - The activity's type-specific attributes are handled correctly and inserted into the respective columns.
	 * - If the activity type is unknown, a SQLException is thrown.
	 * - The database connection is closed after the operation is completed.
	 * - The PreparedStatement is closed after the operation is completed.
	 */
	public void addActivity(Activity activity, int userId) throws SQLException {
        String query = "INSERT INTO activities (user_id, type, duration, date_time, distance, elevation, exercise_name, sets, reps, weight) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setString(2, activity.getType());
            statement.setInt(3, activity.getDuration());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(activity.getDateTime()));
            
            // Handle specific attributes for different activity types
            if (activity instanceof Running) {
                Running running = (Running) activity;
                statement.setFloat(5, running.getDistance());
                statement.setInt(6, 0); // Elevation not applicable for Running
                statement.setString(7, null); // Exercise name not applicable for Running
                statement.setInt(8, 0); // Sets not applicable for Running
                statement.setInt(9, 0); // Reps not applicable for Running
                statement.setInt(10, 0); // Weight not applicable for Running
            } else if (activity instanceof Cycling) {
                Cycling cycling = (Cycling) activity;
                statement.setFloat(5, cycling.getDistance());
                statement.setInt(6, cycling.getElevation());
                statement.setString(7, null); // Exercise name not applicable for Cycling
                statement.setInt(8, 0); // Sets not applicable for Cycling
                statement.setInt(9, 0); // Reps not applicable for Cycling
                statement.setInt(10, 0); // Weight not applicable for Cycling
            } else if (activity instanceof Lifting) {
                Lifting lifting = (Lifting) activity;
                statement.setFloat(5, 0); // Distance not applicable for Lifting
                statement.setInt(6, 0); // Elevation not applicable for Lifting
                statement.setString(7, lifting.getExerciseName());
                statement.setInt(8, lifting.getSets());
                statement.setInt(9, lifting.getReps());
                statement.setInt(10, lifting.getWeight());
            } else {
                throw new SQLException("Unknown activity type.");
            }

            statement.executeUpdate();
        }
    }
	/**
	 * Retrieves a list of activities for a given user ID from the database.
	 * 
	 *  Postconditions:
	 * - Returns a list of Activity objects, each properly initialized based on the type of activity.
	 * - If no activities are found for the given userId, returns an empty list.
	 * - The list contains all activities associated with the specified userId from the database.
	 */
	public List<Activity> getActivities(int userId) throws SQLException {
        List<Activity> activities = new ArrayList<>();
        String query = "SELECT * FROM activities WHERE user_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String type = resultSet.getString("type");
                    int duration = resultSet.getInt("duration");
                    LocalDateTime dateTime = resultSet.getTimestamp("date_time").toLocalDateTime();
                    Activity activity = null;

                    switch (type.toLowerCase()) {
                        case "running":
                            float distance = resultSet.getFloat("distance");
                            String location = resultSet.getBoolean("terrain") ? "I" : "O";
                            activity = new Running(duration, dateTime, distance, location);
                            break;
                        case "cycling":
                            float cyclingDistance = resultSet.getFloat("distance");
                            int elevation = resultSet.getInt("elevation");
                            activity = new Cycling(duration, dateTime, cyclingDistance, elevation);
                            break;
                        case "lifting":
                            String exerciseName = resultSet.getString("exercise_name");
                            int sets = resultSet.getInt("sets");
                            int reps = resultSet.getInt("reps");
                            int weight = resultSet.getInt("weight");
                            activity = new Lifting(duration, dateTime, exerciseName, sets, reps, weight);
                            break;
                        default:
                            throw new SQLException("Unknown activity type.");
                    }

                    activities.add(activity);
                }
            }
        }
        return activities;
    }
	
	/**
	 * Calculates the average running pace for a given user ID.
	 * 
	 * Postconditions:
	 * - Returns the average running pace as a double value.
	 * - If no running activities are found for the given userId, returns 0.
	 */
	
	 public double getAverageRunningPace(int userId) throws SQLException {
	        String query = "SELECT AVG(duration / distance) AS avg_pace FROM activities WHERE user_id = ? AND type = 'running'";
	        try (Connection connection = DatabaseUtil.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, userId);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return resultSet.getDouble("avg_pace");
	                }
	            }
	        }
	        return 0;
	    }

	 /**
	  * Calculates the average cycling distance for a given user ID.
	  * Postconditions:
	  * - Returns the average cycling distance as a double value.
	  * - If no cycling activities are found for the given userId, returns 0.
	  */
	    public double getAverageCyclingDistance(int userId) throws SQLException {
	        String query = "SELECT AVG(distance) AS avg_distance FROM activities WHERE user_id = ? AND type = 'cycling'";
	        try (Connection connection = DatabaseUtil.getConnection();
	             PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setInt(1, userId);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return resultSet.getDouble("avg_distance");
	                }
	            }
	        }
	        return 0;
	    }
}
