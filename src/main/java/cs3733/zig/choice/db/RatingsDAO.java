package cs3733.zig.choice.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Class for Ratings D.A.O
 *
 */
public class RatingsDAO {
	
	private java.sql.Connection conn;
	final private String tableName = "Ratings";
	
	public RatingsDAO() {
		try  {
    		this.conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		this.conn = null;
    	}
	}
	
	/**
	 * Checks if the member has previously rated alternative, via the DAO
	 * @param memberName
	 * @param idAlternative
	 * @return true if member has previously rated alternative, false if not
	 * @throws Exception
	 */
	public boolean checkForRater(String memberName, String idAlternative) throws Exception{
		 try {
	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE idAlternative=? AND memberName=?;");
	            ps.setString(1,  idAlternative);
	            ps.setString(2, memberName);
	            
	            ResultSet resultSet = ps.executeQuery();
	            
				if (resultSet.next()) {
					resultSet.close();
					ps.close();
					return true;
				}

				else {
					resultSet.close();
					ps.close();
					return false;
				}

	        } catch (Exception e) {
	        	e.printStackTrace();
	            throw new Exception("Failed in getting rater: " + e.getMessage());
	        }
	}
	
	/**
	 * Adds a rating to the DAO
	 * @param memberName
	 * @param idAlternative
	 * @param rating true if approve, false if disapprove
	 * @throws Exception
	 */
	public void addRating(String memberName, String idAlternative, boolean rating) throws Exception{
		try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tableName + " VALUES (?, ?, ?, ?);");
            ps.setString(1,  UUID.randomUUID().toString());
            ps.setString(2, idAlternative);
            ps.setString(3, memberName);
            ps.setBoolean(4, rating);
            ps.executeQuery();

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in adding rating: " + e.getMessage());
        }
	}
	
	/**
	 * Retrieves the value of a rating in the DAO
	 * @param memberName
	 * @param idAlternative
	 * @return true if rating is approve, false if disapprove
	 * @throws Exception
	 */
	public boolean getRating(String memberName, String idAlternative) throws Exception{
		try {
			boolean rating = false;
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE idAlternative=? AND memberName=?;");
            ps.setString(1,  idAlternative);
            ps.setString(2, memberName);
            
            ResultSet resultSet = ps.executeQuery();
            
			while (resultSet.next()) {
				rating = resultSet.getBoolean("rating");
			}
			
			resultSet.close();
			ps.close();
			return rating;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting rating: " + e.getMessage());
        }
	}
	
	/**
	 * deletes a rating entry from the DAO
	 * @param membername
	 * @param idAlternative
	 * @throws Exception
	 */
	public void unselectRating(String memberName, String idAlternative) throws Exception{
		try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tableName + " WHERE idAlternative=? AND memberName=?;");
            ps.setString(1, idAlternative);
            ps.setString(2, memberName);
            ps.executeQuery();

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in unselecting rating: " + e.getMessage());
        }
	}

	/**
	 * Updates the rating for a member who has changed their mind
	 * @param memberName
	 * @param idAlternative
	 * @param the new rating, true if approve, false if disapprove
	 * @throws Exception
	 */
	public void switchRating(String memberName, String idAlternative, boolean rating) throws Exception{
		try {
            PreparedStatement ps = conn.prepareStatement("UPDATE " + tableName + " SET rating=? WHERE idAlternative=? AND memberName=?;");
            ps.setBoolean(1, rating);
            ps.setString(2, idAlternative);
            ps.setString(3, memberName);
            ps.executeQuery();

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in switching rating: " + e.getMessage());
        }
	}

	/**
	 * Gets a list of members who have either approved or disapproved an alternative
	 * @param idAlternative
	 * @param rating, true for approve, false for disapprove
	 * @return list of member names
	 * @throws Exception
	 */
	public ArrayList<String> getRaters(String idAlternative, boolean rating) throws Exception{
		try {
			ArrayList<String> listOfMembers = new ArrayList<String>(); 
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE idAlternative=? AND rating=?;");
            ps.setString(1,  idAlternative);
            ps.setBoolean(2, rating);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                listOfMembers.add(resultSet.getString("memberName"));
            }
                  
            resultSet.close();
            ps.close();
            
            return listOfMembers;
		} catch (Exception e) {
			e.printStackTrace();
            throw new Exception("Failed in getting approvers: " + e.getMessage());
		}
	}


}
