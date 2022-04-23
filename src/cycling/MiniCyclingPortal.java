package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * MiniCyclingPortal is a minimally compiling, a full functioning implementor of
 * the MiniCyclingPortalInterface interface.
 * 
 * @author Kechen Liu
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class MiniCyclingPortal implements MiniCyclingPortalInterface {

	@Override
	public int[] getRaceIds() {
		// return an empty array if none exists.
		if (Race.races.size() == 0) {
			int[] arr = {};
			return arr;
		}
		Set<Integer> integerSet = Race.races.keySet();
		int[] raceIds = new int[integerSet.size()];
		int index = 0;
		for (Integer i : integerSet) {
			raceIds[index++] = i;
		}
		assert (raceIds != null);
		return raceIds;
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		// TODO Auto-generated method stub
		if (Race.checkName(name) == true) {
			throw new IllegalNameException("Name already taken.");
		}
		if (name == null) {
			throw new InvalidNameException("Name cannot be null.");
		}
		if (name.isEmpty()) {
			throw new InvalidNameException("Name cannot be empty.");
		}
		if (name.length() > 30) {
			throw new InvalidNameException("Name cannot contain more than 30.");
		}
		if (name.contains(" ")) {
			throw new InvalidNameException("Name cannot contain whitespaces.");
		}
		// race desc can be null
		if (description != null) {
			Race race = new Race(name, description);
			Integer raceid = race.getRaceID();
			Race.races.put(raceid, race);
			assert (race.getRaceID() != 0);
			return race.getRaceID();
		} else {
			Race race = new Race(name);
			Integer raceid = race.getRaceID();
			Race.races.put(raceid, race);
			assert (race.getRaceID() != 0);
			return race.getRaceID();
		}
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer rid = raceId;
		if (Race.races.containsKey(rid) == false) {
			throw new IDNotRecognisedException("Race ID does not exist in system.");
		}
		Race race = Race.races.get(raceId);
		assert (race != null);
		return "race ID: " + race.getRaceID() + " ,race name: " + race.getRaceName() + " ,race description: "
				+ race.getRaceDesc() + " ,the number of stages: " + race.raceStages.size() + " ,race's total length: "
				+ race.showRaceLength();
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer raceid = raceId;
		if (Race.races.containsKey(raceid) == false) {
			throw new IDNotRecognisedException("ID does not exist in system.");
		}
		Race.races.remove(raceid);
		assert (Race.races.get(raceid) == null);
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer rid = raceId;
		if (Race.races.containsKey(rid) == false) {
			throw new IDNotRecognisedException("ID does not exist in system.");
		}
		// get number successfully
		assert (Race.races.get(rid) != null);
		return Race.races.get(rid).raceStages.size();
	}

	// create stage
	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		// TODO Auto-generated method stub
		Integer rid = raceId;
		if (Race.races.containsKey(rid) == false) {
			throw new IDNotRecognisedException("Race ID does not exist in system.");
		}
		if (Stage.checkName(stageName) == true) {
			throw new IllegalNameException("This stage name is already in use.");
		}
		if (stageName == null) {
			throw new InvalidNameException("Name cannot be null.");
		}
		if (stageName.isEmpty()) {
			throw new InvalidNameException("Name cannot be empty.");
		}
		if (stageName.length() > 30) {
			throw new InvalidNameException("Name cannot contain more than 30.");
		}
		if (stageName.contains(" ")) {
			throw new InvalidNameException("Name cannot contain whitespaces.");
		}
		if (length < 5.0) {
			throw new InvalidLengthException("Stage length cannot be less than 5 (kilometres).");
		}
		// create new instance
		Stage stage = new Stage(raceId, stageName, description, length, startTime, type);
		Integer staid = stage.getStageID();
		// add stage to stages
		Stage.stages.put(staid, stage);
		// add stage to raceStages
		Race.races.get(rid).raceStages.put(staid, stage);
		// add stage successfully
		assert (Stage.stages.get(staid) != null);
		assert (Race.races.get(rid).raceStages.get(staid) != null);
		return stage.getStageID();
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer rid = raceId;
		if (Race.races.containsKey(rid) == false) {
			throw new IDNotRecognisedException("ID does not exist in system.");
		}
		Set<Integer> integerSet = Race.races.get(rid).raceStages.keySet();
		int[] stageIds = new int[integerSet.size()];
		int index = 0;
		for (Integer i : integerSet) {
			stageIds[index++] = i;
		}
		// get race stages successfully
		assert (stageIds != null);
		return stageIds;
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer sid = stageId;
		if (Stage.stages.containsKey(sid) == false) {
			throw new IDNotRecognisedException("ID does not exist in system.");
		}
		double length = Stage.stages.get(sid).getStageLength();
		// get stage length successfully
		// when create stage, length is assigned, so length(double) could not be 0.0
		assert (length != 0.0);
		return length;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer staid = stageId;
		if (Stage.stages.containsKey(staid) == false) {
			throw new IDNotRecognisedException("ID does not exist in system.");
		}
		// when create a stage ,the race id is assigned.
		Integer rid = Stage.stages.get(staid).getraceID();
		Stage.stages.remove(staid);
		Race.races.get(rid).raceStages.remove(staid);
		// remove successfully
		assert (Race.races.get(rid).raceStages.get(staid) == null || Stage.stages.get(staid) == null);
	}

	// create segment
	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		// TODO Auto-generated method stub
		Integer staid = stageId;
		if (Stage.stages.containsKey(staid) == false) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		if (location < 0 || location > Stage.stages.get(staid).getStageLength()) {
			throw new InvalidLocationException("Stage length out of bounds.");
		}
		if (Stage.stages.get(staid).getStageState() != null) {
			throw new InvalidStageStateException("stage is waiting for results");
		}
		if (Stage.stages.get(staid).getStageType() == StageType.TT) {
			throw new InvalidStageTypeException("Cannot add segments to a time-trial stage.");
		}
		Segment segment = new Segment(stageId, location, type, averageGradient, length);
		Integer segid = segment.getSegmentId();
		Stage.stages.get(staid).stageSegments.put(segid, segment);
		Segment.segments.put(segid, segment);
		// create segment successfully
		assert (Stage.stages.get(staid).stageSegments != null || Segment.segments.get(segid) != null);
		return segid;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		// TODO Auto-generated method stub
		Integer staid = stageId;
		if (Stage.stages.containsKey(staid) == false) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		if (location < 0 || location > Stage.stages.get(staid).getStageLength()) {
			throw new InvalidLocationException("Stage length out of bounds.");
		}
		if (Stage.stages.get(staid).getStageState() != null) {
			throw new InvalidStageStateException("stage is waiting for results");
		}
		if (Stage.stages.get(staid).getStageType() == StageType.TT) {
			throw new InvalidStageTypeException("Cannot add segments to a time-trial stage.");
		}
		Segment segment = new Segment(stageId, location);
		Integer segid = segment.getSegmentId();
		Stage.stages.get(staid).stageSegments.put(segid, segment);
		Segment.segments.put(segid, segment);
		// create segment successfully
		assert (Stage.stages.get(staid).stageSegments != null || Segment.segments.get(segid) != null);
		return segid;
	}

	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub
		Integer segid = segmentId;
		if (Segment.segments.containsKey(segmentId) == false) {
			throw new IDNotRecognisedException("Segment ID not recognised.");
		}
		Integer staid = Segment.segments.get(segid).getStageId();
		if (Stage.stages.get(staid).getStageState() != null) {
			throw new InvalidStageStateException("Cannot add to stage. Stage already concluded.");
		}
		Stage.stages.get(staid).stageSegments.remove(segid);
		Segment.segments.remove(segid);
		// remove segment successfully
		assert (Stage.stages.get(staid).stageSegments.get(segid) == null || Segment.segments.get(segid) == null);
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub
		Integer staid = stageId;
		if (Stage.stages.containsKey(staid) == false) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}

		if (Stage.stages.get(staid).getStageState() == "waiting for results") {
			throw new InvalidStageStateException("Stage already concluded.");
		}
		Stage.stages.get(staid).setStageState("waiting for results");
		// change stage's state successfully
		assert (Stage.stages.get(staid).getStageState() != null);
	}

	@Override
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer staid = stageId;
		if (Stage.stages.containsKey(staid) == false) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		Set<Integer> integerSet = Stage.stages.get(staid).stageSegments.keySet();
		int[] segmentIds = new int[integerSet.size()];
		int index = 0;
		for (Integer i : integerSet) {
			segmentIds[index++] = i;
		}
		// get stage segments successfully
		assert (segmentIds != null);
		return segmentIds;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// TODO Auto-generated method stub

		if (Team.checkName(name) == true) {
			throw new IllegalNameException("Name already taken.");
		}
		if (name == null) {
			throw new InvalidNameException("Name cannot be null.");
		}
		if (name.isEmpty()) {
			throw new InvalidNameException("Name cannot be empty.");
		}
		if (name.length() > 30) {
			throw new InvalidNameException("Name cannot contain more than 30 characters.");
		}
		Team team = new Team(name, description);
		Integer tid = team.getTeamID();
		Team.teams.put(tid, team);
		// create team successfully
		assert (team != null);
		return team.getTeamID();
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer tid = teamId;
		if (Team.teams.containsKey(tid) == false) {
			throw new IDNotRecognisedException("Team ID not recognised.");
		}
		Team.teams.remove(tid);
		// remove team successfully
		assert (Team.teams.get(tid) == null);
	}

	@Override
	public int[] getTeams() {
		// TODO Auto-generated method stub
		Set<Integer> integerSet = Team.teams.keySet();
		int[] teamIds = new int[integerSet.size()];
		int index = 0;
		for (Integer i : integerSet) {
			teamIds[index++] = i;
		}
		// get teams successfully
		assert (teamIds != null);
		return teamIds;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer tid = teamId;
		if (Team.teams.containsKey(tid) == false) {
			throw new IDNotRecognisedException("Team ID not recognised.");
		}
		Set<Integer> integerSet = Team.teams.get(tid).teamRiders.keySet();
		int[] riderIds = new int[integerSet.size()];
		int index = 0;
		for (Integer i : integerSet) {
			riderIds[index++] = i;
		}
		// get teams riders successfully
		assert (riderIds != null);
		return riderIds;
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		// TODO Auto-generated method stub
		Integer tid = teamID;
		if (Team.teams.containsKey(tid) == false) {
			throw new IDNotRecognisedException("Team ID not recognised.");
		}
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null.");
		}

		if (yearOfBirth < 1900) {
			throw new IllegalArgumentException("Year of birth cannot be less than 1900.");
		}
		Rider rider = new Rider(teamID, name, yearOfBirth);
		Integer rid = rider.getRiderId();
		// put riders into the team he/she belongs to
		Team.teams.get(tid).teamRiders.put(rid, rider);
		// put riders into riders
		Rider.riders.put(rid, rider);
		// create rider successfully
		assert (Rider.riders.get(rid) != null || Team.teams.get(tid).teamRiders.get(rid) != null || rider != null);
		return rider.getRiderId();
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer rid = riderId;
		if (Rider.riders.containsKey(rid) == false) {
			throw new IDNotRecognisedException("ID not recognised.");
		}
		// if rider has registered in a stage/many satges
		// rider1 registered in stage1 and stage2
		if (Rider.riders.get(rid).getStageIds().size() != 0) {
			for (Integer stageid : Rider.riders.get(rid).getStageIds()) {
				// HashMap<Integer, LocalTime>
				Stage.stages.get(stageid).stageRidersTotalElapsedTime.remove(rid);
				// debug
				// System.out.println("debug:" +
				// Stage.stages.get(stageid).stageRidersTotalElapsedTime.get(rid));
				// HashMap<Integer, Rider>
				Stage.stages.get(stageid).stageRiders.remove(rid);
				// System.out.println("debug:" +
				// Stage.stages.get(stageid).stageRiders.get(rid));
			}
		}
		// remove rider from Rider.riders
		int teamid = Rider.riders.get(rid).getTeamId();
		Integer tid = teamid;
		Rider.riders.remove(rid);
		// remove rider from Team.teamRiders
		Team.teams.get(tid).teamRiders.remove(rid);
		// remove rider successfully
		assert (Rider.riders.get(rid) == null || Team.teams.get(tid).teamRiders.get(rid) == null);
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {
		// TODO Auto-generated method stub
		Integer rid = riderId;
		Integer staid = stageId;
		// get the rider
		Rider rider = Rider.riders.get(rid);
		// get the stage
		Stage stage = Stage.stages.get(staid);
		// put the rider into Stage.stageRiders
		stage.stageRiders.put(rid, rider);
		Rider riderInStage = stage.stageRiders.get(rid);
		if (riderInStage.getRegisteredInStage()) {
			throw new DuplicatedResultException(
					"Cannot have more than one record of a rider's result for a single stage.");
		}

		if (Stage.stages.containsKey(staid) == false) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		if (Rider.riders.containsKey(rid) == false) {
			throw new IDNotRecognisedException("Rider ID not recognised.");
		}
		if (Rider.guranteeCheckpoints(stageId, checkpoints)) {
			throw new InvalidCheckpointsException("The length of checkpoints is not correct");
		}
		if (stage.getStageState() == null) {
			throw new InvalidStageStateException(
					"Results can only be added to a stage while it is waiting for results.");
		}
		// register rider's result in that stage
		riderInStage.setCheckPoints(checkpoints);
		// add stageid to rider's stageIds
		Rider.riders.get(rid).getStageIds().add(staid); // modifider ok
		// configure
		Rider.generateRecord(stageId, riderId);
		// set up flag
		riderInStage.setRegisteredInStage(true);
		rider.setRegisteredInStage(false);
		// rider register result successfully in the stage
		assert (riderInStage.getCheckPoints() != null);
	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer rid = riderId;
		Integer staid = stageId;
		Rider riderInStage = Stage.stages.get(staid).stageRiders.get(rid);
		if (Stage.stages.containsKey(staid) == false) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		if (Rider.riders.containsKey(rid) == false) {
			throw new IDNotRecognisedException("Rider ID not recognised.");
		}
		if (Rider.riders.get(rid).getStageIds().contains(staid) == false) {
			// create empty arr
			LocalTime[] t = {};
			return t;
		} else {
			LocalTime[] checkpoints = riderInStage.getCheckPoints();
			// create an new array to store the result
			LocalTime[] times = new LocalTime[checkpoints.length + 1];
			for (int i = 0; i < checkpoints.length; i++) {
				times[i] = checkpoints[i];
			}
			LocalTime totalElapsedTime = riderInStage.getTotalElapsedTime();
			times[checkpoints.length] = totalElapsedTime;
			// successfully get the result
			assert (times.length != 0);
			return times;
		}
	}

	// debug
	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer rid = riderId;
		Integer staid = stageId;
		if (Stage.stages.containsKey(staid) == false) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		if (Rider.riders.containsKey(rid) == false) {
			throw new IDNotRecognisedException("Rider ID not recognised.");
		}
		Rider riderInStage = Stage.stages.get(staid).stageRiders.get(rid);
		if (Rider.riders.get(rid).getStageIds().contains(staid) == false) {
			return null;
		} else if (Stage.stages.get(staid).getStageType() == StageType.TT) {
			// There is no adjustments on elapsed time on time-trials.
			return null;
		} else {
			LocalTime adjustedElapsedTime = riderInStage.getAdjustedElapsedTime();
			return adjustedElapsedTime;
		}
	}

	// bug
	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		// Removes the stage results from the rider.
		Integer rid = riderId;
		Integer staid = stageId;
		if (Stage.stages.containsKey(stageId) == false) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		if (Rider.riders.containsKey(riderId) == false) {
			throw new IDNotRecognisedException("Rider ID not recognised.");
		}
		Rider riderInStage = Stage.stages.get(staid).stageRiders.get(rid);
		Stage.stages.get(staid).stageRidersTotalElapsedTime.remove(rid);
		riderInStage.setRegisteredInStage(false);
		riderInStage.setCheckPoints(null);
	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer staid = stageId;
		if (Stage.stages.containsKey(staid) == false) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		// An empty list if there is no result for the stage.
		// which means no rider registered in the stage
		if (Stage.stages.get(staid).stageRiders == null) {
			int[] arr = {};
			return arr;
		}
		HashMap<Rider, LocalTime> sortedET = Stage.sortRiderByEclapsedTime(stageId);
		ArrayList<Integer> listRids = new ArrayList<Integer>();
		// iterate over and get keys and values
		for (Map.Entry<Rider, LocalTime> entry : sortedET.entrySet()) {
			Integer riderIdw = entry.getKey().getRiderId();
			listRids.add(riderIdw);
		}
		// convert arraylist to array
		int[] ids = new int[listRids.size()];
		for (int i = 0; i < listRids.size(); i++) {
			int e = listRids.get(i);
			ids[i] = e;
		}
		// get rank successfully
		assert (ids.length != 0);
		return ids;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer staid = stageId;
		if (Stage.stages.containsKey(staid) == false) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		// An empty list if there is no result for the stage.
		// which means no rider registered in the stage
		if (Stage.stages.get(staid).stageRiders == null) {
			LocalTime[] arr = {};
			return arr;
		}
		HashMap<Rider, LocalTime> sortedAT = Stage.sortRiderByFinishTime(stageId);
		ArrayList<LocalTime> listAdEcTime = new ArrayList<LocalTime>();
		// iterate over and get keys and values
		for (Map.Entry<Rider, LocalTime> entry : sortedAT.entrySet()) {
			LocalTime riderAtime = entry.getKey().getAdjustedElapsedTime();
			listAdEcTime.add(riderAtime);
		}
		// convert arraylist to array
		LocalTime[] times = new LocalTime[listAdEcTime.size()];
		// Iterating using for loop
		for (int i = 0; i < listAdEcTime.size(); i++) {
			LocalTime e = listAdEcTime.get(i);
			times[i] = e;
		}
		assert (times.length != 0);
		return times;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer staid = stageId;
		if (Stage.stages.containsKey(staid) == false) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		// An empty list if there is no result for the stage.
		// which means no rider registered in the stage
		if (Stage.stages.get(staid).stageRiders == null) {
			int[] arr = {};
			return arr;
		}
		// assign the field of riderPoint to all the riders in the stage
		Stage.assignRiderPointsInStage(stageId);
		// obtain a hashmap of sortedET
		HashMap<Rider, LocalTime> sortedET = Stage.sortRiderByEclapsedTime(stageId);
		// get riderpoints int[] from sortedFT
		ArrayList<Integer> listRpoints = new ArrayList<Integer>();
		// iterate over and get keys and values
		for (Map.Entry<Rider, LocalTime> entry : sortedET.entrySet()) {
			Integer riderPoint = entry.getKey().getRiderPoints();
			listRpoints.add(riderPoint);
		}
		// convert arraylist to array
		int[] points = new int[listRpoints.size()];
		// Iterating using for loop
		for (int i = 0; i < listRpoints.size(); i++) {

			int e = listRpoints.get(i);
			points[i] = e;
		}
		assert (points.length != 0);
		return points;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		Integer staid = stageId;
		if (Stage.stages.containsKey(staid) == false) {
			throw new IDNotRecognisedException("Stage ID not recognised.");
		}
		// An empty list if there is no result for the stage.
		// which means no rider registered in the stage
		if (Stage.stages.get(staid).stageRiders == null) {
			int[] arr = {};
			return arr;
		}
		// assign the field of riderPoint to all the riders in the stage
		Stage.assignRiderMountainPointsInStage(stageId);
		// obtain a hashmap of sortedET
		HashMap<Rider, LocalTime> sortedFT = Stage.sortRiderByFinishTime(stageId);
		// get riderpoints int[] from sortedFT
		ArrayList<Integer> listRMpoints = new ArrayList<Integer>();
		// iterate over and get keys and values
		for (Map.Entry<Rider, LocalTime> entry : sortedFT.entrySet()) {
			Integer riderMPoint = entry.getKey().getRiderPoints();
			listRMpoints.add(riderMPoint);
		}
		// convert arraylist to array
		int[] mpoints = new int[listRMpoints.size()];
		// Iterating using for loop
		for (int i = 0; i < listRMpoints.size(); i++) {

			int e = listRMpoints.get(i);
			mpoints[i] = e;
		}
		assert (mpoints.length != 0);
		return mpoints;
	}

	@Override
	public void eraseCyclingPortal() {
		// TODO Auto-generated method stub
		// resets all internal counters
		// Race
		Race.raceCounter = 1;
		Race.races = new HashMap<>();
		// Stage
		Stage.stageCounter = 1;
		Stage.stages = new HashMap<>();
		// Segment
		Segment.segments = new HashMap<>();
		// Team
		Team.teamCounter = 1;
		Team.teams = new HashMap<>();
		// Rider
		Rider.riderIDCounter = 1;
		Rider.riders = new HashMap<>();
		// empties this MiniCyclingPortalInterface of its contents
		CyclingPortals.portal = null;
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(CyclingPortals.portal);
		} catch (java.io.IOException ex) {
			System.out.println(ex.getMessage());
			throw new IOException("there is a problem experienced when trying to save the store contents to the file");
		}
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		MiniCyclingPortal portal = null;
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
			Object obj = in.readObject();
			if (obj instanceof MiniCyclingPortal) {
				portal = (MiniCyclingPortal) obj;
			}
		} catch (IOException e1) {
			System.out.println(e1.getMessage());
			throw new IOException(
					"There is a problem experienced when trying to load the store contents from the file");
		} catch (ClassNotFoundException e2) {
			throw new ClassNotFoundException("required class files cannot be found when loading");
		}
	}

}
