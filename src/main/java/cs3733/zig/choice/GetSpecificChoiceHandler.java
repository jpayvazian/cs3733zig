package cs3733.zig.choice;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import cs3733.zig.choice.db.ChoicesDAO;
import cs3733.zig.choice.http.GetSpecificChoiceRequest;
import cs3733.zig.choice.http.GetSpecificChoiceResponse;
import cs3733.zig.choice.model.Choice;

//since this is a GET, response should be Object I THINK
public class GetSpecificChoiceHandler implements RequestHandler<GetSpecificChoiceRequest,GetSpecificChoiceResponse>{

	LambdaLogger logger;
	
	@Override
	public GetSpecificChoiceResponse handleRequest(GetSpecificChoiceRequest input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of GetSpecificChoiceHandler");
		logger.log(input.toString());
		Choice theChoice = getChoice(input.getIdChoice());
		if(theChoice==null)
			return new GetSpecificChoiceResponse(400, "Choice not found/loaded properly! code recorded: " + input.getIdChoice());
		else
			return new GetSpecificChoiceResponse(200, theChoice);
	}

	private Choice getChoice(String idChoice) {
		return getChoiceFromRDS(idChoice);
	}

	private Choice getChoiceFromRDS(String idChoice) {
		if (logger!=null) logger.log("in getChoiceFromRDS");
		ChoicesDAO dao = new ChoicesDAO();
		Choice c;
		try {
			c = dao.getChoice(idChoice);
			return c;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
