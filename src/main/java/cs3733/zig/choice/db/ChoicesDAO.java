package cs3733.zig.choice.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Date;

import cs3733.zig.choice.model.Alternative;
import cs3733.zig.choice.model.Choice;

public class ChoicesDAO {
	
	private java.sql.Connection conn;
	final private String tableName = "Choices";   // Exact capitalization

    public ChoicesDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

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
                c = new Choice(resultSet.getString("description"), alternatives, resultSet.getInt("maxMembers"), ts);
            }
            return c;
		} catch (Exception e) {
			throw new Exception("Choice was not gotten properly");
		}
	}
}
