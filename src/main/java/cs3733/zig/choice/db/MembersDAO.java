package cs3733.zig.choice.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cs3733.zig.choice.model.Member;

public class MembersDAO {
	private java.sql.Connection conn;
	final private String tableName = "Members";
	public MembersDAO() {
		try  {
    		this.conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		this.conn = null;
    	}
	}
	
	public List<String> getListOfMembers(String idChoice) throws Exception {
		try {
			List<String> listOfMembers = new ArrayList<>(); //TODO: make i equal to max, which we CAN GET
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE idChoice=?;");
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
	
	public Member getMember(String idChoice, String memberName) {
		try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE idChoice=?;");
            ps.setString(1,  idChoice);
            ResultSet resultSet = ps.executeQuery();  
            Member member = null;
            while (resultSet.next()) {
                String temp = resultSet.getString("memberName");
                if(temp.equals(memberName)) {
                	member =  new Member(resultSet.getString("memberName"), resultSet.getString("password"));
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

	public void createMember(String idChoice, String memberName, String password) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tableName + " VALUES (?, ?, ?, ?);");
	        ps.setString(1, UUID.randomUUID().toString());
			ps.setString(2,  idChoice);
	        ps.setString(3, memberName);
	        ps.setString(4, password);
	        ps.execute();  
		} catch (Exception e) {
			throw new Exception("tim's exception");
			//we errored
		}
		
	}
}
