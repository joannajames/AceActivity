package activityace;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Running extends Activity {
	
	public float distance;  // Distance covered during the running activity
	public boolean terrain; // Indicates whether the running activity was indoors or outdoors
	

	
	public Running(int duration,LocalDateTime dateTime,float distanceValue, String location){
		super("Running",duration,dateTime);
		if (location.equalsIgnoreCase("I")) {
			terrain=true;
		}else if (location.equalsIgnoreCase("O")) {
			terrain=false;
		}
		this.distance=distanceValue;
		
	}
	
	/**
     * Calculates the intensity of the running activity based on pace.
     * 
     * Pre-condition: The duration of the running activity and the distance covered
     * must be positive non-zero values.
     * 
     * Post-condition: Returns a string representing the intensity of the running activity 
     * based on the calculated pace.
     * 
     * @return A string representing the intensity of the running activity.
     */
	
	public String calculateIntensity() {
		float pace=this.getPace();
		if (pace<3.0){
			return "Light";
		}else if (pace>=3.0 && pace<=5.0){
			return "Moderate";
		}else if (pace>5.0){
			return "Vigorous";
		}
		return null;
	}
	
	/**
     * Returns a string representation of the running activity.
     * 
     * Post-condition: Returns a string containing information about the running activity, 
     * including intensity, date/time, duration, distance, and location (indoors or outdoors).
     * 
     */
	
	public String toString() {
		DateTimeFormatter df= DateTimeFormatter.ofPattern("dd-M-yyyy hh:mm:ss");
		if (terrain==true) {
			return(this.calculateIntensity()+" indoor running session at " + df.format(super.dateTime) + " for " + this.duration + " minutes and " + this.distance + " miles.");
		}else {
			return(this.calculateIntensity()+" outdoor running session at " + df.format(super.dateTime) + " for " + this.duration + " minutes and " + this.distance + " miles.");
		}
	}
	
	public float getDistance() {
		return this.distance;
	}
	
	public boolean getTerrain() {
		return this.terrain;
	}
	
	public int getDuration() {
		return super.duration;
	}
	
	public LocalDateTime getDateTime() {
		return super.dateTime;
	}
	
	public float getPace() {
		float pace=(float)(this.distance/(this.duration/60.0)); // Calculate pace in miles per hour
		return pace;
	}
	
	public String getType() {
		return "Running";
	}
	
}
