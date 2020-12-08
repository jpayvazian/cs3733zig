package cs3733.zig.choice;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import cs3733.zig.choice.db.ChoicesDAO;
import cs3733.zig.choice.http.DeleteChoicesRequest;
import cs3733.zig.choice.http.DeleteChoicesResponse;

public class DeleteChoicesHandler implements RequestHandler<DeleteChoicesRequest,DeleteChoicesResponse> {
	
	LambdaLogger logger;
	
	
	@Override
	public DeleteChoicesResponse handleRequest(DeleteChoicesRequest input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler of DeleteChoicesHandler");
		logger.log(input.toString());
		DeleteChoicesResponse response = null;
		
		if (deleteChoices(input.getDays())) {
			response = new DeleteChoicesResponse(200);
		}else {
			response = new DeleteChoicesResponse(400, "Unable to delete choices");
		}

		return response;
	}
	
	/**
	 * returns boolean to determine whether anything has been deleted/error thrown
	 * @param days
	 * @return
	 */
	private boolean deleteChoices(double days) {
		return deleteChoicesFromRDS(days);
	}

	/**
	 * deletes choices from RDS based on number of days
	 * @param days
	 * @return
	 */
	private boolean deleteChoicesFromRDS(double days) {
		if (logger!=null) logger.log("in deleteChoicesFromRDS");
		ChoicesDAO dao = new ChoicesDAO();
		try {
			//return true if it successfully deletes or false if nothing to delete
			return dao.deleteChoices(days, logger);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
