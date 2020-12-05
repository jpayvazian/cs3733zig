package cs3733.zig.choice.http;

import cs3733.zig.choice.model.Feedback;

public class AddFeedbackResponse {
	
	public Feedback feedback;
	public int statusCode;
	public String error;
	
	public AddFeedbackResponse(Feedback feedback) {
		this.feedback = feedback;
		this.statusCode = 200;
		this.error = "";
	}
	
	public AddFeedbackResponse (String errorMessage, int code) {
		this.statusCode = code;
		this.error = errorMessage;
	}

	public String toString() {
		if (statusCode / 100 == 2) { 
			return "Result(Feedback: " + feedback.getMemberName() + " : " + feedback.getContents() + " at " + feedback.getTimestamp() + ")";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}

}
