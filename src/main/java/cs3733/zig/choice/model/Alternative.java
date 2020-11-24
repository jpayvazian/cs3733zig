package cs3733.zig.choice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
/**
 * 
 * Alternative class. Part of the model.
 *
 */
public class Alternative {
	private String id;
	private String name;
	private String description;
	private List<Feedback> feedback;
	private ArrayList<String> approvers;
	private ArrayList<String> disapprovers;
	
	/**
	 * Constructor for creating an Alternative for the first time
	 * @param name
	 * @param description
	 */
	public Alternative(String name, String description) {
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.description = description;
		this.feedback = new ArrayList<>();
		this.approvers = new ArrayList<String>();
		this.disapprovers = new ArrayList<String>();
	}
	/**
	 * Constructor for loading an Alternative that exists in the DAO
	 * @param id
	 * @param name
	 * @param description
	 * @param feedback
	 * @param ratings
	 */
	public Alternative(String id, String name, String description, List<Feedback> feedback, ArrayList<String> approvers, ArrayList<String> disapprovers) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.feedback = feedback;
		this.approvers = approvers;
		this.disapprovers = disapprovers;
	}
	
	
	//for JSON needs default constructor
	public Alternative() { }
	
	/**
	 * Adds a rater to list of either approves or disapproves
	 * @param rating true if approve, false if disapprove
	 * @param raterName
	 * @return true if successful, can't have rater in same list twice or both lists simultaneously
	 */
	public boolean addRating(boolean rating, String raterName) {
		if(rating && !approvers.contains(raterName)) {
			approvers.add(raterName);
				return true;
		}
		else if(!rating && !disapprovers.contains(raterName)) {
				disapprovers.add(raterName);
				return true;
		}
		return false; 
	}
	
	/**
	 * Removes a rater name from list of approves or disapproves
	 * @param rating true if approve, false if disapprove
	 * @return raterName
	 * @throws Exception
	 */
	public boolean removeRating(String raterName) {
		if(approvers.contains(raterName)) {
			approvers.remove(raterName);
				return true;
		}
		else if(disapprovers.contains(raterName)) {
				disapprovers.remove(raterName);
				return true;
		}
		return false; 
	}
	
	/**
	 * 
	 * @param feedback
	 * @return
	 * @throws Exception
	 */
	public boolean addFeedback(Feedback feedback) throws Exception {
		throw new Exception("Not implemented yet");
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Iterator<Feedback> allFeedback() throws Exception {
		throw new Exception("Not implemented yet");
	}
	
	/*
	 * Below are getters:
	 */
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public List<Feedback> getFeedback() {
		return feedback;
	}

	public ArrayList<String> getApprovers() {
		return approvers;
	}
	
	public ArrayList<String> getDisapprovers() {
		return disapprovers;
	}
	
	public String getId() {
		return id;
	}
 	
	
}
