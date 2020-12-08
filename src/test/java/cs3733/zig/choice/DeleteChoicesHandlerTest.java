package cs3733.zig.choice;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

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
		DeleteChoicesResponse dcr = new DeleteChoicesResponse(200);
		
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
		DeleteChoicesRequest dcr = new DeleteChoicesRequest(14);
		String SAMPLE_INPUT_STRING = new Gson().toJson(dcr);
		
		try {
			testInput(SAMPLE_INPUT_STRING);
		} catch(IOException ioe) {
			Assert.fail("Invalid:" + ioe.getMessage());
		}
	}

	void testInput(String incoming) throws IOException{
		
	}

}
