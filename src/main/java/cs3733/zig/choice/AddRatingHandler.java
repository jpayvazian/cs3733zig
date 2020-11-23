package cs3733.zig.choice;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import cs3733.zig.choice.db.ChoicesDAO;
import cs3733.zig.choice.db.RatingsDAO;
import cs3733.zig.choice.http.AddRatingRequest;
import cs3733.zig.choice.http.AddRatingResponse;
/**
 * 
 * Realizes the use case of a user approving/disapproving an alternative 
 * Invoked on POST request /AddRating
 *
 */
public class AddRatingHandler implements RequestHandler<AddRatingRequest, AddRatingResponse> {

	LambdaLogger logger;
	
    @Override
    public AddRatingResponse handleRequest(AddRatingRequest input, Context context) {
        context.getLogger().log("Input: " + input);
      	logger.log("Loading Java Lambda handler of AddRatingHandler");
		logger.log(input.toString());
		
		//check if member has rated this alternative, if no add a rating
		//if yes, check if the rating is same or different value
		//if same, remove the entry, if different update the entry
	/*	if(!isRaterInDB(input.getMemberName(), input.getIdAlternative()){
			if(!addRating(input.getMemberName(), input.getRating())) {
				return new AddRatingResponse("Error adding rating", 400);
			}
		}
		else {
			if(getRatingInDB(input.getMemberName()) == input.getRating()) {
				if(!unselectRating(input.getMemberName())) {
					return new AddRatingResponse("Error unselecting rating", 400);
				}
			}
			
			else {
				if(!switchRating(input.getMemberName(), input.getRating())) {
				return new AddRatingResponse("Error switching rating", 400);
			}
		}
	}
		ArrayList<String> approvers = getApprovers(input.getIdAlternative());
		ArrayList<String> disapprovers = getDisapprovers(input.getIdAlternative());
		
		AddRatingResponse response = new AddRatingResponse(approvers, disapprovers);
		
		return response;
    }

    private boolean isRaterInDB(String memberName, String idAlternative) {
    	if (logger != null) logger.log("in isRaterInDB");
    	RatingsDAO dao = new RatingsDAO();
    	return dao.checkForRater(String memberName, String idAlternative);
    }
    */
    return new AddRatingResponse("",400);
}
}
