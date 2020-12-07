package cs3733.zig.choice.http;

import java.sql.Timestamp;

public class CompleteChoiceResponse {
	
	public Timestamp completionDate;
	public int statusCode;
	public String error;
	
	public CompleteChoiceResponse(Timestamp completionDate) {
		this.completionDate = completionDate;
		this.statusCode = 200;
		this.error = "";
	}
	
	public CompleteChoiceResponse(String errorMessage, int code) {
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (statusCode / 100 == 2) { 
			return "Result(Choice completed at: " + completionDate + ")";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}
}
