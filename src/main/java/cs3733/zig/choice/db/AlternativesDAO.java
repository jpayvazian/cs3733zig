package cs3733.zig.choice.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cs3733.zig.choice.model.Alternative;
import cs3733.zig.choice.model.Choice;
/**
 * Class for Alternatives D.A.O
 *
 */
public class AlternativesDAO {
	private java.sql.Connection conn;
	final private String tableName = "Alternatives";
	public AlternativesDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }
	/**
	 * Gets a list of alternatives from choice of idChoice, via the DAO
	 * @param idChoice
	 * @return the list of alternatives. Or null if choice not found
	 * @throws Exception
	 */
	public Alternative[] getAlternatives(String idChoice) throws Exception {
		try {
			Alternative[] alternatives = new Alternative[5];
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE idChoice=?;");
            ps.setString(1,  idChoice);
            ResultSet resultSet = ps.executeQuery();
            int i=0;
            while(resultSet.next()) {
            	//FOR NOW, DO NOT WORRY ABOUT ADDING FEEDBACK
            	//I WILL WORRY ABOUT FEEDBACK VERY SOON
            	//WHICH WILL REQUIRE STUFF LIKE CONSTRUCTOR CHANGING
            	alternatives[i++] = new Alternative(resultSet.getString("name"), resultSet.getString("description"));
            }
            return alternatives;
		} catch (Exception e) {
			throw new Exception("Alternatives not found!");
		}
	}
	/**
	 * Creates a list of alternatives, based on the choice
	 * @param choice
	 * @return true if it succeeds in creating the list of alternatives, false if it fails
	 * @throws Exception
	 */
	public boolean createAlternatives(Choice choice) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE idChoice = ?;");
            ps.setString(1, choice.getId());
            ResultSet resultSet = ps.executeQuery();
            
            // id not unique (very unlikely)?
            while (resultSet.next()) {
                return false;
            }
            
			for (int i = 0; i < 5; i++) {
				if (choice.getAlternatives()[i] != null) {
					ps = conn.prepareStatement("INSERT INTO " + tableName + " (idAlternative, idChoice, name, description) values(?,?,?,?);");
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
