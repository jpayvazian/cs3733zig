package cs3733.zig.choice.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import cs3733.zig.choice.model.Alternative;

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

}
