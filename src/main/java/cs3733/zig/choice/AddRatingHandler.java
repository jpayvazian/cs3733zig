package cs3733.zig.choice;

import java.util.ArrayList;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import cs3733.zig.choice.db.AlternativesDAO;
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
    	logger =  context.getLogger();
      	logger.log("Loading Java Lambda handler of AddRatingHandler");
		logger.log(input.toString());
		AddRatingResponse response;
		
		//check if member has rated this alternative, if no add a rating
		//if yes, check if the rating is same or different value
		//if same, remove the entry, if different update the entry
		try {
		if(!isRaterInDB(input.getMemberName(), input.getIdAlternative())){
			addRatingToDB(input.getMemberName(), input.getIdAlternative(), input.getRating());
		}
		else {
			if(getRatingInDB(input.getMemberName(), input.getIdAlternative()) == input.getRating()) {
				unselectRatingInDB(input.getMemberName(), input.getIdAlternative()); 
			}
			else {
				switchRatingInDB(input.getMemberName(), input.getIdAlternative(), input.getRating());
		}
	}
		ArrayList<String> approvers = getRatersInDB(input.getIdAlternative(), true);
		ArrayList<String> disapprovers = getRatersInDB(input.getIdAlternative(), false);
		
		response = new AddRatingResponse(approvers, disapprovers);
		
		} catch (Exception e) {
			response = new AddRatingResponse("Unable to add rating " + "(" + e.getMessage() + ")", 400);
		}
		
		return response;
    }

    private void switchRatingInDB(String raterName, String idAlternative, boolean rating) throws Exception {
    	if (logger != null) logger.log("in switchRatingInDB");
		RatingsDAO dao = new RatingsDAO();
		dao.switchRating(raterName, idAlternative, rating);
		AlternativesDAO altdao = new AlternativesDAO();
		altdao.getAlternative(idAlternative).removeRating(raterName);
		altdao.getAlternative(idAlternative).addRating(rating, raterName);
	}

	private void unselectRatingInDB(String raterName, String idAlternative) throws Exception{
		if (logger != null) logger.log("in unselectRatingInDB");
		RatingsDAO dao = new RatingsDAO();
		dao.unselectRating(raterName, idAlternative);
		AlternativesDAO altdao = new AlternativesDAO();
		altdao.getAlternative(idAlternative).removeRating(raterName);
	}
	
	private ArrayList<String> getRatersInDB(String idAlternative, boolean rating) throws Exception{
		if (logger != null) logger.log("in getApproversInDB");
		RatingsDAO dao = new RatingsDAO();
		return dao.getRaters(idAlternative, rating);
	}

	private boolean isRaterInDB(String raterName, String idAlternative) throws Exception{
    	if (logger != null) logger.log("in isRaterInDB");
    	RatingsDAO dao = new RatingsDAO();
    	return dao.checkForRater(raterName, idAlternative);
    }    
    
    private void addRatingToDB(String raterName, String idAlternative, boolean rating) throws Exception{
		if (logger != null) logger.log("in addRatingToDB");
		RatingsDAO dao = new RatingsDAO();
		dao.addRating(raterName, idAlternative, rating);
		AlternativesDAO altdao = new AlternativesDAO();
		altdao.getAlternative(idAlternative).addRating(rating, raterName);
	}

    private boolean getRatingInDB(String raterName, String idAlternative) throws Exception{
    	if (logger != null) logger.log("in getRatingInDB");
    	RatingsDAO dao = new RatingsDAO();
    	return dao.getRating(raterName, idAlternative);
    }   
}
