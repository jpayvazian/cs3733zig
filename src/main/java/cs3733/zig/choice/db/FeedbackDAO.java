package cs3733.zig.choice.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import cs3733.zig.choice.model.Feedback;

public class FeedbackDAO {
	
	private java.sql.Connection conn;
	final private String tableName = "Feedback";
	public FeedbackDAO() {
		try  {
    		this.conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		this.conn = null;
    	}
	}
	/**
	 * Creates a feedback entry into the DAO
	 * @param feedback
	 * @param idAlternative
	 * @throws Exception
	 */
	public void addFeedback(Feedback feedback, String idAlternative) throws Exception{
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tableName + " VALUES (?, ?, ?, ?, ?);");
	        ps.setString(1, UUID.randomUUID().toString());
			ps.setString(2, idAlternative);
	        ps.setString(3, feedback.getMemberName());
	        ps.setString(4, feedback.getContents());
	        ps.setTimestamp(5, feedback.getTimestamp());
	        ps.execute();  
	        
		} catch (Exception e) {
			throw new Exception("failed to add Feedback");
		}
	}

	/**
	 * Gets a list of feedback from alternative: idAlternative, via the DAO
	 * @param idAlternative
	 * @return the list of feedback. Or empty if alternative not found
	 * @throws Exception
	 */
	public ArrayList<Feedback> getFeedback(String idAlternative) throws Exception {
		ArrayList<Feedback> list = new ArrayList<Feedback>();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE idAlternative = ?;");
			ps.setString(1,  idAlternative);
			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				Feedback fb = new Feedback(resultSet.getString("memberName"), resultSet.getString("contents"), resultSet.getTimestamp("timestamp"));
                list.add(fb);
            }
            resultSet.close();
            ps.close();
            
            Collections.sort(list);
            return list;
            
		} catch (Exception e) {
			throw new Exception("Feedback not found!");
		}
	}
}
