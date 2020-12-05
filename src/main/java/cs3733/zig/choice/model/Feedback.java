package cs3733.zig.choice.model;

import java.sql.Timestamp;

/**
 * 
 * Class for Feedback
 *
 */
public class Feedback implements Comparable<Feedback>{
	private Timestamp timestamp; //might change to a different type!
	private String memberName;
	private String contents;
	
	/**
	 * Constructor for creating a feedback for the first time
	 * @param memberName
	 * @param content
	 */
	public Feedback(String memberName, String contents) {
		this.timestamp = new Timestamp(new java.util.Date().getTime());
		this.memberName = memberName;
		this.contents = contents;
	}
	/**
	 * Constructor for loading a Feedback that exists in the DAO
	 * @param memberName
	 * @param contents
	 * @param timestamp
	 */
	public Feedback(String memberName, String contents, Timestamp timestamp) {
		this.memberName = memberName;
		this.contents = contents;
		this.timestamp = timestamp;
	}
	
	// empty constructor, just in case
	public Feedback() { }
	
	/*
	 * below are getters and setters:
	 */
	
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getContents() {
		return contents;
	}
	@Override
	public int compareTo(Feedback fb) {
		return this.getTimestamp().compareTo(fb.getTimestamp());
	}
	
	
} 
