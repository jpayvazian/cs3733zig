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
	private Map<String, Rating> ratings;
	
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
		this.ratings = new HashMap<>();
	}
	/**
	 * Constructor for loading an Alternative that exists in the DAO
	 * @param id
	 * @param name
	 * @param description
	 * @param feedback
	 * @param ratings
	 */
	public Alternative(String id, String name, String description, List<Feedback> feedback, Map<String, Rating> ratings) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.feedback = feedback;
		this.ratings = ratings;
	}
	
	
	//for JSON needs default constructor
	public Alternative() { }
	
	/**
	 * 
	 * @param rating
	 * @param raterName
	 * @return
	 * @throws Exception
	 */
	public boolean addRating(boolean rating, String raterName) throws Exception {
		throw new Exception("Not implemented yet");
	}
	
	/**
	 * 
	 * @param raterName
	 * @return
	 * @throws Exception
	 */
	public boolean removeRating(String raterName) throws Exception {
		throw new Exception("Not implemented yet");
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

	public Map<String, Rating> getRatings() {
		return ratings;
	}
	
	public String getId() {
		return id;
	}
 	
	
}
