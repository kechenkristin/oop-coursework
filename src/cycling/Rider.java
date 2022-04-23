package cycling;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rider {
	/**
	 * All the rider in the system.
	 *
	 * 
	 * @author Kechen Liu
	 * @version 1.0
	 */
	private String name;
	private int yearOfBirth;
	public static int riderIDCounter = 1;
	private int riderID;
	private int teamId;
	// these fields specifically used for riders in stage
	private ArrayList<Integer> stageIds = new ArrayList<Integer>();
	private LocalTime[] checkpoints;
	private LocalTime totalElapsedTime;
	private boolean RegisteredInStage = false;
	private LocalTime adjustedElapsedTime;
	private int riderPoints;
	private int riderMountainPoints;
	// store all the riders in the system
	public static HashMap<Integer, Rider> riders = new HashMap<Integer, Rider>();

	// constructor
	public Rider(int teamId, String name, int yearOfBirth) {
		this.teamId = teamId;
		this.setRiderName(name);
		this.setYearOfBirth(yearOfBirth);
		this.riderID = riderIDCounter;
		riderIDCounter++;
	}

	// method: getter& setter
	public String getRiderName() {
		return this.name;
	}

	public void setRiderName(String name) {
		this.name = name;
	}

	public int getYearOfBirth() {
		return this.yearOfBirth;
	}

	public void setYearOfBirth(int yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public int getRiderId() {
		return this.riderID;
	}

	public void setRiderId(String name) {
		this.name = name;
	}

	public int getTeamId() {
		return this.teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public ArrayList<Integer> getStageIds() {
		return this.stageIds;
	}

	public void setStageId(ArrayList<Integer> stageIds) {
		this.stageIds = stageIds;
	}

	public LocalTime[] getCheckPoints() {
		return this.checkpoints;
	}

	public void setCheckPoints(LocalTime[] checkpoints) {
		this.checkpoints = checkpoints;
	}

	public LocalTime getAdjustedElapsedTime() {
		return this.adjustedElapsedTime;
	}

	public void setAdjustedElapsedTime(LocalTime adjustedElapsedTime) {
		this.adjustedElapsedTime = adjustedElapsedTime;
	}

	public void setTotalElapsedTime(LocalTime totalElapsedTime) {
		this.totalElapsedTime = totalElapsedTime;
	}

	public LocalTime getTotalElapsedTime() {
		return this.totalElapsedTime;
	}

	public void setRegisteredInStage(boolean RegisteredInStage) {
		this.RegisteredInStage = RegisteredInStage;
	}

	// RegisteredTotalET
	public boolean getRegisteredInStage() {
		return this.RegisteredInStage;
	}

	public int getRiderPoints() {
		return this.riderPoints;
	}

	public void setRiderPoints(int riderPoints) {
		this.riderPoints = riderPoints;
	}

	public int getRiderMountainPoints() {
		return this.riderMountainPoints;
	}

	public void setRiderMountainPoint(int riderMountainPoints) {
		this.riderMountainPoints = riderMountainPoints;
	}

	// method: guarantee checkpoints's length correct
	// serve for portal: registerRiderResultsInStage's InvalidCheckpointsException
	public static boolean guranteeCheckpoints(int stageId, LocalTime... checkpoints) {
		// get the stage
		Integer staId = stageId;
		Stage stage = Stage.stages.get(staId);
		// get stage's segment number
		int n = stage.stageSegments.size();
		if (checkpoints.length != (n + 2)) {
			return true;
		} else {
			return false;
		}
	}

	// method: calculate rider's totalElapseTime
	public static LocalTime calculateTotalElapsedTime(int riderID, int stageID) {
		Integer rid = riderID;
		Integer staid = stageID;
		// 1. obtain rider's checkpoints
		Rider riderInStage = Stage.stages.get(staid).stageRiders.get(rid);
		LocalTime[] checkpoints = riderInStage.getCheckPoints();
		// obtain Duration(calculate the difference)
		Duration totalETd = Duration.between(checkpoints[0], checkpoints[checkpoints.length - 1]);
		// 2.convert durantion to localtim
		long longd = totalETd.toMillis();
		LocalTime totalElapsedTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(longd), ZoneId.systemDefault())
				.toLocalTime();
		return totalElapsedTime;
	}

	// method: assign TotalElapsedTime to rider when registered in a specific stage
	public static void confiTotalElapsedTime(Integer staid, Integer rid, LocalTime totalElapsedTime) {
		Rider riderInStage = Stage.stages.get(staid).stageRiders.get(rid);
		// assign value to rider's totalElapsedTime
		riderInStage.setTotalElapsedTime(totalElapsedTime);
		// add rider's totalElapsedTime to stageRiderTotalElapsedTime
		Stage.stages.get(staid).stageRidersTotalElapsedTime.put(rid, totalElapsedTime);
	}

	// method: calculate rider's AdjustedElapsedTime
	public static LocalTime calculateAdjustedElapsedTime(int riderID, int stageID) {
		// 1. obtain all riders' finish time, find the smallest
		// 1.1 obtain all the riders in the stage
		Integer rid = riderID;
		Integer staid = stageID;
		// There is no adjustments on elapsed time on time-trials.
		if (Stage.stages.get(staid).getStageType() == StageType.TT) {
			return null;
		}
		HashMap<Integer, Rider> allRidersInStage = Stage.stages.get(staid).stageRiders;
		// 1.2 find the smallest finishtime
		// 1.2.1 create a list to store all the finishtime
		List<LocalTime> ftList = new ArrayList<>();
		// 1.2.2 traverse the hashmap

		for (Map.Entry<Integer, Rider> mapElement : allRidersInStage.entrySet()) {
			LocalTime[] checkpoints = mapElement.getValue().getCheckPoints();
			LocalTime finishTime = checkpoints[checkpoints.length - 1];
			// System.out.print(finishTime + " ");
			ftList.add(LocalTime.parse(finishTime.toString()));
		}
		// 1.2.3 sortftList
		Collections.sort(ftList);
		// 1.2.4 find the smallest finishTime
		LocalTime smallFt = ftList.get(0);
		// 2.calculate rider's aet
		// 2.1 get the rider
		Rider riderInStage = Stage.stages.get(staid).stageRiders.get(rid);
		// 2.2 calculate the difference
		LocalTime startTime = riderInStage.getCheckPoints()[0];
		// System.out.println("debug");
		// System.out.println("startTime" + startTime); ok
		Duration aetd = Duration.between(startTime, smallFt);
		// 2.convert durantion to localtim
		long longd = aetd.toMillis();
		LocalTime adjustedElapsedTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(longd), ZoneId.systemDefault())
				.toLocalTime();
		return adjustedElapsedTime;
	}

	// // method: assign AdjustedElapsedTime to rider when registered in a specific
	// stage
	public static void confiAdjustedElapsedTime(Integer staid, Integer rid, LocalTime adjustedElapsedTime) {
		Rider riderInStage = Stage.stages.get(staid).stageRiders.get(rid);
		// assign value to rider's adjustedElapsedTime
		riderInStage.setAdjustedElapsedTime(adjustedElapsedTime);
	}

	// these method call the four methods up it
	// assign attributes to rider in the specific stage
	// serve for registerRiderResultsInStage
	public static void generateRecord(int staid, int rid) {
		Integer riderid = rid;
		Integer sid = staid;
		LocalTime totalElapsedTime = Rider.calculateTotalElapsedTime(riderid, sid);
		Rider.confiTotalElapsedTime(sid, riderid, totalElapsedTime);
		LocalTime adjustedElapsedTime = Rider.calculateAdjustedElapsedTime(riderid, sid);
		Rider.confiAdjustedElapsedTime(sid, riderid, adjustedElapsedTime);
	}

}
