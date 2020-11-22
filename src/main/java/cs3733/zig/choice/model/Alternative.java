package cs3733.zig.choice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
/**
 * 
 * @author Luke (zig)
 *
 */
public class Alternative {
	private String id;
	private String name;
	private String description;
	private List<Feedback> feedback;
	private Map<String, Rating> ratings;
	
	/**
	 * Constructor for Alternative
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
	
	//for JSON needs default constructor
	public Alternative() {
		
	}
	/**
	 * IDEA:
	 * what if... what if... we had a hashMap
	 * where the key was the member, the value was a boolean. true if approve, false if not approve
	 * 
	 */
	
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
