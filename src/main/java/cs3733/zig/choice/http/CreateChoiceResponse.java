package cs3733.zig.choice.http;

public class CreateChoiceResponse {

	public String idChoice;
	public int httpCode;
	public String error;
	
	public CreateChoiceResponse (String idChoice) {
		this.idChoice = idChoice;
		this.httpCode = 200;
		this.error = "";
	}
	
	public CreateChoiceResponse (String errorMessage, int code) {
		this.httpCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (httpCode / 100 == 2) { 
			return "Result( Choice with id: " + this.idChoice + " has successfully been created)";
		} else {
			return "ErrorResult(" + httpCode + ", err=" + error + ")";
		}
	}
}
