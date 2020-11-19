package cs3733.zig.choice.http;

public class RegisterForChoiceResponse {
	public String memberName;
	public int statusCode;  // HTTP status code.
	public String error;
	
	public RegisterForChoiceResponse (String memberName, int statusCode) {
		this.memberName = "" + memberName;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public RegisterForChoiceResponse (int statusCode, String errorMessage) {
		this.memberName = "";
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "Result(" + this.memberName + " has successfully registered)";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}
