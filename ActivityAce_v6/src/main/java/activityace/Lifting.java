package activityace;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;



public class Lifting extends Activity {
	
	public String name; // Name of the lifting exercise
	public int sets; // Number of sets for the lifting exercise
	public int reps; // Number of reps for each set of the lifting exercise
	public int weight; // Weight lifted for each rep of the lifting exercise
	
	
	public Lifting(int duration,LocalDateTime dateTime,String exerciseName, int setsNum, int repsNum, int weightNum) {
		super("Lifting",duration,dateTime);
		this.name=exerciseName;
		this.sets=setsNum;
		this.reps=repsNum;
		this.weight=weightNum;
	}
	
	/**
     * Calculates the intensity of the lifting activity based on sets and reps.
     * 
     * Pre-condition: The number of sets and reps must be positive non-zero values.
     * 
     * Post-condition: Returns a string representing the intensity of the lifting activity 
     * based on the criteria of sets and reps.
     * 
     */
	
	public String calculateIntensity(){
		if (this.sets>=3&&this.reps>10){
			return "High";
		}else if (this.sets>=3&&this.reps<10) {
			return "Moderate";
		}else {
			return "Light";
		}
	}
	
	 /*
     * Post-condition: Returns a string containing information about the lifting activity, 
     * including intensity, name of the exercise, number of sets, number of reps, and weight lifted.
     * 
     * @return A string representation of the lifting activity.
     */
	public String toString() {
		DateTimeFormatter df= DateTimeFormatter.ofPattern("dd-M-yyyy hh:mm:ss");
		return(this.calculateIntensity()+ " intensity " + this.name + " session at " + df.format(super.dateTime) + " of " + this.sets + " sets of " + this.reps + " reps at "+ this.weight+" lbs.");
	}
	
	public String getExerciseName() {
		return this.name;
	}
	
	public int getSets() {
		return this.sets;
	}
	
	public int getReps() {
		return this.reps;
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public int getDuration() {
		return super.duration;
	}
	
	public LocalDateTime getDateTime() {
		return super.dateTime;
	}
	
	public String getType() {
		return "Lifting";
	}
}
