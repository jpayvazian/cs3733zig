package cs3733.zig.choice;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import cs3733.zig.choice.db.ChoicesDAO;
import cs3733.zig.choice.http.RequestChoiceResponse;
import cs3733.zig.choice.model.Choice;

public class RequestChoicesHandler implements RequestHandler<Object,RequestChoiceResponse> {
	
	LambdaLogger logger;

	@Override
	public RequestChoiceResponse handleRequest(Object input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of RequestChoicesHandler");
		logger.log(input.toString());
		
		List<Choice> listOfChoices = getListOfChoices();
		if(listOfChoices==null)
			//Fix this
			return new RequestChoiceResponse(400, "List of Choices not loaded properly! code recorded: ");
		else
			return new RequestChoiceResponse(200, listOfChoices);
	}

	private List<Choice> getListOfChoices() {
		return getListOfChoicesFromRDS();
	}

	private List<Choice> getListOfChoicesFromRDS() {
		if (logger!=null) logger.log("in getListOfChoicesFromRDS");
		ChoicesDAO dao = new ChoicesDAO();
		List<Choice> list;
		try {
			list = dao.getListOfChoices();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
