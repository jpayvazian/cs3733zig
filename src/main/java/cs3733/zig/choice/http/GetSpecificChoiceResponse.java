package cs3733.zig.choice.http;

import cs3733.zig.choice.model.Choice;

public class GetSpecificChoiceResponse {
	final public Choice choice;
	final public int statusCode;
	final public String error;
	
	public GetSpecificChoiceResponse(int statusCode, Choice choice) {
		this.choice = choice;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public GetSpecificChoiceResponse(int statusCode, String errorMessage) {
		this.choice = null;
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	@Override
	public String toString() {
		if (statusCode / 100 == 2) {
			return "Result(" + this.choice.toString() + " choice has been loaded)";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}
