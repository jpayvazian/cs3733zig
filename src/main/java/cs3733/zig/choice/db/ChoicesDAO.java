package cs3733.zig.choice.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import cs3733.zig.choice.model.Member;

public class ChoicesDAO {
java.sql.Connection conn;
	
	String tblNameChoices = "Choices";   // Exact capitalization
	String tblNameMembers = "Members";

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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblNameChoices + " WHERE idChoice=?;");
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

	public List<String> getListOfMembers(String idChoice) throws Exception {
		try {
			List<String> listOfMembers = new ArrayList<>(); //TODO: make i equal to max, which we CAN GET
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblNameMembers + " WHERE idChoice=?;");
            ps.setString(1,  idChoice);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                listOfMembers.add(resultSet.getString("idChoice"));
            }
                        
            resultSet.close();
            ps.close();
            
            return listOfMembers;
			
		} catch (Exception e) {
			//no list! since the code must havebeen valid, else we wouldn't be here...
			return new ArrayList<String>();
		}
		
	}

	public int getMaxMemberCount(String idChoice) {
		// TODO Auto-generated method stub
		try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblNameChoices + " WHERE idChoice=?;");
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

	public Member getMember(String idChoice, String memberName) {
		try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblNameMembers + " WHERE idChoice=?;");
            ps.setString(1,  idChoice);
            ResultSet resultSet = ps.executeQuery();  
            Member member = null;
            while (resultSet.next()) {
                String temp = resultSet.getString("memberName");
                if(temp==memberName) {
                	member =  new Member(resultSet.getString(2), resultSet.getString(3));
                	break;
                }
            }
            resultSet.close();
            ps.close();
            return member;
			
		} catch (Exception e) {
			//if this errors we are in deep trouble
			return null;
		}
	}
	
	

}
