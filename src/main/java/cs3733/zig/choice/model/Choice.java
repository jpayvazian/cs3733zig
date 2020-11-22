package cs3733.zig.choice.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
/**
 * 
 * Class for choices
 *
 */
public class Choice {
	private String id;
	private String description;
	private Alternative[] alternatives;
	private Alternative chosenAlternative;
	private int maximumMembers;
	private boolean isCompleted;
	private List<Member> teamMembers;
	private Timestamp startDate;
	private Timestamp completionDate;
	
	/**
	 * Constructor for creating a Choice for the first time
	 * @param description
	 * @param alternatives
	 * @param maximumMembers
	 */
	public Choice(String description, Alternative[] alternatives, int maximumMembers) {
		this.id = UUID.randomUUID().toString();
		this.description = description;
		this.alternatives = alternatives;
		this.setChosenAlternative(null);
		this.maximumMembers = maximumMembers;
		this.setCompleted(false);
		this.teamMembers = new ArrayList<>();
		this.startDate = new Timestamp(new java.util.Date().getTime());
		this.setCompletionDate(null);
	}
	/**
	 * Constructor for loading a Choice that exists in the DAO
	 * @param id
	 * @param description
	 * @param alternatives
	 * @param chosenAlternative
	 * @param maximumMembers
	 * @param isCompleted
	 * @param teamMembers
	 * @param startDate
	 * @param completionDate
	 */
	public Choice(String id, String description, Alternative[] alternatives, Alternative chosenAlternative, int maximumMembers, boolean isCompleted, List<Member> teamMembers, Timestamp startDate, Timestamp completionDate) {
		this.id = id;
		this.description = description;
		this.alternatives = alternatives;
		this.chosenAlternative = chosenAlternative;
		this.maximumMembers = maximumMembers;
		this.isCompleted = isCompleted;
		this.teamMembers = teamMembers;
		this.startDate = startDate;
		this.completionDate = completionDate;
	}
	
	//empty constructor, just in case
	public Choice() {}
	
	/**
	 * 
	 * @param alt
	 * @return
	 * @throws Exception
	 */
	public boolean complete(Alternative alt) throws Exception {
		throw new Exception("Not yet implemented");
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Iterator<Alternative> alternatives() throws Exception {
		throw new Exception("Not yet implemented");
	}
	
	/*
	 * Below are getters and setters:
	 */
	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public Alternative[] getAlternatives() {
		return alternatives;
	}

	public Alternative getChosenAlternative() {
		return chosenAlternative;
	}

	public void setChosenAlternative(Alternative chosenAlternative) {
		this.chosenAlternative = chosenAlternative;
	}

	public int getMaximumMembers() {
		return maximumMembers;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public List<Member> getTeamMember() {
		return teamMembers;
	}
	
	public Timestamp getStartDate() {
		return startDate;
	}
	
	public void setCompletionDate(Timestamp time) {
		this.completionDate = time;
	}
	
	public Timestamp getCompletionDate() {
		return completionDate;
	}
	//Not fully done yet!
	@Override
	public String toString() {
		return "CHOICE. Des: " + this.description + " numMembers: " + this.maximumMembers + " other stuff in future!";
	}
	
}
