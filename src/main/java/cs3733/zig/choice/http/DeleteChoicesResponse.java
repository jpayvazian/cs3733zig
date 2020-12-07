package cs3733.zig.choice.http;

public class DeleteChoicesResponse {
	final public int statusCode;
	final public String error;
	
	public DeleteChoicesResponse(int statusCode) {
		this.statusCode = statusCode;
		this.error = "";
	}
	
	public DeleteChoicesResponse(int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
	}
	
	@Override
	public String toString() {
		if (statusCode / 100 == 2) {
			return "Result(Choices deleted)";
		} else {
			return "ErrorResult(" + statusCode + ", err=" + error + ")";
		}
	}

}
