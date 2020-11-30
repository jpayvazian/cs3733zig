package cs3733.zig.choice;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Assert;

import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import cs3733.zig.choice.http.CreateChoiceRequest;
import cs3733.zig.choice.http.CreateChoiceResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateChoiceHandlerTest {

	@Test
	public void testCreateChoiceRequest() {
		CreateChoiceRequest ccr = new CreateChoiceRequest();
		ccr.setDescription("Where to eat");
		ccr.setMaxMembers(3);
		String[] altNames =  {"McDonalds", "Panera", "Home"};
		String[] altDescriptions = {"Fast Food", "Soup and Salad", "Cooking"};

		ccr.setAlternativeNames(altNames);
		ccr.setAlternativeDescriptions(altDescriptions);
		assertEquals(ccr.description, "Where to eat");
		assertEquals(ccr.maxMembers, 3);
		assertEquals(ccr.alternativeNames[1], "Panera");
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
		assertEquals(200, response.statusCode);
	}

	void testFailInput(String incoming) throws IOException {
		CreateChoiceHandler handler = new CreateChoiceHandler();
		CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);

		CreateChoiceResponse response = handler.handleRequest(req, createContext("createChoice"));

		Assert.assertEquals(400, response.statusCode);
	}

	@Test
	public void testCreateChoice() {	
		String[] altNames =  {"McDonalds", "Panera", "Home"};
		String[] altDescriptions = {"Fast Food", "Soup and Salad", "Cooking"};
		
    	CreateChoiceRequest ccr = new CreateChoiceRequest("Where to eat", 8, altNames, altDescriptions);
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
	}
}
