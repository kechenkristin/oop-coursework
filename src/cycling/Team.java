package cycling;

import java.util.HashMap;
import java.util.HashSet;

public class Team {
	/**
	 * All the teams in the system.
	 *
	 * 
	 * @author Kechen Liu
	 * @version 1.0
	 */
	static int teamCounter = 1;
	private int teamID;
	private String teamName;
	private String teamDesc;
	// store all the teams in the system
	public static HashMap<Integer, Team> teams = new HashMap<Integer, Team>();
	// store all the riders in one team
	public HashMap<Integer, Rider> teamRiders = new HashMap<Integer, Rider>();

	// constructor
	Team(String teamName) {
		this.setTeamName(teamName);
		this.setTeamDesc(null);
		this.setTeamID(teamCounter);
		teamCounter++;
	}

	Team(String teamName, String teamDesc) {
		this.setTeamName(teamName);
		this.setTeamDesc(teamDesc);
		this.setTeamID(teamCounter);
		teamCounter++;
	}

	// getter and setter methods
	public int getTeamID() {
		return this.teamID;
	}

	public void setTeamID(int teamID) {
		this.teamID = teamID;
	}

	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamDesc() {
		return this.teamDesc;
	}

	public void setTeamDesc(String teamDesc) {
		this.teamDesc = teamDesc;
	}

	public static boolean checkName(String newname) {
		HashSet<String> names = new HashSet<String>();
		for (Integer i : Team.teams.keySet()) {
			names.add(Team.teams.get(i).getTeamName());
		}
		if (names.contains(newname) == true) {
			return true;
		} else {
			return false;
		}
	}

}
