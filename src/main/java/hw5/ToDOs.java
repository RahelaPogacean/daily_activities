package hw5;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ToDOs {

	static List<MonitoredData> monitory = new ArrayList<MonitoredData>();

	public static MonitoredData getMDFromString(String result) throws IOException {

		MonitoredData md = null;
		if (result.contains("\t\t")) {
			String[] pieces = result.split("\t\t");
			String start = pieces[0];
			String end = pieces[1];
			String act = pieces[2];
			md = new MonitoredData(start, end, act);
		} else
			System.out.println("Cannot split!\n");

		return md;
	}

	public static void streamToList() throws IOException {

		File file = new File("Activity.txt");
		Stream<String> linesStream = Files.lines(file.toPath());

		linesStream.forEach(a -> {
			try {
				monitory.add(getMDFromString(a));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public static long noOfMonitoredDays() {

		long count = monitory.stream().map(a -> a.getStartTime().getDayOfMonth()).distinct().count();
		
		return count;
	}

	public static Map<String, Long> appearActivities() {

		Map<String, Long> actt = monitory.stream()
				.collect(Collectors.groupingBy(m -> m.getActivity(), Collectors.counting()));

		return actt;
	}

	public static Map<String, Long> appearActivitiesPerDay(int myDay) {

		Map<String, Long> actt = monitory.stream().filter(a -> a.getStartTime().getDayOfMonth() == myDay)
				.collect(Collectors.groupingBy(m -> m.getActivity(), Collectors.counting()));

		return actt;
	}

	public static List<Duration> recordDuration() {

		List<Duration> dd = new ArrayList<>();

		monitory.forEach((MonitoredData mm) -> {
			DateTime begin = new DateTime(mm.getStartTime().getYear(), mm.getStartTime().getMonthOfYear(),
					mm.getStartTime().getDayOfMonth(), mm.getStartTime().getHourOfDay(),
					mm.getStartTime().getMinuteOfHour(), mm.getStartTime().getSecondOfMinute());

			DateTime finish = new DateTime(mm.getEndTime().getYear(), mm.getEndTime().getMonthOfYear(),
					mm.getEndTime().getDayOfMonth(), mm.getEndTime().getHourOfDay(), mm.getEndTime().getMinuteOfHour(),
					mm.getEndTime().getSecondOfMinute());

			Duration d = new Duration(begin, finish);
			dd.add(d);

		});

		return dd;
	}

	
	public static Map<String, Long> activityDuration() {

		Map<String, Long> dur = monitory.stream().collect(Collectors.groupingBy(m -> m.getActivity(), Collectors
				.summingLong(a -> Math.abs(a.getEndTime().getMinuteOfDay() - a.getStartTime().getMinuteOfDay()))));
		

		return dur;
	}



	public static void main(String[] args) throws IOException {

		FileWriter fileWriter = new FileWriter("results.txt");
		PrintWriter printWriter = new PrintWriter(fileWriter);

		printWriter.println("TASK 1: \n");
		printWriter.println("");
		streamToList();
		monitory.forEach(
				a -> printWriter.println(a.getStartTime() + "\t\t" + a.getEndTime() + "\t\t" + a.getActivity()));

		printWriter.println("");
		printWriter.println("TASK 2: \n");
		printWriter.println("");
		printWriter.println("Number of monitored days is  " + noOfMonitoredDays() + ".\n ");

		printWriter.println("");
		printWriter.println("TASK 3: \n");
		printWriter.println("");

		Map<String, Long> actt = appearActivities();
		for (Map.Entry<String, Long> entry : actt.entrySet()) {
			printWriter.println(entry.getKey() + "  appears  " + entry.getValue() + "  times.");
		}
	
		printWriter.println("");
		printWriter.println("TASK 4:");
		printWriter.println("");
		monitory.stream().map(a -> a.getStartTime().getDayOfMonth()).distinct().forEach(a -> {
			printWriter.println("Day " + a + ": " + appearActivitiesPerDay(a));
		});

		printWriter.println("");
		printWriter.println("TASK 5:");
		printWriter.println("");
		List<Duration> l = recordDuration();
		l.forEach(a -> {
			printWriter.println("minutes " + a.getStandardMinutes());
		});

		printWriter.println("");
		printWriter.println("TASK 6: \n");
		printWriter.println("");
		activityDuration().forEach((key, value) -> printWriter.println(key + "  no of minutes: " + value + "\n"));
		printWriter.close();
		
	
		System.out.println("TASK 1: \n");
		streamToList();
		monitory.forEach(
				a -> System.out.println(a.getStartTime() + "\t\t" + a.getEndTime() + "\t\t" + a.getActivity()));

		System.out.println("TASK 2: \n");
		System.out.println("Number of monitored days is  " + noOfMonitoredDays() + ".\n ");

		System.out.println("TASK 3: \n");

		for (Map.Entry<String, Long> entry : actt.entrySet()) {
			System.out.println(entry.getKey() + "  appears  " + entry.getValue() + "  times.");
		}
	
		System.out.println("TASK 4:");
		monitory.stream().map(a -> a.getStartTime().getDayOfMonth()).distinct().forEach(a -> {
			System.out.println("Day " + a + ": " + appearActivitiesPerDay(a));
		});

		printWriter.println("TASK 5:");

		l.forEach(a -> {
			System.out.println("minutes " + a.getStandardMinutes());
		});

		System.out.println("TASK 6: \n");
		activityDuration().forEach((key, value) -> System.out.println(key + "  no of minutes: " + value + "\n"));

	}
}
