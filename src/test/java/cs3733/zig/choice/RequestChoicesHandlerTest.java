package cs3733.zig.choice;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

import cs3733.zig.choice.http.CreateChoiceRequest;
import cs3733.zig.choice.http.RequestChoiceResponse;
import cs3733.zig.choice.model.Choice;

public class RequestChoicesHandlerTest {

	@Test
	public void testRequestChoicesResponse() {
		RequestChoiceResponse error = new RequestChoiceResponse(400, "Choices not loaded correctly");
		RequestChoiceResponse rcr = new RequestChoiceResponse(200, new ArrayList<Choice>());
		String errorMsg = error.toString();
		String successMsg = rcr.toString();
		
		assertTrue(errorMsg.startsWith("ErrorResult"));
		assertTrue(successMsg.startsWith("Result"));
	}
	
	Context createContext(String apiCall) {
		TestContext ctx = new TestContext();
		ctx.setFunctionName(apiCall);
		return ctx;
	}
	
	@Test
	public void testRequestChoice() throws IOException{
		RequestChoicesHandler handler = new RequestChoicesHandler();

        RequestChoiceResponse resp = handler.handleRequest("{}", createContext("requestChoices"));
        
        Assert.assertTrue(resp.choices.size() > 0);
        Assert.assertEquals(resp.choices.get(0).getDescription(), "Where to eat");
        Assert.assertEquals(200, resp.statusCode);
	}
	

}
