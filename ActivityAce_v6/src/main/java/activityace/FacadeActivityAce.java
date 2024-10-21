package activityace;
import Utilities.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FacadeActivityAce {
    private static FacadeActivityAce instance = null;  //Singleton instance of the FacadeActivityAce class
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(); //ReentrantReadWriteLock used to manage concurrent access to shared resources.
    private ActivityDAO activityDAO; //Data Access Object (DAO) for handling operations related to activities.
    private String username; //Username for authentication
    private String password; //Password for authentication

    // Private constructor to prevent instantiation
    private FacadeActivityAce(String username, String password) throws SQLException {
        this.username = username;
        this.password = password;
        activityDAO = new ActivityDAO();

        // Validate user existence
        UserDAO userDAO = new UserDAO();
        if (!userDAO.validateUser(username, password)) {
            throw new SQLException("User not found or invalid credentials.");
        }
    }

    // Static method to get the singleton instance
    public static FacadeActivityAce getInstance(String username, String password) throws SQLException {
        lock.writeLock().lock();
        try {
            if (instance == null) {
                instance = new FacadeActivityAce(username, password);
            }
            return instance;
        } finally {
            lock.writeLock().unlock();
        }
    }

    // Generic method to add an activity
    public void addActivity(Activity activity) throws SQLException {
        UserDAO userDAO = new UserDAO();
        int userId = userDAO.getUserId(username);
        activityDAO.addActivity(activity, userId);
    }

    // Method to get activities
    public List<Activity> getActivities() throws SQLException {
        UserDAO userDAO = new UserDAO();
        int userId = userDAO.getUserId(username);
        return activityDAO.getActivities(userId);
    }
    
    // Method to get Average of running paces
    public double getAverageRunningPace() throws SQLException {
        UserDAO userDAO = new UserDAO();
        int userId = userDAO.getUserId(username);
        return activityDAO.getAverageRunningPace(userId);
    }

   // Method to get Average of cycling paces
    public double getAverageCyclingDistance() throws SQLException {
        UserDAO userDAO = new UserDAO();
        int userId = userDAO.getUserId(username);
        return activityDAO.getAverageCyclingDistance(userId);
    }

    // Getters for username and password
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
