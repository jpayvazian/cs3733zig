package cs3733.zig.choice;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import cs3733.zig.choice.http.AddRatingRequest;
import cs3733.zig.choice.http.AddRatingResponse;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AddRatingHandlerTest {
// all tests pass after 1 runthrough!!	
// alt id in test schema to use: cb94dc03-08e8-4b96-bbd4-296de339253f
// Delete remaining entries in rds manually between test case runs, since current ratings will affect response
	@Test
	public void testAddRatingRequest() {
		AddRatingRequest arr = new AddRatingRequest(true, "jack", "cb94dc03-08e8-4b96-bbd4-296de339253f");
		assertTrue(arr.rating);
		assertEquals(arr.memberName, "jack");
		assertEquals(arr.idAlternative, "cb94dc03-08e8-4b96-bbd4-296de339253f");
	}
	
	@Test
	public void testAddRatingResponse() {
		AddRatingResponse error = new AddRatingResponse("Error adding rating", 400);
		ArrayList<String> approvers = new ArrayList<String>();
		approvers.add("jack");
		ArrayList<String> disapprovers = new ArrayList<String>();
		
		AddRatingResponse arr = new AddRatingResponse(approvers, disapprovers);
		String errorMsg = error.toString();
		String successMsg = arr.toString();
		
		assertTrue(errorMsg.startsWith("ErrorResult"));
		assertTrue(successMsg.startsWith("Result"));
	}
	
	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}
	
	void testInput(String incoming, ArrayList<String> approvers, ArrayList<String> disapprovers) throws IOException {
		AddRatingHandler handler = new AddRatingHandler();
		AddRatingRequest req = new Gson().fromJson(incoming, AddRatingRequest.class);
		AddRatingResponse response = handler.handleRequest(req, createContext("addRating"));

		assertEquals(200, response.statusCode);
		assertEquals(approvers,response.approvers);
		assertEquals(disapprovers,response.disapprovers);
	}

	void testFailInput(String incoming) throws IOException {
		AddRatingHandler handler = new AddRatingHandler();
		AddRatingRequest req = new Gson().fromJson(incoming, AddRatingRequest.class);
		AddRatingResponse response = handler.handleRequest(req, createContext("addRating"));

		Assert.assertEquals(400, response.statusCode);
	}

	@Test
	public void testAddRatingHandler() {
		AddRatingRequest arr1 = new AddRatingRequest(true, "jack", "cb94dc03-08e8-4b96-bbd4-296de339253f");
        String SAMPLE_INPUT_STRING1 = new Gson().toJson(arr1);  
        
        AddRatingRequest arr2 = new AddRatingRequest(false, "tim", "cb94dc03-08e8-4b96-bbd4-296de339253f");
        String SAMPLE_INPUT_STRING2 = new Gson().toJson(arr2);  
        
        AddRatingRequest arr3 = new AddRatingRequest(false, "jack", "cb94dc03-08e8-4b96-bbd4-296de339253f");
        String SAMPLE_INPUT_STRING3 = new Gson().toJson(arr3);
        
        ArrayList<String> approvers = new ArrayList<String>();
		approvers.add("jack");
		
		ArrayList<String> disapprovers = new ArrayList<String>();
		disapprovers.add("tim");
        
        ArrayList<String> empty = new ArrayList<String>();
        
        ArrayList<String> disapprovers2 = new ArrayList<String>();
		disapprovers2.add("jack");
        
        try {
        	testInput(SAMPLE_INPUT_STRING1, approvers, empty); //approve
        	testInput(SAMPLE_INPUT_STRING2, approvers, disapprovers);//disapprove 
        	testInput(SAMPLE_INPUT_STRING2, approvers, empty); //unselect disapprove
        	testInput(SAMPLE_INPUT_STRING3, empty, disapprovers2);//switch from approve to disapprove
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
	}
}
