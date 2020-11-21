package cs3733.zig.choice;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import cs3733.zig.choice.db.ChoicesDAO;
import cs3733.zig.choice.db.MembersDAO;
import cs3733.zig.choice.http.RegisterForChoiceRequest;
import cs3733.zig.choice.http.RegisterForChoiceResponse;
import cs3733.zig.choice.model.Member;

public class RegisterForChoiceHandler implements RequestHandler<RegisterForChoiceRequest,RegisterForChoiceResponse>{
	
	LambdaLogger logger;
	
	@Override
	public RegisterForChoiceResponse handleRequest(RegisterForChoiceRequest input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RegisterForChoiceHandler");
		logger.log(input.toString());
		
		//first, validate if the code even exists
		if (!isCodeInDB(input.getIdChoice())) {
			return new RegisterForChoiceResponse(400, input.getIdChoice() + " is an invalid code");
		} else {
			//validate whether member is in the DB
			if(isMemberRegistered(input.getIdChoice(), input.getMemberName())) {
				if(invalidCredentials(input.getIdChoice(), input.getMemberName(), input.getPassword())) {
					//400 ERROR
					return new RegisterForChoiceResponse(400, "Incorrect password");
				} else {
					//200 RESPONSE; LOG IN!
					return new RegisterForChoiceResponse(input.getIdChoice(), 200);
				}
			} else if(isMaxCapacity(input.getIdChoice())){
				return new RegisterForChoiceResponse(400, "This choice is full!");
			} else {
				//here we make new member
				createMember(input);
				return new RegisterForChoiceResponse(input.getIdChoice(), 200); 
			}
		}
	}
	
	private void createMember(RegisterForChoiceRequest input) {
		createMemberFromRDS(input.getIdChoice(), input.getMemberName(), input.getPassword());
	}

	private void createMemberFromRDS(String idChoice, String memberName, String password) {
		if (logger != null) logger.log("in createMemberFromRDS");
		MembersDAO dao = new MembersDAO();
		try {
			dao.createMember(idChoice, memberName, password);
		} catch (Exception e) {
			if (logger != null) logger.log("we are bad");
		}
	}

	private boolean invalidCredentials(String idChoice, String memberName, String password) {
		Member m = loadMemberFromRDS(idChoice, memberName);
		return !m.isCorrectPassword(password);
	}

	private boolean isMemberRegistered(String idChoice, String memberName) {
		return loadMemberFromRDS(idChoice, memberName)!=null;
	}
	
	
	private Member loadMemberFromRDS(String idChoice, String memberName) {
		if (logger != null) logger.log("in loadMemberFromRDS");
		MembersDAO dao = new MembersDAO();
		Member grabbedName = dao.getMember(idChoice, memberName);
		return grabbedName;
	}

	private boolean isMaxCapacity(String idChoice) {
		if (loadListOfMembersFromRDS(idChoice).size() >= loadMaxMembersFromRDS(idChoice)) {
			return true;
		}
		return false;
	}
	
	private List<String> loadListOfMembersFromRDS(String idChoice) {
		if (logger != null) logger.log("in loadListOfMembersFromRDS");
		MembersDAO dao = new MembersDAO();
		try {
			List<String> rdsListOfMembers = dao.getListOfMembers(idChoice);
			if (logger != null) logger.log("NUMBER OF CURRENT MEMBERS: " + rdsListOfMembers.size());
			return rdsListOfMembers;
		} catch (Exception e) { //might not be a list of members (I THINK)
			if (logger != null) logger.log("yo that ain't good chief");
			return new ArrayList<String>();
		}
	}
	
	private int loadMaxMembersFromRDS(String idChoice) {
		if (logger != null) logger.log("in loadListOfMembersFromRDS");
		ChoicesDAO dao = new ChoicesDAO();
		int rdsMaxMembers = dao.getMaxMemberCount(idChoice);
		if (logger != null) logger.log("NUMBER OF MAX MEMBERS: " + rdsMaxMembers);
		return rdsMaxMembers;
		
	}

	private boolean isCodeInDB(String code) {
		String rdsCode = "";
		try {
			rdsCode = loadCodeFromRDS(code);
			return rdsCode!=null;
		} catch(Exception e){
			return false;
		}
	}
	
	private String loadCodeFromRDS(String code) throws Exception{
		if (logger != null) { logger.log("in loadCodeFromRDS"); }
		ChoicesDAO dao = new ChoicesDAO();
		String rdsCode = dao.getCode(code);
		if (logger != null) {logger.log("end of load code, code is: " + dao.getCode(code));}
		return rdsCode;
	}

}
