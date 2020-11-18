package cs3733.zig.choice;

import java.util.List;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import cs3733.zig.choice.db.ChoicesDAO;
import cs3733.zig.choice.http.CreateChoiceRequest;
import cs3733.zig.choice.http.CreateChoiceResponse;
import cs3733.zig.choice.model.Alternative;
import cs3733.zig.choice.model.Choice;

public class CreateChoiceHandler implements RequestHandler<Object, String> {
	
	LambdaLogger logger;
/*
	boolean createChoice(String description, int numMembers, List<Alternative> alternatives) throws Exception {
		if (logger != null) { logger.log("in createChoice"); }
		ChoicesDAO dao = new ChoicesDAO();
		
		if(numMembers < 1 || alternatives.length < 2 || alternatives.length > 5){
			return false;
		} else { 
			String id = UUID.randomUUID().toString();
			Choice choice = new Choice(id, description, numMembers, alternatives);
	
			return dao.addChoice(choice);
		}
	}
	
    @Override
    public CreateChoiceResponse handleRequest(CreateChoiceRequest req, Context context) {
      	logger = context.getLogger();
		logger.log(req.toString());
		
		CreateChoiceResponse response;
		try {
				if (createChoice(req.description, req.numMembers, req.alternatives)) {
					response = new CreateChoiceResponse(req.description);
				} else {
					response = new CreateChoiceResponse(req.description, 400);
				}
		} catch (Exception e) {
			response = new CreateChoiceResponse("Unable to create choice: " + req.description + "(" + e.getMessage() + ")", 400);
		}

		return response;
    }
*/

	@Override
	public String handleRequest(Object input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}
}
