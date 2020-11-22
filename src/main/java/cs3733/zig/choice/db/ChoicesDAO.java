package cs3733.zig.choice.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import cs3733.zig.choice.model.Alternative;

import cs3733.zig.choice.model.Choice;

public class ChoicesDAO {
	
	private java.sql.Connection conn;

	String tblChoice = "Choices";   // Exact capitalization
	String tblAlt = "Alternatives";


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

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblChoice + " WHERE idChoice=?;");
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
    	    
    public boolean createChoice(Choice choice) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblChoice + " WHERE idChoice = ?;");
            ps.setString(1, choice.getId());
            ResultSet resultSet = ps.executeQuery();
            
            // id not unique (very unlikely)?
            while (resultSet.next()) {
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblChoice + " (idChoice, description, maxMembers, startDate) values(?,?,?,?);");
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
    
    public int getMaxMemberCount(String idChoice) {
		try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblChoice + " WHERE idChoice=?;");
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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblChoice + " WHERE idChoice=?;");
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
