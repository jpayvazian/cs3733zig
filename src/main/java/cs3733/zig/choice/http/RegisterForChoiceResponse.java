package cs3733.zig.choice.http;

public class RegisterForChoiceResponse {
	final public String idChoice;
	final public int statusCode;  // HTTP status code.
	final public String error;
	
	public RegisterForChoiceResponse (String idChoice, int statusCode) {
		this.idChoice = idChoice;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public RegisterForChoiceResponse (int statusCode, String errorMessage) {
		this.idChoice = "";
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	@Override
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "Result(" + this.idChoice + " has gained a new member/returning member)";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}
