package cs3733.zig.choice.http;

public class CreateChoiceResponse {

	public String idChoice;
	public int statusCode;
	public String error;
	
	public CreateChoiceResponse (String idChoice) {
		this.idChoice = idChoice;
		this.statusCode = 200;
		this.error = "";
	}
	
	public CreateChoiceResponse (String errorMessage, int code) {
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) { 
			return "Result( Choice with id: " + this.idChoice + " has successfully been created)";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}
