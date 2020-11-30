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
// may have to delete entries in rds manually between test case runs, since current ratings will affect response
//EXECUTE 1 AT A TIME TO AVOID RACE CONDITIONS
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
	public void testAddApproveRating() {	
		AddRatingRequest arr = new AddRatingRequest(true, "jack", "cb94dc03-08e8-4b96-bbd4-296de339253f");
        String SAMPLE_INPUT_STRING = new Gson().toJson(arr);  
        
        ArrayList<String> approvers = new ArrayList<String>();
		approvers.add("jack");
		ArrayList<String> disapprovers = new ArrayList<String>();
		
        try {
        	testInput(SAMPLE_INPUT_STRING, approvers, disapprovers);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
	}
	
	@Test
	public void testAddDisapproveRating() {	
		
		AddRatingRequest arr = new AddRatingRequest(false, "tim", "cb94dc03-08e8-4b96-bbd4-296de339253f");
        String SAMPLE_INPUT_STRING = new Gson().toJson(arr);  
        
        ArrayList<String> approvers = new ArrayList<String>();
		approvers.add("jack");
		ArrayList<String> disapprovers = new ArrayList<String>();
		disapprovers.add("tim");
		
        try {
        	testInput(SAMPLE_INPUT_STRING, approvers, disapprovers);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
	}
	
	@Test
	public void testUnselectRating() {	
		//disapproving twice will unselect
		AddRatingRequest arr = new AddRatingRequest(false, "tim", "cb94dc03-08e8-4b96-bbd4-296de339253f");
        String SAMPLE_INPUT_STRING = new Gson().toJson(arr);  
        
        ArrayList<String> approvers = new ArrayList<String>();
		approvers.add("jack");
		ArrayList<String> disapprovers = new ArrayList<String>();
		
        try {
        	testInput(SAMPLE_INPUT_STRING, approvers, disapprovers);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
	}
	
	@Test
	public void testSwitchRating() {	
		AddRatingRequest arr = new AddRatingRequest(false, "jack", "cb94dc03-08e8-4b96-bbd4-296de339253f");
        String SAMPLE_INPUT_STRING = new Gson().toJson(arr);  
        
        ArrayList<String> approvers = new ArrayList<String>();
		
		ArrayList<String> disapprovers = new ArrayList<String>();
		disapprovers.add("jack");
		
        try {
        	testInput(SAMPLE_INPUT_STRING, approvers, disapprovers);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
	}
	
}
