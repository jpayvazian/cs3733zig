package cs3733.zig.choice.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import cs3733.zig.choice.model.Alternative;

import cs3733.zig.choice.model.Choice;
/**
 * Class for Choices D.A.O
 *
 */
public class ChoicesDAO {
	
	private java.sql.Connection conn;
	final private String tableName = "Choices";

    public ChoicesDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
    /**
     * Gets the idChoice code from the DAO, or returns null if idChoice does not exist
     * @param idChoice
     * @return idChoice if it is found, null if not
     * @throws Exception
     */
    public String getCode(String idChoice) throws Exception {
        
        try {
            String code = null;

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE idChoice=?;");
            ps.setString(1,  idChoice);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                code = resultSet.getString("idChoice");
            }
                        
            resultSet.close();
            ps.close();
            
            return code;

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting code ID: " + e.getMessage());
        }
    }
    /**
     * Creates the passed-in choice as an entry into the DAO
     * @param choice
     * @return true if the choice was created, false if not
     * @throws Exception
     */
    public boolean createChoice(Choice choice) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE idChoice = ?;");
            ps.setString(1, choice.getId());
            ResultSet resultSet = ps.executeQuery();
            
            // id not unique (very unlikely)?
            while (resultSet.next()) {
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tableName + " (idChoice, description, maxMembers, startDate) values(?,?,?,?);");
            ps.setString(1, choice.getId());
            ps.setString(2, choice.getDescription());
            ps.setInt(3, choice.getMaximumMembers());
            ps.setTimestamp(4, choice.getStartDate());
            ps.execute();
            
            return new AlternativesDAO().createAlternatives(choice);

        } catch (Exception e) {
            throw new Exception("Failed to create choice: " + e.getMessage());
        }
    }
    /**
     * Gets the maximum number of members for a choice, via its entry in the DAO
     * @param idChoice
     * @return maxCount, or -1 if it fails
     */
    public int getMaxMemberCount(String idChoice) {
		try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE idChoice=?;");
            ps.setString(1,  idChoice);
            ResultSet resultSet = ps.executeQuery();   
            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt("maxMembers");
            }
                        
            resultSet.close();
            ps.close();
            
            return count;
			
		} catch (Exception e) {
			//if this errors we are in deep trouble
			return -1;
		}
	}
    /**
     * Gets the choice, based on the idChoice, in the DAO
     * @param idChoice
     * @return the choice found in the DAO, in Choice class form, or null if not found
     * @throws Exception
     */
    //TODO: FIX THIS CRAP
	public Choice getChoice(String idChoice) throws Exception {
		try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE idChoice=?;");
            ps.setString(1,  idChoice);
            ResultSet resultSet = ps.executeQuery();
            Choice c = null;
            //ERRORS IN WHILE LOOP!
            while (resultSet.next()) { 
            	Alternative[] alternatives = new AlternativesDAO().getAlternatives(idChoice); //TODO: make alternatives DAO
            	//TODO: figure out date shit. see if Jack has this done before i do it!
            	//Timestamp ts = resultSet.getTimestamp("startDate");
            	Timestamp ts = null; //THIS IS BECAUSE ABOVE CAUSES THINGS TO CRASH
                c = new Choice(resultSet.getString("description"), alternatives, resultSet.getInt("maxMembers"));
            }
            return c;
		} catch (Exception e) {
			throw new Exception("Choice was not gotten properly");
		}
	}
}
