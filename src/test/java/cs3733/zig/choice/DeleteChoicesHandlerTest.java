package cs3733.zig.choice;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import cs3733.zig.choice.http.CreateChoiceRequest;
import cs3733.zig.choice.http.CreateChoiceResponse;
import cs3733.zig.choice.http.DeleteChoicesRequest;
import cs3733.zig.choice.http.DeleteChoicesResponse;

public class DeleteChoicesHandlerTest {

	@Test
	public void testDeleteChoicesRequest() {
		DeleteChoicesRequest dcr = new DeleteChoicesRequest();
		dcr.setDays(14);
		assertEquals((int)dcr.getDays(), 14);
	}
	
	@Test
	public void testDeleteChoicesResponse() {
		DeleteChoicesResponse error = new DeleteChoicesResponse(400, "Failed to delete");
		DeleteChoicesResponse dcr = new DeleteChoicesResponse(200,2);
		
		String errorMsg = error.toString();
		String successMsg = dcr.toString();
		
		assertTrue(errorMsg.startsWith("ErrorResult"));
		assertTrue(successMsg.startsWith("Result"));
	}
	
	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}
	
	@Test
	public void testDeleteChoices() {
		//30 days or older
		DeleteChoicesRequest dcr = new DeleteChoicesRequest(30);
		String SAMPLE_INPUT_STRING = new Gson().toJson(dcr);
		
		try {
			testInput(SAMPLE_INPUT_STRING);
		} catch(IOException ioe) {
			Assert.fail("Invalid:" + ioe.getMessage());
		}
	}

	void testInput(String incoming) throws IOException{
		/**
		 * manually insert choice into workbench to make it pass:
		 * insert into Choices values ("908da41c-1bd0-4021-9272-90b323502cf3", "What to Eat", 4, "2020-09-24 20:46:13",null,null)
		 */
		DeleteChoicesHandler handler = new DeleteChoicesHandler();
		DeleteChoicesRequest req = new Gson().fromJson(incoming, DeleteChoicesRequest.class);
		DeleteChoicesResponse response = handler.handleRequest(req, createContext("deleteChoices"));
		
		System.out.println(response.nmbrDeleted);
		assertEquals(200, response.statusCode);
	}
	
	void testFailInput(String incoming) throws IOException{
		DeleteChoicesHandler handler = new DeleteChoicesHandler();
		DeleteChoicesRequest req = new Gson().fromJson(incoming, DeleteChoicesRequest.class);
		DeleteChoicesResponse response = handler.handleRequest(req, createContext("deleteChoices"));
		
		assertEquals(400, response.statusCode);
	}

}
