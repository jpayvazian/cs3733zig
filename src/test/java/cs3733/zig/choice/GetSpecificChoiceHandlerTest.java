package cs3733.zig.choice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import cs3733.zig.choice.http.GetSpecificChoiceRequest;
import cs3733.zig.choice.http.GetSpecificChoiceResponse;

/**
 * A local test for the GetSpecificChoiceHandler
 *
 */
public class GetSpecificChoiceHandlerTest {
	@Test
	public void testGetSpecificChoiceRequest() {
		GetSpecificChoiceRequest gscr = new GetSpecificChoiceRequest();
		gscr.setIdChoice("a28e2b11-5b81-4fd6-830b-b3d98334f0dd");
		assertEquals(gscr.getIdChoice(), "a28e2b11-5b81-4fd6-830b-b3d98334f0dd");
	}
	@Test
	public void testGetSpecificChoiceResponse() {
		GetSpecificChoiceResponse error = new GetSpecificChoiceResponse(400, "choice does not exist");
		assertTrue(error.toString().startsWith("ErrorResult"));
	}
	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}
	void testInput(String incoming) throws IOException {
		GetSpecificChoiceHandler handler = new GetSpecificChoiceHandler();
		GetSpecificChoiceRequest req = new Gson().fromJson(incoming, GetSpecificChoiceRequest.class);
		GetSpecificChoiceResponse response = handler.handleRequest(req, createContext("choice/a28e2b11-5b81-4fd6-830b-b3d98334f0dd"));
		System.out.println(response.statusCode);
		assertTrue(response.choice != null);
		assertEquals(200, response.statusCode);
	}

	void testFailInput(String incoming) throws IOException {
		GetSpecificChoiceHandler handler = new GetSpecificChoiceHandler();
		GetSpecificChoiceRequest req = new Gson().fromJson(incoming, GetSpecificChoiceRequest.class);
		GetSpecificChoiceResponse response = handler.handleRequest(req, createContext("choice/gs-5b81-4fd6-830b-b3d98334f0dd"));
		Assert.assertEquals(400, response.statusCode);
	}

	@Test
	public void testGetSpecificChoice() {	
		
		GetSpecificChoiceRequest ccr = new GetSpecificChoiceRequest("a28e2b11-5b81-4fd6-830b-b3d98334f0dd");
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
	}
}
