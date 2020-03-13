package hw5;

import org.joda.time.DateTime;

public class MonitoredData {


	private DateTime startTime;
	private DateTime endTime;
	private String activity;

	public MonitoredData(String startTime, String endTime, String activity) {

		this.startTime = createDateTime(startTime);
		this.endTime = createDateTime(endTime);
		this.activity = activity;
	}

	private DateTime createDateTime(String s) {
		String[] pieces = s.split(" ");
		String[] data = pieces[0].split("-");
		String[] hour = pieces[1].split(":");

		return new DateTime(Integer.parseInt(data[0]),
				Integer.parseInt(data[1]),
				Integer.parseInt(data[2]),
				Integer.parseInt(hour[0]),
				Integer.parseInt(hour[1]),
				Integer.parseInt(hour[2]));
	}

	public String toString() {
		String s = " ";
		s += "Activity " + activity + "  with start_time " + startTime + "  and end_time " + endTime + "\n";

		return s;
	}

	public String getActivity() {
		return activity;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}
	
}
