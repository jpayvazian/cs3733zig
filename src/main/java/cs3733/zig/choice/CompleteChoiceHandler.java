package cs3733.zig.choice;

import java.sql.Timestamp;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import cs3733.zig.choice.db.ChoicesDAO;
import cs3733.zig.choice.http.CompleteChoiceRequest;
import cs3733.zig.choice.http.CompleteChoiceResponse;


public class CompleteChoiceHandler implements RequestHandler<CompleteChoiceRequest, CompleteChoiceResponse> {

	LambdaLogger logger;
	
    @Override
    public CompleteChoiceResponse handleRequest(CompleteChoiceRequest input, Context context) {
    	logger = context.getLogger();
      	logger.log("Loading Java Lambda handler of CompleteChoiceHandler");
		logger.log(input.toString());
        CompleteChoiceResponse response;
        
        try {
			Timestamp completionDate = completeChoiceInDB(input.idChoice, input.idAlternative);
			response = new CompleteChoiceResponse(completionDate);
			
		} catch (Exception e) {
			response = new CompleteChoiceResponse("Unable to complete Choice: " + input.idChoice + "(" + e.getMessage() + ")", 400);
		}
        
        return response;
    }

	private Timestamp completeChoiceInDB(String idChoice, String idAlternative) throws Exception {
		if (logger != null) logger.log("in completeChoiceInDB");
		ChoicesDAO dao = new ChoicesDAO();
		return dao.completeChoice(idChoice, idAlternative);
	}
}
