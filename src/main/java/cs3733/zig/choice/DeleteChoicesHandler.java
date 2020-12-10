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
		
		int numberDeleted = deleteChoices(input.getDays());
		if (numberDeleted > -1) {
			response = new DeleteChoicesResponse(200, numberDeleted);
		}else {
			response = new DeleteChoicesResponse(400, "Unable to delete choices");
		}

		return response;
	}
	
	/**
	 * returns int to determine how many have been deleted, -1 if error
	 * @param days
	 * @return
	 */
	private int deleteChoices(double days) {
		return deleteChoicesFromRDS(days);
	}

	/**
	 * deletes choices from RDS based on number of days
	 * @param days
	 * @return
	 */
	private int deleteChoicesFromRDS(double days) {
		if (logger!=null) logger.log("in deleteChoicesFromRDS");
		ChoicesDAO dao = new ChoicesDAO();
		try {
			return dao.deleteChoices(days);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
