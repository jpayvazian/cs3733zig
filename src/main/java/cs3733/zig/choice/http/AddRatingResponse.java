package cs3733.zig.choice.http;

import java.util.ArrayList;

public class AddRatingResponse {
	
	public ArrayList<String> approvers;
	public ArrayList<String> disapprovers;
	public int httpCode;
	public String error;
	
	public AddRatingResponse (ArrayList<String> approvers, ArrayList<String> disapprovers) {
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
