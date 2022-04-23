package cycling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Stage {
	/**
	 * All the stages in the system.
	 *
	 * 
	 * @author Kechen Liu
	 * @version 1.0
	 */
	public static int stageCounter = 1;
	private int stageID;
	private String stageName;
	private String stageDesc;
	private double stageLength;
	private StageType type;
	private LocalDateTime startTime;
	private String stageState;
	private int raceID;
	// store all the stages in the system
	public static HashMap<Integer, Stage> stages = new HashMap<Integer, Stage>();
	// store all the segment in one stage
	public HashMap<Integer, Segment> stageSegments = new HashMap<Integer, Segment>();
	// store all the riders in one stage
	public HashMap<Integer, Rider> stageRiders = new HashMap<Integer, Rider>();
	// store all the riders's totalElapsedTime in one stage
	// <riderid,totalElapsedTime>
	public HashMap<Integer, LocalTime> stageRidersTotalElapsedTime = new HashMap<Integer, LocalTime>();

	// constructor
	Stage(int raceId, String stageName, String stageDesc, double stageLength, LocalDateTime startTime, StageType type) {
		this.setRaceID(raceId);
		this.setStageName(stageName);
		this.setStageDesc(stageDesc);
		this.setStageID(stageCounter);
		this.setStageLength(stageLength);
		this.setStageType(type);
		this.setStartTime(startTime);
		this.setStageState(null);
		stageCounter++;
	}

	// getters and setters
	public int getraceID() {
		return this.raceID;
	}

	public void setRaceID(int raceID) {
		this.raceID = raceID;
	}

	public int getStageID() {
		return this.stageID;
	}

	public void setStageID(int stageID) {
		this.stageID = stageID;
	}

	public String getStageName() {
		return this.stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getStageDesc() {
		return this.stageDesc;
	}

	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}

	public double getStageLength() {
		return this.stageLength;
	}

	public void setStageLength(double stageLength) {
		this.stageLength = stageLength;
	}

	public StageType getStageType() {
		return this.type;
	}

	public void setStageType(StageType type) {
		this.type = type;
	}

	public LocalDateTime getStartTime() {
		return this.startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public String getStageState() {
		return this.stageState;
	}

	public void setStageState(String stageState) {
		this.stageState = stageState;
	}

	// method: check satge name already exits
	public static boolean checkName(String newname) {
		HashSet<String> names = new HashSet<String>();
		for (Integer i : stages.keySet()) {
			names.add(stages.get(i).getStageName());
		}
		if (names.contains(newname) == true) {
			return true;
		} else {
			return false;
		}
	}

	// sort Stage.satges.get(staid).stageRiders by EclapsedTime
	// server for getRidersRankInStage getRidersPointsInStage
	public static HashMap<Rider, LocalTime> sortRiderByEclapsedTime(int stageId) {
		// 1. create current Stage's hashmap
		HashMap<Rider, LocalTime> stageRiderEclapedTime = new HashMap<>();
		// 2.put value
		// obtain all riders belong to the stage
		Integer staId = stageId;
		// Stage.stages.get(staId).stageRiders
		// traverse
		for (Map.Entry<Integer, Rider> mapElement : Stage.stages.get(staId).stageRiders.entrySet()) {
			stageRiderEclapedTime.put(mapElement.getValue(), mapElement.getValue().getTotalElapsedTime());
		}
		// Create a list from elements of HashMap
		List<Map.Entry<Rider, LocalTime>> list = new LinkedList<Map.Entry<Rider, LocalTime>>(
				stageRiderEclapedTime.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<Rider, LocalTime>>() {
			public int compare(Map.Entry<Rider, LocalTime> o1, Map.Entry<Rider, LocalTime> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<Rider, LocalTime> sorted = new LinkedHashMap<Rider, LocalTime>();
		for (Map.Entry<Rider, LocalTime> aa : list) {
			sorted.put(aa.getKey(), aa.getValue());
		}
		return sorted;
	}

	// sort Stage.satges.get(staid).stageRiders by FinishTime
	// server for getRankedAdjustedElapsedTimesInStage
	// getRidersMountainPointsInStage
	public static HashMap<Rider, LocalTime> sortRiderByFinishTime(int stageId) {
		HashMap<Rider, LocalTime> stageRiderFinishTime = new HashMap<>();
		Integer staId = stageId;
		// Stage.stages.get(staId).stageRiders
		// traverse
		// finishtime = checkpoints[checkpoints.length - 1]
		for (Map.Entry<Integer, Rider> mapElement : Stage.stages.get(staId).stageRiders.entrySet()) {
			LocalTime[] checkpoints = mapElement.getValue().getCheckPoints();
			LocalTime finishTime = checkpoints[checkpoints.length - 1];
			stageRiderFinishTime.put(mapElement.getValue(), finishTime);
		}
		List<Map.Entry<Rider, LocalTime>> list = new LinkedList<Map.Entry<Rider, LocalTime>>(
				stageRiderFinishTime.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<Rider, LocalTime>>() {
			public int compare(Map.Entry<Rider, LocalTime> o1, Map.Entry<Rider, LocalTime> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});
		// put data from sorted list to hashmap
		HashMap<Rider, LocalTime> sorted = new LinkedHashMap<Rider, LocalTime>();
		for (Map.Entry<Rider, LocalTime> aa : list) {
			sorted.put(aa.getKey(), aa.getValue());
		}
		return sorted;
	}

	// assign stageRider's points to all riders in a stage
	// serve for getRidersPointsInStage
	// call sortRiderByEclapsedTime
	public static void assignRiderPointsInStage(int stageId) {
		// get sorted hashmap<Rider,LocalTime> --------> EclapsedTime
		HashMap<Rider, LocalTime> sortedET = Stage.sortRiderByEclapsedTime(stageId);
		// determine stage's type
		Integer staid = stageId;
		assert (Stage.stages.get(staid).getStageType() == StageType.FLAT
				|| Stage.stages.get(staid).getStageType() == StageType.TT);
		// calculate the number of riders in a stage
		int numOfRider = Stage.stages.get(staid).stageRiders.size();
		assert (numOfRider > 0);
		if (Stage.stages.get(staid).getStageType() == StageType.FLAT) {
			// traverse hashmap
			int n = 1;
			for (Map.Entry<Rider, LocalTime> mapElement : sortedET.entrySet()) {
				int riderid = mapElement.getKey().getRiderId();
				Integer rid = riderid;
				Rider riderInStage = Stage.stages.get(staid).stageRiders.get(rid);
				switch (n) {
					case 1:
						riderInStage.setRiderPoints(50);
						break;
					case 2:
						riderInStage.setRiderPoints(30);
						break;
					case 3:
						riderInStage.setRiderPoints(20);
						break;
					case 4:
						riderInStage.setRiderPoints(18);
						break;
					case 5:
						riderInStage.setRiderPoints(16);
						break;
					case 6:
						riderInStage.setRiderPoints(14);
						break;
					case 7:
						riderInStage.setRiderPoints(12);
						break;
					case 8:
						riderInStage.setRiderPoints(10);
						break;
					case 9:
						riderInStage.setRiderPoints(8);
						break;
					case 10:
						riderInStage.setRiderPoints(7);
						break;
					case 11:
						riderInStage.setRiderPoints(6);
						break;
					case 12:
						riderInStage.setRiderPoints(5);
						break;
					case 13:
						riderInStage.setRiderPoints(4);
						break;
					case 14:
						riderInStage.setRiderPoints(3);
						break;
					case 15:
						riderInStage.setRiderPoints(2);
						break;
				}
				n++;
			}
		}
		if (Stage.stages.get(staid).getStageType() == StageType.TT
				|| Stage.stages.get(staid).getStageType() == StageType.HIGH_MOUNTAIN) {
			int n = 1;
			for (Map.Entry<Rider, LocalTime> mapElement : sortedET.entrySet()) {
				int riderid = mapElement.getKey().getRiderId();
				Integer rid = riderid;
				Rider riderInStage = Stage.stages.get(staid).stageRiders.get(rid);
				switch (n) {
					case 1:
						riderInStage.setRiderPoints(20);
						break;
					case 2:
						riderInStage.setRiderPoints(17);
						break;
					case 3:
						riderInStage.setRiderPoints(15);
						break;
					case 4:
						riderInStage.setRiderPoints(13);
						break;
					case 5:
						riderInStage.setRiderPoints(11);
						break;
					case 6:
						riderInStage.setRiderPoints(10);
						break;
					case 7:
						riderInStage.setRiderPoints(9);
						break;
					case 8:
						riderInStage.setRiderPoints(8);
						break;
					case 9:
						riderInStage.setRiderPoints(7);
						break;
					case 10:
						riderInStage.setRiderPoints(6);
						break;
					case 11:
						riderInStage.setRiderPoints(5);
						break;
					case 12:
						riderInStage.setRiderPoints(4);
						break;
					case 13:
						riderInStage.setRiderPoints(3);
						break;
					case 14:
						riderInStage.setRiderPoints(2);
						break;
					case 15:
						riderInStage.setRiderPoints(1);
						break;
				}
				n++;
			}
		}

		if (Stage.stages.get(staid).getStageType() == StageType.MEDIUM_MOUNTAIN) {
			// traverse hashtable
			int n = 1;
			for (Map.Entry<Rider, LocalTime> mapElement : sortedET.entrySet()) {
				int riderid = mapElement.getKey().getRiderId();
				Integer rid = riderid;
				Rider riderInStage = Stage.stages.get(staid).stageRiders.get(rid);
				switch (n) {
					case 1:
						riderInStage.setRiderPoints(30);
						break;
					case 2:
						riderInStage.setRiderPoints(25);
						break;
					case 3:
						riderInStage.setRiderPoints(22);
						break;
					case 4:
						riderInStage.setRiderPoints(19);
						break;
					case 5:
						riderInStage.setRiderPoints(17);
						break;
					case 6:
						riderInStage.setRiderPoints(15);
						break;
					case 7:
						riderInStage.setRiderPoints(13);
						break;
					case 8:
						riderInStage.setRiderPoints(11);
						break;
					case 9:
						riderInStage.setRiderPoints(9);
						break;
					case 10:
						riderInStage.setRiderPoints(7);
						break;
					case 11:
						riderInStage.setRiderPoints(6);
						break;
					case 12:
						riderInStage.setRiderPoints(5);
						break;
					case 13:
						riderInStage.setRiderPoints(4);
						break;
					case 14:
						riderInStage.setRiderPoints(3);
						break;
					case 15:
						riderInStage.setRiderPoints(2);
						break;
				}
				n++;
			}
		}
	}

	// assign stageRider's mountainpoints to all riders in a stage
	// serve for getRidersMountainPointsInStage
	// call sortRiderByTotalTime
	public static void assignRiderMountainPointsInStage(int stageId) {
		// get sorted hashmap<Rider,LocalTime> --------> EclapsedTime
		HashMap<Rider, LocalTime> sortedFT = Stage.sortRiderByFinishTime(stageId);
		// determine stage's type
		Integer staid = stageId;
		assert (Stage.stages.get(staid).getStageType() == StageType.HIGH_MOUNTAIN
				|| Stage.stages.get(staid).getStageType() == StageType.MEDIUM_MOUNTAIN);
		// calculate the number of riders in a stage
		int numOfRider = Stage.stages.get(staid).stageRiders.size();
		assert (numOfRider > 0);
		if (Stage.stages.get(staid).getStageType() == StageType.MEDIUM_MOUNTAIN) {
			// traverse hashtable
			int n = 1;
			for (Map.Entry<Rider, LocalTime> mapElement : sortedFT.entrySet()) {
				int riderid = mapElement.getKey().getRiderId();
				Integer rid = riderid;
				Rider riderInStage = Stage.stages.get(staid).stageRiders.get(rid);
				switch (n) {
					case 1:
						riderInStage.setRiderMountainPoint(30);
						break;
					case 2:
						riderInStage.setRiderMountainPoint(25);
						break;
					case 3:
						riderInStage.setRiderMountainPoint(22);
						break;
					case 4:
						riderInStage.setRiderMountainPoint(19);
						break;
					case 5:
						riderInStage.setRiderMountainPoint(17);
						break;
					case 6:
						riderInStage.setRiderMountainPoint(15);
						break;
					case 7:
						riderInStage.setRiderMountainPoint(13);
						break;
					case 8:
						riderInStage.setRiderMountainPoint(11);
						break;
					case 9:
						riderInStage.setRiderMountainPoint(9);
						break;
					case 10:
						riderInStage.setRiderMountainPoint(7);
						break;
					case 11:
						riderInStage.setRiderMountainPoint(6);
						break;
					case 12:
						riderInStage.setRiderMountainPoint(5);
						break;
					case 13:
						riderInStage.setRiderMountainPoint(4);
						break;
					case 14:
						riderInStage.setRiderMountainPoint(3);
						break;
					case 15:
						riderInStage.setRiderMountainPoint(2);
						break;
				}
				n++;
			}
		}
		if (Stage.stages.get(staid).getStageType() == StageType.HIGH_MOUNTAIN) {
			int n = 1;
			for (Map.Entry<Rider, LocalTime> mapElement : sortedFT.entrySet()) {
				int riderid = mapElement.getKey().getRiderId();
				Integer rid = riderid;
				Rider riderInStage = Stage.stages.get(staid).stageRiders.get(rid);
				switch (n) {
					case 1:
						riderInStage.setRiderMountainPoint(20);
						break;
					case 2:
						riderInStage.setRiderMountainPoint(17);
						break;
					case 3:
						riderInStage.setRiderMountainPoint(15);
						break;
					case 4:
						riderInStage.setRiderMountainPoint(13);
						break;
					case 5:
						riderInStage.setRiderMountainPoint(11);
						break;
					case 6:
						riderInStage.setRiderMountainPoint(10);
						break;
					case 7:
						riderInStage.setRiderMountainPoint(9);
						break;
					case 8:
						riderInStage.setRiderMountainPoint(8);
						break;
					case 9:
						riderInStage.setRiderMountainPoint(7);
						break;
					case 10:
						riderInStage.setRiderMountainPoint(6);
						break;
					case 11:
						riderInStage.setRiderMountainPoint(5);
						break;
					case 12:
						riderInStage.setRiderMountainPoint(4);
						break;
					case 13:
						riderInStage.setRiderMountainPoint(3);
						break;
					case 14:
						riderInStage.setRiderMountainPoint(2);
						break;
					case 15:
						riderInStage.setRiderMountainPoint(1);
						break;
				}
				n++;
			}
		}
	}

}
