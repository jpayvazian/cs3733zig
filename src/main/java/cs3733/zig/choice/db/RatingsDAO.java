package cs3733.zig.choice.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	
	public boolean checkForRater(String memberName, String idAlternative) throws Exception{
		 try {
	            String code = null;

	            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE idAlternative=? AND memberName=?;");
	            ps.setString(1,  idAlternative);
	            ps.setNString(2, memberName);
	            
	            ResultSet resultSet = ps.executeQuery();
	            
	            while (resultSet.next()) {
	               return true;
	            }
	                        
	            resultSet.close();
	            ps.close();
	            
	            return false;

	        } catch (Exception e) {
	        	e.printStackTrace();
	            throw new Exception("Failed in getting rater: " + e.getMessage());
	        }
	}

}
