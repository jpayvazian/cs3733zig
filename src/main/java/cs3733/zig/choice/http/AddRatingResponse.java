package cs3733.zig.choice.http;

import java.util.List;

public class AddRatingResponse {
	
	public List<String> approvers;
	public List<String> disapprovers;
	public int httpCode;
	public String error;
	
	public AddRatingResponse (List<String> approvers, List<String> disapprovers) {
		this.approvers = approvers;
		this.disapprovers = disapprovers;
		this.httpCode = 200;
		this.error = "";
	}
	
	public AddRatingResponse (String errorMessage, int code) {
		this.httpCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (httpCode / 100 == 2) { 
			return "Result(Approvers: " + approvers.toString() + "Disapprovers: " + disapprovers.toString() + ")";
		} else {
			return "ErrorResult(" + httpCode + ", err=" + error + ")";
		}
	}

}
