package cs3733.zig.choice.http;

import java.util.List;

import cs3733.zig.choice.model.Choice;

public class RequestChoiceResponse {
	final public List<Choice> choices;
	final public int statusCode;
	final public String error;

	public RequestChoiceResponse(int statusCode, List<Choice> choices) {
		this.choices = choices;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public RequestChoiceResponse(int statusCode, String errorMessage) {
		this.choices = null;
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	@Override
	public String toString() {
		if (statusCode / 100 == 2) {
			return "Result(" + this.choices.toString() + " choices have been loaded)";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}
