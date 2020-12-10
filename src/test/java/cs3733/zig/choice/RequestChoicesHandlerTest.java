package cs3733.zig.choice;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import cs3733.zig.choice.http.RequestChoiceResponse;
import cs3733.zig.choice.model.Choice;

public class RequestChoicesHandlerTest {
	//the first element in test schema has a description "Where to eat"
	//the first element in test schema has a id 5dfc1ca7-7c26-44a2-8c12-77f8f1278ab6

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
        Assert.assertEquals(resp.choices.get(0).getId(), "5dfc1ca7-7c26-44a2-8c12-77f8f1278ab6");
        Assert.assertEquals(200, resp.statusCode);
	}
	

}
