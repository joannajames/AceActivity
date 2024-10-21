package activityace;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Cycling extends Activity {
	
	public float distance;  // Distance covered during the cycling activity
	public int elevation; // Indicates how uphill the cycling was (1-3)
	

	public Cycling(int duration,LocalDateTime dateTime,float distanceValue, int elevationValue){
		super("Cycling",duration,dateTime);
		this.distance=distanceValue;
		this.elevation=elevationValue;
		
	}
	
	/**
	 * Pre-condition: The duration of the cycling activity and the distance covered
     * must be positive non-zero values. The elevation must be one of 1,2 or 3.
	 * Postcondition:
	 * Returns a String indicating the intensity level of the cycling session based on the calculated intensity.
	 * Possible return values are "Light", "Moderate", or "Vigorous".
	 * Returns null if none of the conditions are met (which is logically not possible with current conditions).
	 */
	
	public String calculateIntensity() {
		float pace=(float)(this.distance/(this.duration/60.0)); // Calculate pace in miles per hour
		float intensity= pace*this.elevation; //Multiplied by elevation
		if (intensity<3.0){
			return "Light";
		}else if (intensity>=3.0 && intensity<=5.0){
			return "Moderate";
		}else if (intensity>5.0){
			return "Vigorous";
		}
		return null;

	}

	/**
	 * Postcondition:
	 * Returns a String representing the cycling session in a readable format, including intensity,
	 * date and time, duration in minutes, distance in miles, and elevation rank.
	 */
	
	public String toString() {
		DateTimeFormatter df= DateTimeFormatter.ofPattern("dd-M-yyyy hh:mm:ss");
		return(this.calculateIntensity()+" cycling session at " + df.format(super.dateTime) + " for " + this.duration + " minutes and " + this.distance + " miles at elevation rank " + this.elevation + ".");
	}


	public float getDistance() {
		return this.distance;
	}
	
	public int getElevation() {
		return this.elevation;
	}
	
	public int getDuration() {
		return super.duration;
	}
	
	public LocalDateTime getDateTime() {
		return super.dateTime;
	}

	
	public String getType() {
		return "Cycling";
	}
	
}
