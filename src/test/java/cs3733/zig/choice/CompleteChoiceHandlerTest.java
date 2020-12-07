package cs3733.zig.choice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import cs3733.zig.choice.http.CompleteChoiceRequest;
import cs3733.zig.choice.http.CompleteChoiceResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CompleteChoiceHandlerTest {

	@Test
	public void testCompleteChoiceRequest() {
		CompleteChoiceRequest ccr = new CompleteChoiceRequest("b5f2d4de-76b9-484a-a6df-e9c4ab92c722", "22481df3-50a5-4e99-aeb5-1f9836a286a0");
	
		assertEquals(ccr.idChoice, "b5f2d4de-76b9-484a-a6df-e9c4ab92c722");
		assertEquals(ccr.idAlternative, "22481df3-50a5-4e99-aeb5-1f9836a286a0");
	}
	
	@Test
	public void testCompleteChoiceResponse() {
		CompleteChoiceResponse error = new CompleteChoiceResponse("Choice already completed", 400);
		Timestamp now = new Timestamp(new java.util.Date().getTime());
		CompleteChoiceResponse ccr = new CompleteChoiceResponse(now);
		String errorMsg = error.toString();
		String successMsg = ccr.toString();
		
		assertTrue(errorMsg.startsWith("ErrorResult"));
		assertTrue(successMsg.startsWith("Result"));
	}
	
	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}
	
	void testInput(String incoming) throws IOException {
		CompleteChoiceHandler handler = new CompleteChoiceHandler();
		CompleteChoiceRequest req = new Gson().fromJson(incoming, CompleteChoiceRequest.class);
		CompleteChoiceResponse response = handler.handleRequest(req, createContext("completeChoice"));

		assertTrue(response.completionDate != null);
		assertEquals(200, response.statusCode);
	}
	
	@Test
	public void testCompleteChoice() {
		CompleteChoiceRequest ccr = new CompleteChoiceRequest("b5f2d4de-76b9-484a-a6df-e9c4ab92c722", "22481df3-50a5-4e99-aeb5-1f9836a286a0");
		String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
	}
}
