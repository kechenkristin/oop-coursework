import cycling.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortalInterface interface -- note you
 * will want to increase these checks, and run it on your CyclingPortal class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Kechen Liu
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		System.out.println("The system compiled and started the execution...");
		System.out.println("test template");
		System.out.println("*******************************");
		System.out.println("method name");
		System.out.println("--self--");
		System.out.println("--configure--");
		System.out.println("--exception--");

		MiniCyclingPortalInterface portal = new MiniCyclingPortal();
		// CyclingPortalInterface portal = new BadCyclingPortal();

		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

		try {
			// createRace ok
			System.out.println("****************************");
			System.out.println("create race");
			CyclingPortals.portal.createRace("race1", "test1");
			CyclingPortals.portal.createRace("race2", "test2");
			// Race's description (can be null) ok
			CyclingPortals.portal.createRace("r3", null);
			System.out.println(Race.races.get(1).getRaceID());
			// ex: name already exist ok
			// CyclingPortals.portal.createRace("r1", "test2");

			// getRaceIds ok
			System.out.println("****************************");
			System.out.println("get race id");
			System.out.println(CyclingPortals.portal.getRaceIds()); // reference
			for (int i = 0; i < CyclingPortals.portal.getRaceIds().length; i++) {
				System.out.print(CyclingPortals.portal.getRaceIds()[i] + " ");
			}
			System.out.println();

			// viewRaceDetails ok
			System.out.println("****************************");
			System.out.println("view race details");
			System.out.println(CyclingPortals.portal.viewRaceDetails(1));
			System.out.println(CyclingPortals.portal.viewRaceDetails(3));
			// ex:If the ID does not match to any race in the system.OK

			// System.out.println(CyclingPortals.portal.viewRaceDetails(4));

			// removeRaceById ok
			System.out.println("****************************");
			System.out.println("remove race by id");
			CyclingPortals.portal.removeRaceById(2);
			// ex id no m ok
			// CyclingPortals.portal.removeRaceById(4);

			// verify
			System.out.println(CyclingPortals.portal.getRaceIds()); // reference
			for (int i = 0; i < CyclingPortals.portal.getRaceIds().length; i++) {
				System.out.print(CyclingPortals.portal.getRaceIds()[i] + " ");
			}
			System.out.println();
			// getNumberOfStages ok
			System.out.println("****************************");
			System.out.println("get number of stages");
			System.out.println("getNumberOfStages");
			System.out.println(CyclingPortals.portal.getNumberOfStages(1));

			// addStageToRace ok
			System.out.println("****************************");
			System.out.println("add stage to race");
			// bug
			System.out.println("add first stage: " + CyclingPortals.portal.addStageToRace(1, "stage1",
					"test1 for stage", 14.0, LocalDateTime.now(), StageType.FLAT));
			System.out.println("add second stage" + CyclingPortals.portal.addStageToRace(1, "stage2", "test2 for stage",
					14.0, LocalDateTime.now(), StageType.HIGH_MOUNTAIN));
			System.out.println("add third stage" + CyclingPortals.portal.addStageToRace(1, "stage3", "test3 for stage",
					14.0, LocalDateTime.now(), StageType.MEDIUM_MOUNTAIN));
			System.out.println("add fifth stage" + CyclingPortals.portal.addStageToRace(1, "stage4", "test4 for stage",
					14.0, LocalDateTime.now(), StageType.TT));
			// verify
			System.out.println("number of stages in race1");
			System.out.println(CyclingPortals.portal.getNumberOfStages(1));
			System.out.println(Race.races.get(1).raceStages);

			// getRaceStages ok
			System.out.println("*******************************");
			System.out.println("get race stages");
			System.out.println(CyclingPortals.portal.getRaceStages(1)); // reference
			for (int i = 0; i < CyclingPortals.portal.getRaceStages(1).length; i++) {
				System.out.print(CyclingPortals.portal.getRaceStages(1)[i] + " ");
			}
			System.out.println();
			// getStageLength ok
			System.out.println("**************************");
			System.out.println("getStageLength");
			System.out.println(CyclingPortals.portal.getStageLength(1));

			// removeStageById ok
			System.out.println("*************************");
			System.out.println("removeStageById");
			// CyclingPortals.portal.removeStageById(2);
			// verify
			System.out.println(CyclingPortals.portal.getRaceStages(1)); // reference
			for (int i = 0; i < CyclingPortals.portal.getRaceStages(1).length; i++) {
				System.out.print(CyclingPortals.portal.getRaceStages(1)[i] + " ");
			}
			System.out.println();

			// addCategorizedClimbToStage ok
			System.out.println("*************************");
			System.out.println("addCategorizedClimbToStage");
			System.out.println(CyclingPortals.portal.addCategorizedClimbToStage(1, 2.0, SegmentType.C1, 22.3, 2.0));
			System.out.println(CyclingPortals.portal.addCategorizedClimbToStage(1, 4.0, SegmentType.C2, 44.5, 2.0));
			System.out.println(CyclingPortals.portal.addCategorizedClimbToStage(1, 6.0, SegmentType.C3, 44.5, 2.0));
			System.out.println(CyclingPortals.portal.addCategorizedClimbToStage(1, 8.0, SegmentType.HC, 44.5, 2.0));
			System.out
					.println(CyclingPortals.portal.addCategorizedClimbToStage(1, 10.0, SegmentType.SPRINT, 44.5, 2.0));

			System.out.println("debug:" + Stage.stages.get(1).stageSegments);

			// server for advanced test
			System.out.println("addCategorizedClimbToStage");
			System.out.println(CyclingPortals.portal.addCategorizedClimbToStage(2, 2.0, SegmentType.C1, 22.3, 2.0));
			System.out.println(CyclingPortals.portal.addCategorizedClimbToStage(2, 4.0, SegmentType.C2, 44.5, 2.0));
			System.out.println(CyclingPortals.portal.addCategorizedClimbToStage(2, 6.0, SegmentType.C3, 44.5, 2.0));
			System.out.println(CyclingPortals.portal.addCategorizedClimbToStage(2, 8.0, SegmentType.HC, 44.5, 2.0));
			System.out
					.println(CyclingPortals.portal.addCategorizedClimbToStage(2, 10.0, SegmentType.SPRINT, 44.5, 2.0));
			System.out.println("debug:" + Stage.stages.get(2).stageSegments);

			System.out.println("addCategorizedClimbToStage");
			System.out.println(CyclingPortals.portal.addCategorizedClimbToStage(3, 2.0, SegmentType.C1, 22.3, 2.0));
			System.out.println(CyclingPortals.portal.addCategorizedClimbToStage(3, 4.0, SegmentType.C2, 44.5, 2.0));
			System.out.println(CyclingPortals.portal.addCategorizedClimbToStage(3, 6.0, SegmentType.C3, 44.5, 2.0));
			System.out.println(CyclingPortals.portal.addCategorizedClimbToStage(3, 8.0, SegmentType.HC, 44.5, 2.0));
			System.out
					.println(CyclingPortals.portal.addCategorizedClimbToStage(3, 10.0, SegmentType.SPRINT, 44.5, 2.0));

			// stage4TT coudnot add seg

			// addIntermediateSprintToStage ok
			System.out.println("******************************");
			System.out.println("addIntermediateSprintToStage");
			// System.out.println(CyclingPortals.portal.addIntermediateSprintToStage(3,
			// 3.0));
			// System.out.println(CyclingPortals.portal.addIntermediateSprintToStage(3,
			// 3.0));

			// removeSegment ok
			System.out.println("*******************************");
			System.out.println("removeSegment");
			// CyclingPortals.portal.removeSegment(6);
			// verify

			// getStageSegments ok
			System.out.println("*************************");
			System.out.println("getStageSegments & verify remove Segment");
			System.out.println(CyclingPortals.portal.getStageSegments(1)); // reference
			System.out.println(CyclingPortals.portal.getStageSegments(1).length);
			for (int i = 0; i < CyclingPortals.portal.getStageSegments(1).length; i++) {
				System.out.println(CyclingPortals.portal.getStageSegments(1)[i] + " ");
			}
			System.out.println();

			// concludeStagePreparation ok
			System.out.println("******************************************");
			System.out.println("concludeStagePreparation");
			CyclingPortals.portal.concludeStagePreparation(1);
			// verify
			System.out.println(Race.races.get(1).raceStages.get(1).getStageState());
			System.out.println(Race.races.get(1).raceStages.get(3).getStageState());

			// createTeam ok
			System.out.println("******************************************");
			System.out.println("creteTeam");
			System.out.println(CyclingPortals.portal.createTeam("t1", "team1 for test"));
			System.out.println(CyclingPortals.portal.createTeam("t2", "team2 for test"));
			System.out.println(CyclingPortals.portal.createTeam("t3", "team3 for test"));
			System.out.println(CyclingPortals.portal.createTeam("t4", "team4 for test"));
			// System.out.println(CyclingPortals.portal.createTeam("t1", "extest"));

			// getTeams ok
			System.out.println("******************************************");
			System.out.println("getTeams");
			System.out.println(CyclingPortals.portal.getTeams());
			for (int i = 0; i < CyclingPortals.portal.getTeams().length; i++) {
				System.out.println(CyclingPortals.portal.getTeams()[i]);
			}

			// removeTeam ok
			System.out.println("*******************************************");
			System.out.println("removeTeam");
			CyclingPortals.portal.removeTeam(2);
			for (int i = 0; i < CyclingPortals.portal.getTeams().length; i++) {
				System.out.println(CyclingPortals.portal.getTeams()[i]);
			}

			// createRider ok
			System.out.println("*******************************************");
			System.out.println("createRider");
			System.out.println("--test self--");
			System.out.println(CyclingPortals.portal.createRider(1, "rider1", 2001));
			System.out.println(CyclingPortals.portal.createRider(1, "rider2", 2002));
			System.out.println(CyclingPortals.portal.createRider(1, "rider3", 2003));
			System.out.println(CyclingPortals.portal.createRider(1, "rider4", 2004));
			System.out.println(CyclingPortals.portal.createRider(1, "rider5", 2005));
			System.out.println(CyclingPortals.portal.createRider(1, "rider6", 2006));
			System.out.println(CyclingPortals.portal.createRider(1, "rider7", 2007));
			System.out.println(CyclingPortals.portal.createRider(1, "rider8", 2008));
			System.out.println(CyclingPortals.portal.createRider(1, "rider9", 2009));
			System.out.println(CyclingPortals.portal.createRider(1, "rider10", 2010));
			System.out.println("--configure--");
			System.out.println(Rider.riders.get(1));
			System.out.println(Team.teams.get(1).teamRiders.get(2));
			System.out.println("--exception--");
			System.out.println();

			// getTeamRiders ok
			System.out.println("******************************************");
			System.out.println("getTeamRiders");
			System.out.println(CyclingPortals.portal.getTeamRiders(1)); // reference
			for (int i = 0; i < CyclingPortals.portal.getTeamRiders(1).length; i++) {
				System.out.println(CyclingPortals.portal.getTeamRiders(1)[i]);
			}

			// removeRider ok
			// bug
			System.out.println("******************************************");
			System.out.println("removerider");
			CyclingPortals.portal.removeRider(9);
			// v1 verify ok
			for (int i = 0; i < CyclingPortals.portal.getTeamRiders(1).length; i++) {
				System.out.println(CyclingPortals.portal.getTeamRiders(1)[i] + " ");
			}
			System.out.println();
			System.out.println("*******************************");
			System.out.println("registerRiderResultsInStage");
			System.out.println("--self--"); // ok
			// 1h
			LocalTime[] r1checkpoints = { LocalTime.of(0, 00, 00, 00000), LocalTime.of(01, 00, 00, 00000),
					LocalTime.of(02, 00, 00, 00000), LocalTime.of(03, 00, 00, 00000), LocalTime.of(04, 00, 00, 00000),
					LocalTime.of(05, 00, 00, 00000), LocalTime.of(06, 00, 00, 00000) };
			// 1min
			LocalTime[] r2checkpoints = { LocalTime.of(00, 00, 00, 00000), LocalTime.of(00, 01, 00, 00000),
					LocalTime.of(00, 02, 00, 00000), LocalTime.of(00, 03, 00, 00000), LocalTime.of(00, 04, 00, 00000),
					LocalTime.of(00, 05, 00, 00000), LocalTime.of(00, 06, 00, 00000) };
			// 1h start
			LocalTime[] r3checkpoints = { LocalTime.of(01, 00, 00, 00000), LocalTime.of(02, 00, 00, 00000),
					LocalTime.of(03, 00, 00, 00000), LocalTime.of(04, 00, 00, 00000), LocalTime.of(05, 00, 00, 00000),
					LocalTime.of(06, 00, 00, 00000), LocalTime.of(07, 00, 00, 00000) };
			// 30min
			LocalTime[] r4checkpoints = { LocalTime.of(00, 00, 00, 00000), LocalTime.of(00, 30, 00, 00000),
					LocalTime.of(01, 00, 00, 00000), LocalTime.of(01, 30, 00, 00000), LocalTime.of(02, 00, 00, 00000),
					LocalTime.of(02, 30, 00, 00000), LocalTime.of(03, 00, 00, 00000) };
			// 30 min start
			LocalTime[] r5checkpoints = { LocalTime.of(00, 30, 00, 00000), LocalTime.of(01, 00, 00, 00000),
					LocalTime.of(01, 30, 00, 00000), LocalTime.of(02, 00, 00, 00000), LocalTime.of(02, 30, 00, 00000),
					LocalTime.of(03, 00, 00, 00000), LocalTime.of(03, 30, 00, 00000) };
			// 10 min
			LocalTime[] r6checkpoints = { LocalTime.of(00, 00, 00, 00000), LocalTime.of(00, 10, 00, 00000),
					LocalTime.of(00, 20, 00, 00000), LocalTime.of(00, 30, 00, 00000), LocalTime.of(00, 40, 00, 00000),
					LocalTime.of(00, 50, 00, 00000), LocalTime.of(01, 00, 00, 00000) };
			// 20 min
			LocalTime[] r7checkpoints = { LocalTime.of(00, 00, 00, 00000), LocalTime.of(00, 20, 00, 00000),
					LocalTime.of(00, 40, 00, 00000), LocalTime.of(01, 00, 00, 00000), LocalTime.of(01, 20, 00, 00000),
					LocalTime.of(01, 40, 00, 00000), LocalTime.of(02, 00, 00, 00000) };
			// 2h
			LocalTime[] r8checkpoints = { LocalTime.of(00, 00, 00, 00000), LocalTime.of(02, 00, 00, 00000),
					LocalTime.of(04, 00, 00, 00000), LocalTime.of(06, 00, 00, 00000), LocalTime.of(8, 00, 00000),
					LocalTime.of(10, 40, 00, 00000), LocalTime.of(12, 00, 00, 00000) };

			CyclingPortals.portal.registerRiderResultsInStage(1, 1, r1checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(1, 2, r2checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(1, 3, r3checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(1, 4, r4checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(1, 5, r5checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(1, 6, r6checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(1, 7, r7checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(1, 8, r8checkpoints);
			// debug
			System.out.println("debug:" + Stage.stages.get(1).stageRiders.get(1).getRegisteredInStage()); // true
			System.out.println("ok");
			System.out.println("--configure--"); // ok
			// ris
			// checkpoints
			System.out.println("checkpoints" + Stage.stages.get(1).stageRiders.get(1).getCheckPoints());
			// ttet
			System.out.println("TotalElapsedTime" + Stage.stages.get(1).stageRiders.get(1).getTotalElapsedTime());
			// adet
			System.out.println("AdjustedElapsedTime" + Stage.stages.get(1).stageRiders.get(1).getAdjustedElapsedTime());
			System.out.println("--exception--");
			// DuplicatedResultException ok
			// CyclingPortals.portal.registerRiderResultsInStage(1, 1, r1checkpoints);
			// InvalidCheckpointsException ok
			// InvalidStageStateException ok
			// CyclingPortals.portal.registerRiderResultsInStage(2, 1, r1checkpoints);
			// CyclingPortals.portal.registerRiderResultsInStage(2, 2, r2checkpoints);

			// getRiderResultsInStage ok
			System.out.println("*******************************");
			System.out.println("getRiderResultsInStage");
			System.out.println("--self--");
			System.out.println(CyclingPortals.portal.getRiderResultsInStage(1, 1)); // reference
			for (int i = 0; i < CyclingPortals.portal.getRiderResultsInStage(1, 1).length; i++) {
				System.out.print(CyclingPortals.portal.getRiderResultsInStage(1, 1)[i] + " ");
			}
			System.out.println();
			// Return an empty array if there is no result registered for the rider in the
			// stage. ok
			System.out.println(CyclingPortals.portal.getRiderResultsInStage(1, 10));
			for (int i = 0; i < CyclingPortals.portal.getRiderResultsInStage(1, 10).length; i++) {
				System.out.print(CyclingPortals.portal.getRiderResultsInStage(1, 10)[i] + " ");
			}
			System.out.println();
			System.out.println(CyclingPortals.portal.getRiderResultsInStage(1, 10));
			System.out.println("--configure--");
			System.out.println("--exception--");

			// getRiderAdjustedElapsedTimeInStage ok
			System.out.println("*******************************");
			System.out.println("getRiderAdjustedElapsedTimeInStage");
			System.out.println("--self--");
			// There is no adjustments on elapsed time on time-trials.
			// change stage1's type as TT
			// Return an empty array if there is no result registered for the rider in the
			// stage. ok
			System.out.println(CyclingPortals.portal.getRiderAdjustedElapsedTimeInStage(1, 10));
			System.out.println(CyclingPortals.portal.getRiderAdjustedElapsedTimeInStage(1, 1));
			System.out.println("--configure--");
			System.out.println("--exception--");

			System.out.println("=======================================================================");
			System.out.println("config for advanced testing");
			System.out.println("TotalElapsedTime");
			System.out.print("r1: " + Stage.stages.get(1).stageRiders.get(1).getTotalElapsedTime() + " ");
			System.out.print("r2: " + Stage.stages.get(1).stageRiders.get(2).getTotalElapsedTime() + " ");
			System.out.print("r3: " + Stage.stages.get(1).stageRiders.get(3).getTotalElapsedTime() + " ");
			System.out.print("r4: " + Stage.stages.get(1).stageRiders.get(4).getTotalElapsedTime() + " ");
			System.out.print("r5: " + Stage.stages.get(1).stageRiders.get(5).getTotalElapsedTime() + " ");
			System.out.print("r6: " + Stage.stages.get(1).stageRiders.get(6).getTotalElapsedTime() + " ");
			System.out.print("r7: " + Stage.stages.get(1).stageRiders.get(7).getTotalElapsedTime() + " ");
			System.out.print("r8: " + Stage.stages.get(1).stageRiders.get(8).getTotalElapsedTime() + " ");
			System.out.println();

			System.out.println("AdjustedElapsedTime");
			System.out.print("r1: " + CyclingPortals.portal.getRiderAdjustedElapsedTimeInStage(1, 1) + " ");
			System.out.print("r2: " + CyclingPortals.portal.getRiderAdjustedElapsedTimeInStage(1, 2) + " ");
			System.out.print("r3: " + CyclingPortals.portal.getRiderAdjustedElapsedTimeInStage(1, 3) + " ");
			System.out.print("r4: " + CyclingPortals.portal.getRiderAdjustedElapsedTimeInStage(1, 4) + " ");
			System.out.print("r5: " + CyclingPortals.portal.getRiderAdjustedElapsedTimeInStage(1, 5) + " ");
			System.out.print("r6: " + CyclingPortals.portal.getRiderAdjustedElapsedTimeInStage(1, 6) + " ");
			System.out.print("r7: " + CyclingPortals.portal.getRiderAdjustedElapsedTimeInStage(1, 7) + " ");
			System.out.print("r8: " + CyclingPortals.portal.getRiderAdjustedElapsedTimeInStage(1, 8) + " ");
			System.out.println();
			System.out.println("Start Time");
			System.out.print("r1: " + Stage.stages.get(1).stageRiders.get(1).getCheckPoints()[0] + " ");
			System.out.print("r2: " + Stage.stages.get(1).stageRiders.get(2).getCheckPoints()[0] + " ");
			System.out.print("r3: " + Stage.stages.get(1).stageRiders.get(3).getCheckPoints()[0] + " ");
			System.out.print("r4: " + Stage.stages.get(1).stageRiders.get(4).getCheckPoints()[0] + " ");
			System.out.print("r5: " + Stage.stages.get(1).stageRiders.get(5).getCheckPoints()[0] + " ");
			System.out.print("r6: " + Stage.stages.get(1).stageRiders.get(6).getCheckPoints()[0] + " ");
			System.out.print("r7: " + Stage.stages.get(1).stageRiders.get(7).getCheckPoints()[0] + " ");
			System.out.print("r8: " + Stage.stages.get(1).stageRiders.get(8).getCheckPoints()[0] + " ");
			System.out.println();
			System.out.println("Finish Time");
			System.out.print("r1: " + Stage.stages.get(1).stageRiders.get(1).getCheckPoints()[6] + " ");
			System.out.print("r2: " + Stage.stages.get(1).stageRiders.get(2).getCheckPoints()[6] + " ");
			System.out.print("r3: " + Stage.stages.get(1).stageRiders.get(3).getCheckPoints()[6] + " ");
			System.out.print("r4: " + Stage.stages.get(1).stageRiders.get(4).getCheckPoints()[6] + " ");
			System.out.print("r5: " + Stage.stages.get(1).stageRiders.get(5).getCheckPoints()[6] + " ");
			System.out.print("r6: " + Stage.stages.get(1).stageRiders.get(6).getCheckPoints()[6] + " ");
			System.out.print("r7: " + Stage.stages.get(1).stageRiders.get(7).getCheckPoints()[6] + " ");
			System.out.print("r8: " + Stage.stages.get(1).stageRiders.get(8).getCheckPoints()[6] + " ");
			System.out.println();

			// getRidersRankInStage
			System.out.println("*******************************");
			System.out.println("getRidersRankInStage");
			System.out.println("--self--");
			// normal
			System.out.println("BEFORE REMOVE RIDER6");
			System.out.println(CyclingPortals.portal.getRidersRankInStage(1)); // reference
			System.out.print("riders rank in stage:(sorted by their elapsed time): ");
			for (int i = 0; i < CyclingPortals.portal.getRidersRankInStage(1).length; i++) {
				System.out.print(CyclingPortals.portal.getRidersRankInStage(1)[i] + " ");
			}
			System.out.println();
			// An empty list if there is no result for the stage. ok
			System.out.println(CyclingPortals.portal.getRidersRankInStage(3).length);
			System.out.println("--configure--");
			System.out.println("--exception--");
			// IDNotRecognisedException ok
			// System.out.println(CyclingPortals.portal.getRidersRankInStage(2).length);

			// getRankedAdjustedElapsedTimesInStage ok

			System.out.println("*******************************");
			System.out.println("getRankedAdjustedElapsedTimesInStage");
			System.out.println("--self--");
			System.out.print("The ranked list of adjusted elapsed times sorted by their finish time: ");
			System.out.println(CyclingPortals.portal.getRankedAdjustedElapsedTimesInStage(1)); // reference
			for (int i = 0; i < CyclingPortals.portal.getRankedAdjustedElapsedTimesInStage(1).length; i++) {
				System.out.print(CyclingPortals.portal.getRankedAdjustedElapsedTimesInStage(1)[i] + " ");
			}
			System.out.println();
			// An empty list if there is no result for the stage. ok
			System.out.println(CyclingPortals.portal.getRankedAdjustedElapsedTimesInStage(3).length);
			System.out.println("AFTER REMOVE RIDRR6");
			// CyclingPortals.portal.removeRider(6);
			System.out.print("riders rank in stage:(sorted by their elapsed time): ");
			for (int i = 0; i < CyclingPortals.portal.getRidersRankInStage(1).length; i++) {
				System.out.print(CyclingPortals.portal.getRidersRankInStage(1)[i] + " ");
			}
			System.out.println();
			System.out.print("The ranked list of adjusted elapsed times sorted by their finish time: ");
			System.out.println(CyclingPortals.portal.getRankedAdjustedElapsedTimesInStage(1)); // reference
			for (int i = 0; i < CyclingPortals.portal.getRankedAdjustedElapsedTimesInStage(1).length; i++) {
				System.out.print(CyclingPortals.portal.getRankedAdjustedElapsedTimesInStage(1)[i] + " ");
			}
			System.out.println();

			System.out.println("--configure--");
			System.out.println("--exception--");

			// getRidersPointsInStage
			// sorted by their elapsed time
			System.out.println("*******************************");
			System.out.println("getRidersPointsInStage");
			System.out.println("--self--");
			System.out.println("stage type == flat");
			System.out.println("normal");
			System.out.println(
					"The ranked list of points each riders received in the stage, sorted by their elapsed time. ");
			System.out.println(CyclingPortals.portal.getRidersPointsInStage(1)); // reference
			for (int i = 0; i < CyclingPortals.portal.getRidersPointsInStage(1).length; i++) {
				System.out.print(CyclingPortals.portal.getRidersPointsInStage(1)[i] + " ");
			}
			System.out.println();
			// test match ok
			System.out.println(Stage.stages.get(1).stageRiders.get(6).getRiderPoints()); // rider points 30
			// riders rank in stage:(sorted by their elapsed time) 2 6 7 5 4 3 1 8
			System.out.println("stage type == tt");
			// Time-trial stages cannot contain any segment.
			// There is no adjustments on elapsed time on time-trials
			// test
			CyclingPortals.portal.concludeStagePreparation(2);
			CyclingPortals.portal.registerRiderResultsInStage(2, 1, r1checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(2, 2, r2checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(2, 3, r3checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(2, 4, r4checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(2, 5, r5checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(2, 6, r6checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(2, 7, r7checkpoints);
			CyclingPortals.portal.registerRiderResultsInStage(2, 8, r8checkpoints);

			System.out.println("--configure--");
			System.out.println("--exception--");

			// getRidersMountainPointsInStage
			// not sure
			System.out.println("*******************************");
			System.out.println("getRidersMountainPointsInStage");
			System.out.println("--self--");
			System.out.println("debug: " + Stage.stages.get(2).getStageType());
			System.out.println("Stage2's points should all be 0");
			System.out.println(CyclingPortals.portal.getRidersPointsInStage(2)); // reference
			for (int i = 0; i < CyclingPortals.portal.getRidersPointsInStage(2).length; i++) {
				System.out.print(CyclingPortals.portal.getRidersPointsInStage(2)[i] + " ");
			}
			System.out.println();
			System.out.println();
			System.out.println(CyclingPortals.portal.getRidersMountainPointsInStage(2)); // reference
			for (int i = 0; i < CyclingPortals.portal.getRidersMountainPointsInStage(2).length; i++) {
				System.out.print(CyclingPortals.portal.getRidersMountainPointsInStage(2)[i] + " ");
			}
			System.out.println();
			System.out.println("--configure--");
			System.out.println("--exception--");

			// remove rider
			System.out.println("*******************************");
			System.out.println("remove rider");
			System.out.println("--self--");
			// CyclingPortals.portal.removeRider(1);
			System.out.println("--configure--");
			// verify
			// Rider.riders
			System.out.println(Rider.riders);
			System.out.println(Rider.riders.get(1));
			// team riders
			System.out.println(CyclingPortals.portal.getTeamRiders(1).length);
			for (int i = 0; i < CyclingPortals.portal.getTeamRiders(1).length; i++) {
				System.out.println(CyclingPortals.portal.getTeamRiders(1)[i]);
			}
			// ris == null
			System.out.println(Stage.stages.get(1).stageRiders.get(1));
			System.out.println(Stage.stages.get(1).stageRidersTotalElapsedTime);
			System.out.println("--exception--");

			// deleteRiderResultsInStage
			System.out.println("*******************************");
			System.out.println("deleteRiderResultsInStage");
			System.out.println("--self--");
			CyclingPortals.portal.deleteRiderResultsInStage(1, 2);
			System.out.println("--configure--");

			Rider ris = Stage.stages.get(1).stageRiders.get(2);
			System.out.println(ris == null);

			System.out.println(ris.getCheckPoints());
			System.out.println(Stage.stages.get(1).stageRidersTotalElapsedTime);
			System.out.println("--exception--");

			System.out.println("====================debug==============================");
			System.out.println("before remove rider1");
			System.out.println("stage1's stageRider");
			HashMap<Integer, Rider> staR = Stage.stages.get(1).stageRiders;
			for (Map.Entry<Integer, Rider> mapElement : staR.entrySet()) {
				Integer rid = mapElement.getKey();
				Rider rider = mapElement.getValue();
				System.out.print("rid " + rid + " : " + rider + " ");
			}
			System.out.println();
			System.out.println("stage1's stageTET");
			HashMap<Integer, LocalTime> staTET = Stage.stages.get(1).stageRidersTotalElapsedTime;
			for (Map.Entry<Integer, LocalTime> mapElement : staTET.entrySet()) {
				Integer rid = mapElement.getKey();
				LocalTime rTET = mapElement.getValue();
				System.out.print("rid " + rid + " : " + rTET + " ");
			}
			System.out.println();

			CyclingPortals.portal.removeRider(1);
			System.out.println();
			System.out.println("after remove rider1");
			System.out.println("stage1's stageRider");
			HashMap<Integer, Rider> staR1 = Stage.stages.get(1).stageRiders;
			for (Map.Entry<Integer, Rider> mapElement : staR1.entrySet()) {
				Integer rid = mapElement.getKey();
				Rider rider = mapElement.getValue();
				System.out.print("rid " + rid + " : " + rider + " ");
			}
			System.out.println();
			System.out.println("stage1's stageTET");
			HashMap<Integer, LocalTime> staTET1 = Stage.stages.get(1).stageRidersTotalElapsedTime;
			for (Map.Entry<Integer, LocalTime> mapElement : staTET1.entrySet()) {
				Integer rid = mapElement.getKey();
				LocalTime rTET = mapElement.getValue();
				System.out.print("rid " + rid + " : " + rTET + " ");
			}
			System.out.println();
			System.out.println("====================debug==============================");
			// System.out.println(CyclingPortals.portal.getRidersPointsInStage(1)); //
			// reference

			for (int i = 0; i < CyclingPortals.portal.getRidersPointsInStage(1).length; i++) {
				System.out.print(CyclingPortals.portal.getRidersPointsInStage(1)[i] + " ");
			}
			System.out.println();

			// eraseCyclingPortal ok
			System.out.println("*******************************");
			System.out.println("eraseCyclingPortal");
			System.out.println("--self--");
			System.out.println("before");
			System.out.println(Rider.riderIDCounter);
			// CyclingPortals.portal.eraseCyclingPortal();
			System.out.println("--configure--");
			System.out.println("after");
			System.out.println(Rider.riderIDCounter);
			System.out.println(Race.races);
			System.out.println("--exception--");

			// saveCyclingPortal ok
			System.out.println("*******************************");
			System.out.println("saveCyclingPortal");
			System.out.println("--self--");
			// CyclingPortals.portal.saveCyclingPortal("test");
			System.out.println("--configure--");
			System.out.println("--exception--");

			// loadCyclingPortal
			System.out.println("*******************************");
			System.out.println("loadCyclingPortal");
			System.out.println("--self--");
			// CyclingPortals.portal.loadCyclingPortal("test");
			System.out.println();
			System.out.println("--configure--");
			System.out.println("--exception--");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
