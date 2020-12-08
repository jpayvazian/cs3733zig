package cs3733.zig.choice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import cs3733.zig.choice.http.AddFeedbackRequest;
import cs3733.zig.choice.http.AddFeedbackResponse;
import cs3733.zig.choice.model.Feedback;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AddFeedbackHandlerTest {

	@Test
	public void testAddFeedbackRequest() {
		AddFeedbackRequest afr = new AddFeedbackRequest("jack", "I can't cook", "cb94dc03-08e8-4b96-bbd4-296de339253f");
		assertEquals(afr.memberName, "jack");
		assertEquals(afr.contents, "I can't cook");
		assertEquals(afr.idAlternative, "cb94dc03-08e8-4b96-bbd4-296de339253f");
	}
	
	@Test
	public void testAddFeedbackResponse() {
		AddFeedbackResponse error = new AddFeedbackResponse("Error adding feedback", 400);
		Feedback feedback = new Feedback("jack", "I can't cook");
		AddFeedbackResponse afr = new AddFeedbackResponse(feedback);
		String errorMsg = error.toString();
		String successMsg = afr.toString();
		
		assertTrue(errorMsg.startsWith("ErrorResult"));
		assertTrue(successMsg.startsWith("Result"));
	}
	
	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}
	
	void testInput(String incoming, Feedback feedback) throws IOException{
		AddFeedbackHandler handler = new AddFeedbackHandler();
		AddFeedbackRequest req = new Gson().fromJson(incoming, AddFeedbackRequest.class);
		
		AddFeedbackResponse response = handler.handleRequest(req, createContext("addFeedback"));
		
		assertEquals(response.feedback.getMemberName(), feedback.getMemberName());
		assertEquals(response.feedback.getContents(), feedback.getContents());
		assertEquals(200, response.statusCode);
	}
	
	void testFailInput(String incoming) throws IOException{
		AddFeedbackHandler handler = new AddFeedbackHandler();
		AddFeedbackRequest req = new Gson().fromJson(incoming, AddFeedbackRequest.class);
		
		AddFeedbackResponse response = handler.handleRequest(req, createContext("addFeedback"));
		
		assertEquals(response.error, "Choice has already been completed");
		assertEquals(400, response.statusCode);
	}
	
	@Test
	public void testAddFeedback() {
		AddFeedbackRequest afr = new AddFeedbackRequest("jack", "I can't cook","cb94dc03-08e8-4b96-bbd4-296de339253f");
		 String SAMPLE_INPUT_STRING = new Gson().toJson(afr); 
		 Feedback fb = new Feedback("jack", "I can't cook");
		 
		 try {
	        	testInput(SAMPLE_INPUT_STRING, fb);
	        } catch (IOException ioe) {
	        	Assert.fail("Invalid:" + ioe.getMessage());
	        }
	}
	
	@Test
	public void testChoiceAlreadyCompleted() {
		AddFeedbackRequest afr = new AddFeedbackRequest("tester", "testFeedback","ae8e73a2-6897-4f98-aca5-9751087b0271");
		 String SAMPLE_INPUT_STRING = new Gson().toJson(afr);  
		 try {
	        	testFailInput(SAMPLE_INPUT_STRING);
	        } catch (IOException ioe) {
	        	Assert.fail("Invalid:" + ioe.getMessage());
	        }
	}
}
