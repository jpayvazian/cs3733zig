package cs3733.zig.choice.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import cs3733.zig.choice.model.Choice;

public class ChoicesDAO {
java.sql.Connection conn;
	
	String tblName = "Choices";   // Exact capitalization
	String tblAlt = "Alternatives";

    public ChoicesDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

    public String getCode(String name) throws Exception {
        
        try {
            String code = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE idChoice=?;");
            ps.setString(1,  name);
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
    	    
    public boolean createChoice(Choice choice) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE idChoice = ?;");
            ps.setString(1, choice.getId());
            ResultSet resultSet = ps.executeQuery();
            
            // id not unique (very unlikely)?
            while (resultSet.next()) {
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (idChoice, description, maxMembers, startDate) values(?,?,?,?);");
            ps.setString(1, choice.getId());
            ps.setString(2, choice.getDescription());
            ps.setInt(3, choice.getMaximumMembers());
            ps.setTimestamp(4, choice.getStartDate());
            ps.execute();
            
            return createAlternatives(choice);

        } catch (Exception e) {
            throw new Exception("Failed to create choice: " + e.getMessage());
        }
    }
 
    public boolean createAlternatives(Choice choice) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblAlt + " WHERE idChoice = ?;");
            ps.setString(1, choice.getId());
            ResultSet resultSet = ps.executeQuery();
            
            // id not unique (very unlikely)?
            while (resultSet.next()) {
                return false;
            }
            
			for (int i = 0; i < 5; i++) {
				if (choice.getAlternatives()[i] != null) {
					ps = conn.prepareStatement("INSERT INTO " + tblAlt + " (idAlternative, idChoice, name, description) values(?,?,?,?);");
					ps.setString(1, choice.getAlternatives()[i].getId());
					ps.setString(2, choice.getId());
					ps.setString(3, choice.getAlternatives()[i].getName());
					ps.setString(4, choice.getAlternatives()[i].getDescription());
					ps.execute();
				}
			}
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to create Alternatives: " + e.getMessage());
        }
    } 
    
}
