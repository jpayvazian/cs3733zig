package cs3733.zig.choice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import cs3733.zig.choice.http.RegisterForChoiceRequest;
import cs3733.zig.choice.http.RegisterForChoiceResponse;

/**
 * A local test for the RegisterForChoiceHandler
 *
 */
public class RegisterForChoiceHandlerTest {
	@Test
	public void testRegisterForChoiceRequest() {
		RegisterForChoiceRequest rfcr = new RegisterForChoiceRequest();
		rfcr.setMemberName("my member");
		rfcr.setPassword("1234abcd");
		rfcr.setIdChoice("5dfc1ca7-7c26-44a2-8c12-77f8f1278ab6");
		assertEquals(rfcr.getMemberName(), "my member");
		assertEquals(rfcr.getPassword(), "1234abcd");
		assertEquals(rfcr.getIdChoice(), "5dfc1ca7-7c26-44a2-8c12-77f8f1278ab6");
	}
	@Test
	public void testRegisterForChoiceResponse() {
		RegisterForChoiceResponse error = new RegisterForChoiceResponse(400, "choice does not exist");
		assertTrue(error.toString().startsWith("ErrorResult"));
	}
	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}
	void testInput(String incoming) throws IOException {
		RegisterForChoiceHandler handler = new RegisterForChoiceHandler();
		RegisterForChoiceRequest req = new Gson().fromJson(incoming, RegisterForChoiceRequest.class);
		RegisterForChoiceResponse response = handler.handleRequest(req, createContext("registerForChoice"));
		assertTrue(response.idChoice.equals("5dfc1ca7-7c26-44a2-8c12-77f8f1278ab6"));
		assertEquals(200, response.statusCode);
	}

	void testFailInput(String incoming) throws IOException {
		RegisterForChoiceHandler handler = new RegisterForChoiceHandler();
		RegisterForChoiceRequest req = new Gson().fromJson(incoming, RegisterForChoiceRequest.class);
		RegisterForChoiceResponse response = handler.handleRequest(req, createContext("registerForChoice"));
		Assert.assertEquals(400, response.statusCode);
	}

	@Test
	public void testRegisterForChoice() {	
		
		RegisterForChoiceRequest ccr = new RegisterForChoiceRequest("my member", "","5dfc1ca7-7c26-44a2-8c12-77f8f1278ab6");
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
	}
	@Test
	public void testRegisterForChoiceButFails() {
		RegisterForChoiceRequest ccr = new RegisterForChoiceRequest("abcd", "test", "");
        String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);  
        
        try {
        	testFailInput(SAMPLE_INPUT_STRING);
        } catch (IOException ioe) {
        	Assert.fail("Invalid:" + ioe.getMessage());
        }
	}
}
