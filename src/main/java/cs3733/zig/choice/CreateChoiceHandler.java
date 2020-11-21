package cs3733.zig.choice;

import java.sql.Timestamp;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import cs3733.zig.choice.db.ChoicesDAO;
import cs3733.zig.choice.http.CreateChoiceRequest;
import cs3733.zig.choice.http.CreateChoiceResponse;
import cs3733.zig.choice.model.Alternative;
import cs3733.zig.choice.model.Choice;


public class CreateChoiceHandler implements RequestHandler<CreateChoiceRequest, CreateChoiceResponse> {
	
	LambdaLogger logger;

	public String createChoice(String description, int maxMembers, Alternative[] alternatives) throws Exception {
		if (logger != null) { logger.log("in createChoice"); }
		ChoicesDAO dao = new ChoicesDAO();
		
			java.util.Date date = new java.util.Date();
			Timestamp startDate = new Timestamp(date.getTime());
			Choice choice = new Choice(description, alternatives, maxMembers, startDate);
	
			boolean create = dao.createChoice(choice);
			if(create) { return choice.getId(); }
			else return "";
	}
	
    @Override
    public CreateChoiceResponse handleRequest(CreateChoiceRequest req, Context context) {
      	logger = context.getLogger();
      	logger.log("Loading Java Lambda handler of CreateChoiceHandler");
		logger.log(req.toString());
		
		CreateChoiceResponse response;
		try {
			String idChoice = createChoice(req.description, req.maxMembers, req.alternatives);
			
				if (idChoice != "") {
					response = new CreateChoiceResponse(idChoice);
				} else {
					response = new CreateChoiceResponse(req.description, 400);
				}
		} catch (Exception e) {
			response = new CreateChoiceResponse("Unable to create choice: " + req.description + "(" + e.getMessage() + ")", 400);
		}

		return response;
    }

}
