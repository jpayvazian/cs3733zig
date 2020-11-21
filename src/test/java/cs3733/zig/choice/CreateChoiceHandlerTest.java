package cs3733.zig.choice;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Assert;

import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import cs3733.zig.choice.http.CreateChoiceRequest;
import cs3733.zig.choice.http.CreateChoiceResponse;
import cs3733.zig.choice.model.Alternative;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateChoiceHandlerTest {

	@Test
	public void testCreateChoiceRequest() {
		CreateChoiceRequest ccr = new CreateChoiceRequest();
		ccr.setDescription("Where to eat");
		ccr.setMaxMembers(3);
		Alternative[] alts = new Alternative[5];
		alts[0] = new Alternative("McDonalds", "Fast Food");
		alts[1] = new Alternative("Panera", "Soup and Salad");
		alts[2] = new Alternative("Home", "Cook something");
		ccr.setAlternatives(alts);
		assertEquals(ccr.description, "Where to eat");
		assertEquals(ccr.maxMembers, 3);
		assertEquals(ccr.alternatives[1].getName(), "Panera");
	}
	
	@Test
	public void testCreateChoiceResponse() {
		CreateChoiceResponse error = new CreateChoiceResponse("Choice id not unique", 400);
		CreateChoiceResponse ccr = new CreateChoiceResponse("abcdefghijklmnopqrstuvwxyz1234567890");
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
		CreateChoiceHandler handler = new CreateChoiceHandler();
		CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);
		CreateChoiceResponse response = handler.handleRequest(req, createContext("createChoice"));

		assertTrue(response.idChoice != "");
		assertEquals(200, response.httpCode);
	}

	void testFailInput(String incoming) throws IOException {
		CreateChoiceHandler handler = new CreateChoiceHandler();
		CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);

		CreateChoiceResponse response = handler.handleRequest(req, createContext("createChoice"));

		Assert.assertEquals(400, response.httpCode);
	}

	@Test
	public void testCreateChoice() {	
		Alternative[] alts = new Alternative[5];
		alts[0] = new Alternative("Video Games", "Among Us");
		alts[1] = new Alternative("Board Games", "Catan");
		alts[2] = new Alternative("Jogging", "5k around elm park");
		
    	CreateChoiceRequest ccr = new CreateChoiceRequest("Weekend Plans", 4, alts);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
	}
}
