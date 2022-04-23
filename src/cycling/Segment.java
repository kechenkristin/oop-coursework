package cycling;

import java.util.HashMap;

public class Segment {
	/**
	 * All the segments in the system.
	 *
	 * 
	 * @author Kechen Liu
	 * @version 1.0
	 */
	private Double location;
	private Double length;
	private Double averageGrandient;
	private SegmentType type;
	public static int segmentCounter = 1;
	private int segmentId;
	private int stageId;
	// store all the segments in the system
	public static HashMap<Integer, Segment> segments = new HashMap<Integer, Segment>();

	// constructor
	// this constructor for addCateClimlStage
	public Segment(int stageId, Double location, SegmentType type, Double averageGragient, Double length) {
		this.setStageId(stageId);
		this.setLocation(location);
		this.setType(type);
		this.setAverageGrandient(averageGragient);
		this.setLength(length);
		this.setSegmentId(segmentCounter);
		segmentCounter++;
	}

	public Segment(int stageId, Double location) {
		this.setStageId(stageId);
		this.setLocation(location);
		this.setType(SegmentType.SPRINT);
		this.setAverageGrandient(null);
		this.setLength(null);
		this.setSegmentId(segmentCounter);
		segmentCounter++;
	}

	// get&set
	public Double getLocation() {
		return this.location;
	}

	public void setLocation(Double location) {
		this.location = location;
	}

	public Double getLength() {
		return this.length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getAverageGrandient() {
		return this.averageGrandient;
	}

	public void setAverageGrandient(Double averageGrandient) {
		this.averageGrandient = averageGrandient;
	}

	public SegmentType gettype() {
		return this.type;
	}

	public void setType(SegmentType type) {
		this.type = type;
	}

	public int getStageId() {
		return this.stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public int getSegmentId() {
		return this.segmentId;
	}

	public void setSegmentId(int segmentId) {
		this.segmentId = segmentId;
	}

}
