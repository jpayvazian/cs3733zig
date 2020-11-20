package cs3733.zig.choice.model;

import java.util.Date;

/**
 * 
 * @author Luke (Zig)
 *
 */
public class Feedback {
	final private Date timestamp; //might change to a different type!
	final private String memberName;
	final private String contents;
	
	/**
	 * Constructor for Feedback
	 * @param memberName
	 * @param content
	 */
	public Feedback(String memberName, String contents) {
		this.timestamp = new Date();
		this.memberName = memberName;
		this.contents = contents;
	}
	/*
	 * below are getters and setters:
	 */
	
	public Date getTimestamp() {
		return timestamp;
	}

	public String getMemberName() {
		return memberName;
	}

	public String getContents() {
		return contents;
	}
	
	
} 
