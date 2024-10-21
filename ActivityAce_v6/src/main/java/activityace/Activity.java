package activityace;
import java.time.*;
import java.io.Serializable;

public abstract class Activity implements Serializable {
	private static final long serialVersionUID= 1L;
		public int duration; // Duration of the activity in minutes
		public LocalDateTime dateTime; //Date and time when the activity occurred
		public String type; //String: Cycling, Running or Lifting

		public Activity(String type,int duration,LocalDateTime dateTime) {
			this.type=type;
			this.duration=duration;
			this.dateTime=dateTime;
		}
		
		public abstract String calculateIntensity();
		public abstract String toString();
		public abstract LocalDateTime getDateTime();
		public abstract int getDuration();
		public abstract String getType();
}
