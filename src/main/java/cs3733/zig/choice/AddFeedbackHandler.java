package cs3733.zig.choice;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import cs3733.zig.choice.db.FeedbackDAO;
import cs3733.zig.choice.http.AddFeedbackRequest;
import cs3733.zig.choice.http.AddFeedbackResponse;
import cs3733.zig.choice.model.Feedback;

/**
 * 
 * Realizes the use case of a user adding feedback to an alternative 
 * Invoked on POST request /AddFeedback
 *
 */
public class AddFeedbackHandler implements RequestHandler<AddFeedbackRequest, AddFeedbackResponse> {

	LambdaLogger logger;
	
    @Override
    public AddFeedbackResponse handleRequest(AddFeedbackRequest input, Context context) {
    	logger = context.getLogger();
      	logger.log("Loading Java Lambda handler of AddFeedbackHandler");
		logger.log(input.toString());
		
		AddFeedbackResponse response;
	
		try {
			Feedback feedback = addFeedback(input.memberName, input.contents, input.idAlternative);
			response = new AddFeedbackResponse(feedback);
			
		} catch (Exception e) {
			response = new AddFeedbackResponse("Unable to add Feedback: " + input.contents + "(" + e.getMessage() + ")", 400);
		}
		return response;
    }

	private Feedback addFeedback(String memberName, String contents, String idAlternative) throws Exception {
		if (logger != null) logger.log("in createFeedback");
		FeedbackDAO dao = new FeedbackDAO();
		Feedback feedback = new Feedback(memberName, contents);
		dao.addFeedback(feedback, idAlternative);
		return feedback;
	}
}
