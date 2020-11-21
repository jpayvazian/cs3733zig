package cs3733.zig.choice.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
/**
 * Class for choices
 * @author Luke (Zig)
 *
 */
public class Choice {
	final private String id;
	final private String description;
	final private Alternative[] alternatives;
	private Alternative chosenAlternative;
	final private int maximumMembers;
	private boolean isCompleted;
	final private List<Member> teamMember;
	final private Date startDate;
	private Date completionDate;
	/**
	 * Constructor for a choice
	 * @param description
	 * @param alternatives
	 * @param maximumMembers
	 */
	public Choice(String description, Alternative[] alternatives, int maximumMembers, Date startDate) {
		this.id = UUID.randomUUID().toString();
		this.description = description;
		this.alternatives = alternatives;
		this.setChosenAlternative(null);
		this.maximumMembers = maximumMembers;
		this.setCompleted(false);
		this.teamMember = new ArrayList<>();
		this.startDate = startDate;
		this.setCompletionDate(null);
	}

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
		return teamMember;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setCompletionDate(Date date) {
		this.completionDate = date;
	}
	
	public Date getCompletionDate() {
		return completionDate;
	}
	
}
