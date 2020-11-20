package cs3733.zig.choice;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import cs3733.zig.choice.db.ChoicesDAO;
import cs3733.zig.choice.http.RegisterForChoiceRequest;
import cs3733.zig.choice.http.RegisterForChoiceResponse;

public class RegisterForChoiceHandler implements RequestHandler<RegisterForChoiceRequest,RegisterForChoiceResponse>{
	
	LambdaLogger logger;
	
	@Override
	public RegisterForChoiceResponse handleRequest(RegisterForChoiceRequest input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RegisterForChoiceHandler");
		logger.log(input.toString());
		
		//first, validate if the code even exists
		RegisterForChoiceResponse response;
		if (!isCodeInDB(input.getIdChoice())) {
			response = new RegisterForChoiceResponse(400, input.getIdChoice() + " is an invalid code");
		} else {
			//validate whether there is still space in the choice
//			if (isMaxCapacity(input.getIdChoice())) {
//				
//			}
			//validate whether member is in the DB
			//validate if there is a password
			
			response = new RegisterForChoiceResponse(input.getMemberName(), 200);  // success
		}
		
		return response;
	}

//	private boolean isMaxCapacity(String idChoice) {
//		if (loadListOfMembersFromRDS(idChoice).length >= loadMaxMembersFromRDS(idChoice)) {
//			
//		}
//	}
	
//	private String[] loadListOfMembersFromRDS(String code) {
//		
//	}
	
//	private int loadMaxMembersFromRDS(String idChoice) {
//		
//	}

	private boolean isCodeInDB(String code) {
		String rdsCode = "";
		try {
			rdsCode = loadCodeFromRDS(code);
			return true;
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
